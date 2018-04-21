package com.blackwell.solver;

import java.io.*;

public class Tree {
    private Node root;
    private BufferedWriter out;

    private static final int MAX_DEPTH = 4;
    public Tree(){
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data/solver/results.txt")));
            root = new Node((new Cube()), (byte) 0);
            run(root, (new StringBuilder()), 0);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void run(Node n, StringBuilder result, int depth){
        if (depth < MAX_DEPTH) {
            System.out.println("-");
            n.setSons();
            for (int i = 0; i < Cube.SIDES; ++i){
                run(n.getSon(i), result.append(n.getSon(i).getRotation()), ++depth);
                try {
                    out.append(n.getSon(i).getCube()).append(": ").append(result.toString()).append(System.lineSeparator());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
