package com.blackwell;

import java.io.*;
import java.util.*;

class CubeSolver {
    private static final int DEFAULT_DEPTH_LIMIT = 7;

    private static final int MAX_CORNER_PERMUTATIONS = 88_179_840;
    private static final int MAX_EDGE_PERMUTATIONS = 42_577_920;

    private static final String DATA_PATH = "data/";
    private static final String CORNERS_FILE = DATA_PATH + "corners.txt";
    private static final String EDGE_ONE_FILE = DATA_PATH + "edge1.txt";
    private static final String EDGE_TWO_FILE = DATA_PATH + "edge2.txt";

    private static final String ALREADY_SOLVED = "This cube is already solved!";

    private boolean isLoaded = false;
    private byte[] cornerHeuristics;
    private byte[] edgeOneHeuristics;
    private byte[] edgeTwoHeuristics;

    CubeSolver(){
        Scanner in = new Scanner(System.in);
        int depthLimit = DEFAULT_DEPTH_LIMIT;
        int userSelection;
        do {
            System.out.println("Enter your choice: ");
            userSelection = in.nextInt();
            switch (userSelection) {
                case 0:
                    System.out.println("Generating heuristic value for corners.");
                    iterativeDeepening(new EncodeCorner(), MAX_CORNER_PERMUTATIONS, CORNERS_FILE, depthLimit);
                    break;
                case 1:
                    System.out.println("Generating heuristic value for edges, group one.");
                    iterativeDeepening(new EncodeEdge(CubieGroup.EDGE_ONE), MAX_EDGE_PERMUTATIONS, EDGE_ONE_FILE, depthLimit);
                    break;
                case 2:
                    System.out.println("Generating heuristic value for edges, group two.");
                    iterativeDeepening(new EncodeEdge(CubieGroup.EDGE_TWO), MAX_EDGE_PERMUTATIONS, EDGE_TWO_FILE, depthLimit);
                    break;
                case 3:
                    RubiksCube startState = new RubiksCube();

                    startState = startState.performRotation(Rotation.COUNTER_CLOCKWISE, Face.BOTTOM);

                    String solution = findOptimalSolution(startState);
                    System.out.println(solution);
                    break;
            }
        } while (userSelection != 4);
    }


    //////////////////////////////////////////////////////////////////////////
    // Solver
    //////////////////////////////////////////////////////////////////////////

    private String findOptimalSolution(RubiksCube startState) {
        if (startState.isSolved())
            return ALREADY_SOLVED;

        if(!isLoaded){
            cornerHeuristics = loadHeuristicValue(CORNERS_FILE, MAX_CORNER_PERMUTATIONS);
            edgeOneHeuristics = loadHeuristicValue(EDGE_TWO_FILE, MAX_EDGE_PERMUTATIONS);
            edgeTwoHeuristics = loadHeuristicValue(EDGE_TWO_FILE, MAX_EDGE_PERMUTATIONS);
            isLoaded = true;
        }

        CubeNode startNode = new CubeNode(null, startState, null, null, 0, 0);
        startNode.heuristic = maxHeuristic(startNode.state);

        CubeNode solution = IDAStar(startNode);

        return buildSolutionString(solution);
    }

    private CubeNode IDAStar(CubeNode root) {
        int heuristicMin = root.heuristic;
        CubeNode solution = null;

        while (solution == null)
            solution = doSearch(root, 0,heuristicMin++);

        return solution;
    }

    private CubeNode doSearch(CubeNode toSearch, int pathCost, int currentMin) {
        if (toSearch.state.isSolved())
            return toSearch;

        List<CubeNode> successors = generateSuccessors(toSearch);

        for (CubeNode successor : successors){
            int estimatedCost = pathCost + successor.heuristic;

            if(estimatedCost <= currentMin){
                CubeNode solution = doSearch(successor, pathCost+1, currentMin);

                if (solution != null)
                    return solution;
            }
        }

        return null;
    }

    private List<CubeNode> generateSuccessors(CubeNode root) {
        List<CubeNode> successors = new ArrayList<>();
        RubiksCube dummyCube;
        int heuristic;

        for(Face face : Face.values())
            for(Rotation rotation : Rotation.values()){
                if(root.rotation == rotation && root.face == face)
                    continue;

                dummyCube = root.state.performRotation(rotation, face);
                heuristic = maxHeuristic(dummyCube);
                successors.add( new CubeNode(root, dummyCube, rotation, face, heuristic, 0));
            }

        return successors;
    }

    //////////////////////////////////////////////////////////////////////////
    // Heuristic
    //////////////////////////////////////////////////////////////////////////

    private int maxHeuristic(RubiksCube cube) {
        int corner = cornerHeuristics[cube.getCornerEncoding()];
        int edgeOne = edgeOneHeuristics[cube.getEdgeOneEncoding()];
        int edgeTwo = edgeTwoHeuristics[cube.getEdgeTwoEncoding()];

        return Math.max( Math.max(corner, edgeOne), edgeTwo);
    }

    private byte[] loadHeuristicValue(String fileName, int tableSize){
        byte[] result = new byte[tableSize];

        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {

            for(int i=0; i<tableSize; ++i)
                result[i] = Byte.parseByte(in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void iterativeDeepening(EncodeStrategy encoder, int tableSize, String fileName, int depthLimit){
        byte[] hTable = new byte[tableSize];

        RubiksCube solved = new RubiksCube();
        CubeNode root = new CubeNode(null,solved,null,null,0,0);

        for(int depth=0; depth<depthLimit; depth++)
            dlsRecursive(root, encoder, hTable, depth);

        writeToFile(hTable, fileName);
    }

    private void dlsRecursive(CubeNode startNode, EncodeStrategy encoder, byte[] hTable, int depthLinmit){
        if (startNode.heuristic == depthLinmit){
            if(hTable[startNode.permId] == 0)
                hTable[startNode.permId] = (byte) depthLinmit;
            return;
        }

        int encoding;
        RubiksCube child;
        for(Face currentFace : Face.values()){
            child = startNode.state.performRotation(Rotation.CLOCKWISE, currentFace);
            encoding = encoder.doEncode(child);

            if (encoding == 0)
                continue;

            dlsRecursive(new CubeNode(startNode, child, Rotation.CLOCKWISE, currentFace, startNode.heuristic+1, encoding),
                    encoder, hTable, depthLinmit);
        }
    }

    //////////////////////////////////////////////////////////////////////////
    // Utils
    //////////////////////////////////////////////////////////////////////////

    private static String buildSolutionString(CubeNode solutionNode) {

        Deque<String> moveStack = new ArrayDeque<>();

        while (solutionNode.parent != null){
            moveStack.push(solutionNode.face.toString() + ":"+solutionNode.rotation.toString());
            solutionNode = solutionNode.parent;
        }

        StringBuilder sb = new StringBuilder();
        while (!moveStack.isEmpty())
            sb.append(moveStack.pop()).append("|");

        return sb.toString();
    }

    private void writeToFile(byte[] hTable, String fileName){
        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(fileName)))) {

            for (byte aHTable : hTable)
                out.println(aHTable);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


// Press Play On Tape - The Man With The Gun
// Emphatic - This Time
// Flowing River - Dying in Paradise
// The Halo Method - The Last Astronaut