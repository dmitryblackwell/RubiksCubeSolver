package com.blackwell.solver.CubeTree;

import com.blackwell.solver.cube.Cube;
import com.blackwell.solver.cube.Side;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CubeSolver {
    public static String findOptimalSolution(Cube startState){

        cornerHeuristics = loadHeuristicValues(CORNERS_HEURISTIC_FILE, MAX_CORNER_PERMUTATIONS);
        edgeOneHeuristics = loadHeuristicValues(EDGE_ONE_HEURISTIC_FILE, MAX_EDGE_PERMUTATIONS);
        edgeTwoHeuristics = loadHeuristicValues(EDGE_TWO_HEURISTIC_FILE, MAX_EDGE_PERMUTATIONS);

        Node root = new Node(null, startState,null,0);
        root.setHeuristic(maxHeuristic(root.getCube()));
        Node solution = IDAStar(root);

        return buildSolution(solution);
    }

    private static byte[] loadHeuristicValues(String fileName, int tableSize) {
        byte[] toReturn = new byte[tableSize];

        try (BufferedReader inFile = new BufferedReader(new FileReader(fileName))) {
            for (int i=0; i < tableSize; i+=1) {
                toReturn[i] = Byte.parseByte(inFile.readLine());
            }
        } catch (IOException e) {
            System.out.println("Unable to load heuristic values contained in file \"" + fileName + "\".");
        }
        return toReturn;
    }

    private static String buildSolution(Node solution) {
        // TODO Node solution -> String
        return "";
    }

    private static Node IDAStar(Node root) {
        int heuristicMin = root.getHeuristic();
        Node solution = null;
        while (solution == null){
            solution = doSearch(root, 0, heuristicMin);
            heuristicMin += 1;
        }

        return solution;
    }

    private static Node doSearch(Node root, int pathcost, int currentMin) {
        if (root.isSolved())
            return root;

        List<Node> successors = generateSuccessors(root);
        for(Node successor : successors){
            int estimateCost = pathcost + successor.getHeuristic();
            if(estimateCost < currentMin){
                Node solution = doSearch(successor, pathcost + 1, currentMin);
                if (solution != null)
                    return solution;
            }
        }
        return null;
    }

    private static List<Node> generateSuccessors(Node root) {
        List<Node> successors = new ArrayList<>();
        Cube dummyCube = null;
        int heuristic = 0;

        for(Rotation rotation : Rotation.getPossibleRotations()){
                dummyCube = new Cube(root.getCube());
                dummyCube.rotate(rotation);
                heuristic = maxHeuristic(dummyCube);
                successors.add(new Node(root, dummyCube, rotation, heuristic));
        }
        return successors;
    }
    private static final String CORNERS_HEURISTIC_FILE = "data/heuristic/corners.txt";
    private static final String EDGE_ONE_HEURISTIC_FILE = "data/heuristic/edges1.txt";
    private static final String EDGE_TWO_HEURISTIC_FILE = "data/heuristic/edges2.txt";

    private static byte[] cornerHeuristics;
    private static byte[] edgeOneHeuristics;
    private static byte[] edgeTwoHeuristics;

    private static final int MAX_CORNER_PERMUTATIONS = 88_179_840;
    private static final int MAX_EDGE_PERMUTATIONS = 42_577_920;


    private static int maxHeuristic(Cube cube) {
        // Lookup the heuristic values for each of the three cubie subgroups.
//        int cornerValue = cornerHeuristics[cube.getCornerEncoding()];
//        int edgeGroupOne = edgeOneHeuristics[cube.getEdgeOneEncoding()];
//        int edgeGroupTwo = edgeTwoHeuristics[cube.getEdgeTwoEncoding()];
//
//        int max = Math.max(Math.max(cornerValue, edgeGroupOne), edgeGroupTwo);
//        return max;
        //TODO make it find maxHeuristic
        return 0;
    }
}
