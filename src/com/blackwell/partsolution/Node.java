package com.blackwell.partsolution;

public class Node {
    int s;
    CubeCorners cube;
    Node[] nodes = new Node[6];

    private static final int DEEP_MAX = 11;
    // 12 -> 0.02
    // 20 -> 2.06
    // 21 -> 3.66
    // 22 -> 7.78
    private static int nodesCreated = 0;
    public boolean isSolved(){
        return cube.isSolved();
    }

    Node(int s, CubeCorners c, int deep){
        this.s = s;
        cube = c;

        for(int i=0; i<6; ++i)
            nodes[i] = null;

        if (deep < DEEP_MAX){
            nodes[0] = new Node(Side.L.get(), (new CubeCorners(cube)).getLeft(), ++deep);
            nodes[1] = new Node(Side.R.get(), (new CubeCorners(cube)).getRight(), ++deep);
            nodes[2] = new Node(Side.F.get(), (new CubeCorners(cube)).getFace(), ++deep);
            nodes[3] = new Node(Side.B.get(), (new CubeCorners(cube)).getBack(), ++deep);
            nodes[4] = new Node(Side.U.get(), (new CubeCorners(cube)).getUp(), ++deep);
            nodes[5] = new Node(Side.D.get(), (new CubeCorners(cube)).getDown(), ++deep);
        }
    }

    public static void search(CubeCorners c){
        if (c.isSolved())
            System.out.println("solved");

        Node n = new Node(0,new CubeCorners(c),0);
        search(n,1);

    }

    private static void printSolution(long result){
        System.err.println(getSolution(result));
    }
    private static String getSolution(long result){
        StringBuilder builder = new StringBuilder();
        if (result == 1)
            System.out.println("we fucked");

        while (result != 1){
            long last = result%10;
            builder.append(Side.values()[(int) last]);
            result = (result-last)/10L;
        }
        return builder.reverse().toString();
    }

    private static void search(Node n, long result){
        //System.out.println( getSolution(result) +": \n" + n.cube + "\n");
        if (n.isSolved()) {
            System.out.println("_____________________________ haha");
            System.out.println(getSolution(result) +": \n" + n.cube + "\n");
           // printSolution(result);
            System.out.println("_____________________________ haha");
            return;
        }
        for(int i=0; i<6; ++i){
            if (n.nodes[i] != null)
                search(n.nodes[i],result*10+n.nodes[i].s);
        }
        //return result;
    }
}
