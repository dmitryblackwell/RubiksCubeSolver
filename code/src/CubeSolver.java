import java.util.ArrayList;
import java.util.List;

public class CubeSolver {

    private static final int MAX_CORNER_PERMUTATIONS = 88_179_840;
    private static final int MAX_EDGE_PERMUTATIONS = 42_577_920;

    private static byte[] cornerHeuristics;
    private static byte[] edgeOneHeuristics;
    private static byte[] edgeTwoHeuristics;

    private static boolean isLoaded = false;

    public static void main(String[] args) {
        int userEnter = 0;
        int depthLimit = 7;
        switch (userEnter) {
            case 0:
                //Generating the heuristic values for the corners
                iterativeDeepening(new EncodeStrategyCorner(),
                        MAX_CORNER_PERMUTATIONS, "corners.txt", depthLimit);
                break;
            case 1:
                // Generating the heuristic values for the edge group one
                iterativeDeepening(new EncodeStrategyEdge(CubieGroup.EDGE_ONE),
                        MAX_EDGE_PERMUTATIONS, "edges1.txt", depthLimit);
                break;
            case 2:
                // Generating the heuristic values for the edge group two
                iterativeDeepening(new EncodeStrategyEdge(CubieGroup.EDGE_TWO),
                        MAX_EDGE_PERMUTATIONS, "edges2.txt", depthLimit);
                break;
            case 3:
                RubiksCube startState = loadCubeFromFile(); // loading Cube from file
                if (startState != null) {
                    String solution = findOptimalSolution(startState); // searching for solution
                    System.out.println("\nSolution Path: " + solution + "\n");
                }
                break;
            default:
                System.out.println("Not a valid choice. Please select an option from the following...");
        }
    }

    private static RubiksCube loadCubeFromFile() {
        return new RubiksCube("RRRRRRRRRGGGYYYBBBGGGYYYBBBGGGYYYBBBOOOOOOOOOWWWWWWWWW");
    }
    /////////////////////////////////////////////////////////////////////// Solution Searcher
    private static String findOptimalSolution(RubiksCube startState) {

        // Check if the cube is already solved.
        if (startState.isSolved()) {
            return "This cube is already solved!";
        }

        // Check if the heuristic tables have already been loaded into memory.
        if (!isLoaded) {
            cornerHeuristics = loadHeuristicValues("corners.txt", MAX_CORNER_PERMUTATIONS);
            edgeOneHeuristics = loadHeuristicValues("edges1.txt", MAX_EDGE_PERMUTATIONS);
            edgeTwoHeuristics = loadHeuristicValues("edges2.txt", MAX_EDGE_PERMUTATIONS);
            isLoaded = true;
        }

        // Create the root node from which the search will branch out.
        CubeNode startNode = new CubeNode(null, startState, null, null, 0, 0);

        // Set the heuristic value for the starting state.
        startNode.heuristic = lookupMaxHeuristic(startNode.state);

        // Begin the search
        System.out.println("Initiating search for optimal solution...");
        CubeNode solution = IDAStar(startNode);

        // Return the solution path.
        return buildSolutionString(solution);
    }

    private static CubeNode IDAStar(CubeNode root) {

        int heuristicMinimum = root.heuristic;
        CubeNode solution = null;

        while (solution == null) {
            solution = doSearch(root, 0, heuristicMinimum);
            heuristicMinimum += 1;
        }

        return solution;
    }

    private static CubeNode doSearch(CubeNode toSearch, int pathcost, int currentMin) {

        if (toSearch.state.isSolved())
            return toSearch;

        List<CubeNode> successors = generateSuccessors(toSearch);

        for (CubeNode successor : successors) {

            int estimatedCost = pathcost + successor.heuristic;

            if (estimatedCost <= currentMin) {
                CubeNode solution = doSearch(successor, pathcost + 1, currentMin);

                if (solution != null)
                    return solution;
            }
        }

        return null;
    }


    private static List<CubeNode> generateSuccessors(CubeNode root) {

        List<CubeNode> successors = new ArrayList<CubeNode>();
        RubiksCube dummyCube = null;
        int heuristic = 0;

        // Perform every possible rotation of each face.
        for (Face currentFace : Face.values()) {
            for (Rotation currentRotation : Rotation.values()) {

                if (root.rotation == currentRotation && root.face == currentFace)
                    continue;

                dummyCube = root.state.performRotation(currentRotation, currentFace);
                heuristic = lookupMaxHeuristic(dummyCube);

                successors.add(new CubeNode(root, dummyCube, currentRotation, currentFace, heuristic, 0));
            }
        }

        return successors;
    }

    private static int lookupMaxHeuristic(RubiksCube cube) {
        int cornerValue = cornerHeuristics[cube.getCornerEncoding()];
        int edgeGroupOne= edgeOneHeuristics[cube.getEdgeOneEncoding()];
        int edgeGroupTwo = edgeTwoHeuristics[cube.getEdgeTwoEncoding()];

        int currentMax = Math.max(Math.max(cornerValue, edgeGroupOne), edgeGroupTwo);// max of this three

        // Return the max heuristic value.
        return currentMax;
    }

