package com.blackwell;

public class Node {
    private String rotations;
    private Cube cube;
    private Node[] sons = new Node[6];

    public static final int MAX_DEPTH = 9 ; // 8 maximum for now
    // 8 -> 2 secs
    // 9 ->

    public Node(String r,Cube c) {
        /*String log = r+"___"+r.length()+"___"+"\r\n";
        //System.out.println(log);

        try {
            Files.write(Paths.get("log.txt"), log.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            System.out.println(e);
            //exception handling left as an exercise for the reader
        }*/

        rotations = r;
        cube = new Cube(c);
        if (rotations.length() == 1)
            System.out.println("haha");
        if(rotations.length() < MAX_DEPTH) {
            /*Cube cL = new Cube(cube);
            cL.rotateLeft();
            Cube cR = new Cube(cube);
            cR.rotateRight();
            Cube cF = new Cube(cube);
            cF.rotateFace();
            Cube cB = new Cube(cube);
            cB.rotateBack();
            Cube cU = new Cube(cube);
            cU.rotateUp();
            Cube cD = new Cube(cube);
            cD.rotateDown();*/
            sons[0] = new Node(rotations + "L", (new Cube(cube)).rotateLeft());
            sons[1] = new Node(rotations + "R", (new Cube(cube)).rotateRight());
            sons[2] = new Node(rotations + "F", (new Cube(cube)).rotateFace());
            sons[3] = new Node(rotations + "B", (new Cube(cube)).rotateBack());
            sons[4] = new Node(rotations + "U", (new Cube(cube)).rotateUp());
            sons[5] = new Node(rotations + "D", (new Cube(cube)).rotateDown());
        }
    }

    public Node getSon(int n){
        return sons[n];
    }

    /*public Cube getCube() {
        return cube;
    }*/

    public boolean isNodeSolved(){
        return cube.isSoved();
    }

    public String getRotations() {
        return rotations;
    }

}
