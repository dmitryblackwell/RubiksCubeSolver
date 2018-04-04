package com.blackwell.test;

import java.util.Random;

public class Node {

    private StringBuilder value;
    private StringBuilder cube;
    private Node[] sons = new Node[6];

    private static Random R = new Random();
    private static final int DEPTH = 11;
    public static int NodeCount = 0;
    // 10 -> 12093235 8226 ms
    public Node(StringBuilder value, StringBuilder cube) {
        NodeCount++;

        this.value = value;
        this.cube = cube;

        //System.out.println("Node "+NodeCount+" created: "+ this.value.length());

        if (this.value.length() >= DEPTH)
            return;

        StringBuilder[] cubes = new StringBuilder[6];
        for(int i=0; i<6; ++i){
            cubes[i] = new StringBuilder(cube);
            for(int j=0; j<20; ++j){
                int a = R.nextInt(54);
                int b = R.nextInt(54);
                char tmp = cubes[i].charAt(a);
                cubes[i].setCharAt(a,cubes[i].charAt(b));
                cubes[i].setCharAt(b,tmp);
            }
            StringBuilder sb = new StringBuilder(value);
            sb.append("A");
            sons[i] = new Node(sb,cubes[i]);
        }
    }
}