    private static String buildSolutionString(CubeNode solution) {
        // return solution in string nor CubeNode
        return solution.toString();
    }
    private static byte[] loadHeuristicValues(String filename, int tableSize) {
        // loading heuristic value from file
        return new byte[tableSize];
    }



    /////////////////////////////////////////////////////////////////////// Heuristic finder
    private static void iterativeDeepening(EncodeStrategy encoder,
                                           int tableSize, String filename, int depthLimit) {
        byte[] hTable = new byte[tableSize];

        // Start with a solved cube.
        RubiksCube solved = new RubiksCube();
        CubeNode rootNode = new CubeNode(null, solved, null, null, 0, 0);

        for (int depth=0; depth < depthLimit; depth+=1) {
            dlsRecursive(rootNode, encoder, hTable, depth);
        }

        CubeSolver.writeToFile(hTable, filename);
    }


    private static void dlsRecursive(CubeNode startNode, EncodeStrategy encoder,
                                     byte[] hTable, int depthLimit) {

        if (startNode.heuristic == depthLimit) {
            if (hTable[startNode.permId] == 0)
                hTable[startNode.permId] = (byte)depthLimit;

            return;
        }

        // loop variables
        int encoding = 0;
        RubiksCube child = null;

        // Generate all successors...
        for (Face currentFace : Face.values()) {

            child = startNode.state.performRotation(Rotation.CLOCKWISE, currentFace);

            encoding = encoder.doEncode(child);

            // If this is a goal node we do not want to explore it...
            if (encoding == 0)
                continue;

            dlsRecursive(new CubeNode(startNode, child, Rotation.CLOCKWISE, currentFace, startNode.heuristic+1, encoding),
                    encoder, hTable, depthLimit);
        }
    }

    private static void writeToFile(byte[] hTable, String filename) {
        // save this table to file
    }
}


class RubiksCube{
    public RubiksCube(String cubeStr) {/* Creating cube from string */}
    public RubiksCube() {/* Creating solved cube */}
    public boolean isSolved() { return false; }

    public RubiksCube performRotation(Rotation clockwise, Face currentFace) {
        // return copy of this cube with rotation
        return new RubiksCube();
    }

    public int getCornerEncoding() { return cornerEncoder.doEncode(this); }
    public int getEdgeOneEncoding() { return edgeOneEncoder.doEncode(this); }
    public int getEdgeTwoEncoding() { return edgeTwoEncoder.doEncode(this); }


    private static EncodeStrategy cornerEncoder = new EncodeStrategyCorner();
    private static EncodeStrategy edgeOneEncoder = new EncodeStrategyEdge(CubieGroup.EDGE_ONE);
    private static EncodeStrategy edgeTwoEncoder = new EncodeStrategyEdge(CubieGroup.EDGE_TWO);

}

class EncodeStrategyCorner implements EncodeStrategy {

    @Override
    public int doEncode(RubiksCube aCube) {
        // TODO encode this shit
    }
}


class EncodeStrategyEdge implements EncodeStrategy {
    public EncodeStrategyEdge(CubieGroup group) { }
    @Override
    public int doEncode(RubiksCube aCube) {
        return 0;
    }
}

interface EncodeStrategy {
    public int doEncode(RubiksCube aCube);

    public static final int[] CORNER_WEIGHTS = {3_674_160, 174_960, 9_720, 648, 54, 6, 1, 0};
    public static final int[] EDGE_WEIGHTS = {1_774_080, 80_640, 4_032, 224, 14, 1};
}
enum CubieGroup { CORNER, EDGE_ONE, EDGE_TWO }

class CubeNode{protected RubiksCube state = null;

    protected CubeNode parent = null;
    protected Rotation rotation;
    protected Face face;
    protected int heuristic = 0;
    protected int permId = -1;

    CubeNode(CubeNode parent, RubiksCube aCube, Rotation rotation, Face face, int heuristic, int id) {}
}

enum Face {
    FRONT(0,1,2,3,8,19,18,12),
    REAR(4,5,6,7,10,14,15,16),
    LEFT(7,0,3,4,11,12,13,14),
    RIGHT(1,6,5,2,9,16,17,19),
    TOP(7,6,1,0,10,9,8,11),
    BOTTOM(3,2,5,4,18,17,15,13);

    Face(int tl, int tr, int br, int bl, int t, int r, int b, int l) {
        top_left = tl;
        top_right = tr;
        bottom_right = br;
        bottom_left = bl;
        top = t;
        right = r;
        bottom = b;
        left = l;
    };

    final int top_left;
    final int top_right;
    final int bottom_right;
    final int bottom_left;
    final int top;
    final int right;
    final int bottom;
    final int left;
}

enum Rotation {
    CLOCKWISE, COUNTER_CLOCKWISE, HALF_TURN
};

