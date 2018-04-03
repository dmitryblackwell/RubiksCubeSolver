package com.blackwell;

public class Node {

    private String rotations;
    private Cube cube;
    private Node[] sons = new Node[6];

    public static final int MAX_DEPTH = 10; // 6 -so-so
    private static boolean isSolved = false;
    public static String solution = "";

    public Node(String r,Cube c) {
        /*String log = r+"___"+r.length()+"___"+"\r\n";
        System.out.println(log);*/

        rotations = r;
        cube = new Cube(c);

        if (rotations.length() == 1)
            System.out.println("haha");

        if (cube.isSolved()){
            solution = rotations;
            isSolved = true;
        }
        else if(rotations.length() < MAX_DEPTH && !isSolved) {
            sons[0] = new Node(rotations + "L", (new Cube(cube)).rotateLeft());
            sons[1] = new Node(rotations + "R", (new Cube(cube)).rotateRight());
            sons[2] = new Node(rotations + "F", (new Cube(cube)).rotateFace());
            sons[3] = new Node(rotations + "B", (new Cube(cube)).rotateBack());
            sons[4] = new Node(rotations + "U", (new Cube(cube)).rotateUp());
            sons[5] = new Node(rotations + "D", (new Cube(cube)).rotateDown());
        }
    }

    public void Run(){

    }

    public Node getSon(int n){
        return sons[n];
    }

    public boolean isNodeSolved(){
        return cube.isSolved();
    }

    public String getRotations() {
        return rotations;
    }

}
