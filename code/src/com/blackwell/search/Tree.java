package com.blackwell.search;

import com.blackwell.cube.CubeCorners;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Tree {
    private static final int DEEP_MAX = 11;
    Node root;
    public Tree(CubeCorners c){
        root = new Node(9,c);
        setUp(root, 0);
    }
    private void setUp(Node n, int deep){
        if (deep > DEEP_MAX)
            return;

        n.setUpSons();
        for(int i=0; i<6; ++i)
            setUp(n.getSon(i), ++deep);
    }
    public void search(CubeCorners c){
        if (c.isSolved())
            System.out.println("solved");

        //search(root,root.getRotation());
    }

    private StringBuilder getTreeString(Node n, StringBuilder str, int deep){
        if (deep > DEEP_MAX)
            return str;
        // Out Of Memory
        str.append(n);
        for (int i = 0; i < 6; ++i)
            str.append(getTreeString(n.getSon(i), str, ++deep));

        return str;
    }
    private void print(Node n){
        System.out.println("Node");
        System.out.println(n);
        for (int i = 0; i < 6; ++i)
            if(n.getSon(i) != null)
                print(n.getSon(i));
    }
    private void printToFile(BufferedWriter out, Node n) throws IOException {
        out.write("Node");
        out.newLine();
        out.write(n.toString());
        for (int i = 0; i < 6; ++i)
            if(n.getSon(i) != null)
                printToFile(out, n.getSon(i));
    }

    public void printToFile(){
        try (BufferedWriter out = new BufferedWriter(new FileWriter("tree.txt"))) {
            printToFile(out,root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        print(root);
        return "Height: " + root.getHeight(0);
    }
}
