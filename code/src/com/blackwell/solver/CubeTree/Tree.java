package com.blackwell.solver.CubeTree;

import com.blackwell.solver.cube.Cube;
import com.blackwell.solver.cube.Side;

import java.io.*;

public class Tree {
    private Node root;
    private BufferedWriter out;

    private static final String EOL = System.lineSeparator();
    private static final int MAX_DEPTH = 10;
    public Tree(){
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data/solver/results.txt")));
            root = new Node((new Cube()), (byte) 0);
            run(root, 0, 0);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run(Node n, long result, int depth) throws IOException {
        out.append(n.getCube()).append(": ").append(String.valueOf(result)).append(EOL);
        if (depth == MAX_DEPTH)
            return;

        n.setSons();
        for(int i=0; i<Cube.SIDES; ++i)
            run(n.getSon(i), result*10+Side.values()[i].toByte(), depth+1);
    }
}

// create tree with rotations and then go on it, rotate cube and save it