package com.blackwell.hints;

import java.util.Random;

public class Cube {
    private static final int SIDES_LENGTH = 6;
    private Facelet[] faces = new Facelet[SIDES_LENGTH];

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int FACE = 2;
    private static final int BACK = 3;
    private static final int LEFT = 4;
    private static final int RIGHT = 5;
    //private enum Side {UP, DOWN, FACE, BACK, LEFT, RIGHT}

    private void CubeInit(char[][] c){
        for(int i=0; i<SIDES_LENGTH; ++i)
            faces[i] = new Facelet(c[i]);
    }

    public Cube(char[] u, char[] d, char[] f, char[] b, char[] l, char[] r){
        char[][] sides = {u,d,f,b,l,r};
        CubeInit(sides);
    }
    public Cube(Cube c){
        for(int i=0; i<SIDES_LENGTH; ++i)
            faces[i] = new Facelet(c.faces[i]);
    }
    public Cube(){
        char[][] sides = {  {'U','U','U', 'U','U','U', 'U','U','U'},
                            {'Y','Y','Y', 'Y','Y','Y', 'Y','Y','Y'},
                            {'G','G','G', 'G','G','G', 'G','G','G'},
                            {'B','B','B', 'B','B','B', 'B','B','B'},
                            {'O','O','O', 'O','O','O', 'O','O','O'},
                            {'R','R','R', 'R','R','R', 'R','R','R'} };
        CubeInit(sides);
    }
    public boolean isSolved(){
        for (Facelet f : faces)
            if (!f.isComplete())
                return false;
        return true;
    }

    /*public Cube rotateLeft(){
        faces[LEFT].rotate();
        char[] tmp = faces[UP].get(0,3,6); // tmp <- U036
        faces[UP].set(0,3,6, faces[BACK].get(2,5,8));// U036 <- B258
        faces[BACK].set(2,5,8,faces[DOWN].get(6,3,0));// B258 <- D630
        faces[DOWN].set(0,3,6,faces[FACE].get(0,3,6));// D036 <- F036
        faces[FACE].set(0,3,6,tmp);// F036 <- tmp
        return this;
    }*/

    // When you trying to write awesome code you get this:
    private void rotate(final int ROTATE, byte[][] n){ //TOTALLY NOT FUCKING READABLE
        faces[ROTATE].rotate();
        char[] tmp = faces[n[0][0]].get(n[0][1],n[0][2],n[0][3]);
        for(int i=1; i<=3; ++i)
            faces[n[i][0]].set(n[i][1],n[i][2],n[i][3],
                    faces[n[i][4]].get(n[i][5],n[i][6],n[i][7]));
        faces[n[4][0]].set(n[4][1],n[4][2],n[4][3],tmp);
    }
    public Cube rotateLeft(){
        rotate(LEFT, new byte[][]{
                {0,0,3,6},
                {0,0,3,6,3,2,5,8},
                {3,2,5,8,1,6,3,0},
                {1,6,3,0,2,0,3,6},
                {2,0,3,6}});
        // ________________LEFT:
        // tmp <- U036
        // U036 <- B258
        // B258 <- D630
        // D036 <- F036
        // F036 <- tmp
        return this;
    }
    public Cube rotateRight(){
        rotate(RIGHT, new byte[][]{
                {0,2,5,8},
                {0,2,5,8,2,2,5,8},
                {2,2,5,8,1,2,5,8},
                {1,2,5,8,3,6,3,0},
                {3,6,3,0}});
        // ________________RIGHT:
        // tmp <- UP 2 5 8
        // UP 2 5 8 <- FACE 2 5 8
        // FACE 2 5 8 <- DOWN 2 5 8
        // DOWN 2 5 8 <- BACK 6 3 0
        // BACK 6 3 0 <- tmp
        return this;
    }


    public Cube rotateUp(){
        rotate(UP, new byte[][]{
                {2,0,1,2},
                {2,0,1,2,5,0,1,2},
                {5,0,1,2,3,0,1,2},
                {3,0,1,2,4,0,1,2},
                {4,0,1,2}});
        // ________________UP:
        // tmp <- F012
        // F012 <- R012
        // R012 <- B012
        // B012 <- L012
        // L012 <- tmp
        return this;
    }

    public Cube rotateDown(){
        rotate(DOWN, new byte[][]{
                {2,6,7,8},
                {2,6,7,8,4,6,7,8},
                {4,6,7,8,3,6,7,8},
                {3,6,7,8,5,6,7,8},
                {5,6,7,8}});
        // ________________DOWN:
        // tmp <- F678
        // F678 <- L678
        // L678 <- B678
        // B678 <- R678
        // R678 <- tmp
        return this;
    }

    public Cube rotateFace(){
        rotate(FACE, new byte[][]{
                {0,6,7,8},
                {0,6,7,8,4,2,5,8},
                {4,2,5,8,1,0,1,2},
                {1,0,1,2,5,0,3,6},
                {5,0,3,6}});
        // tmp <- U678
        // U678 <- L258
        // L258 <- D012
        // D012 <- R036
        // R036 <- tmp
        return this;
    }

    public Cube rotateBack(){
        rotate(BACK, new byte[][]{
                {0,0,1,2},
                {0,0,1,2,5,2,5,8},
                {5,2,5,8,1,6,7,8},
                {1,6,7,8,4,0,3,6},
                {4,0,3,6}});
        // tmp <- U012
        // U012 <- R258
        // R258 <- D678
        // D678 <- L036
        // L630 <- tmp
        return this;
    }


    public String randomReverseRotation(){
        Random R = new Random();
        String s = "";
        switch (R.nextInt(6)){
            case 0:
                rotateDown();
                rotateDown();
                rotateDown();
                s = "D";
                break;
            case 1:
                rotateUp();
                rotateUp();
                rotateUp();
                s = "U";
                break;
            case 2:
                rotateBack();
                rotateBack();
                rotateBack();
                s = "B";
                break;
            case 3:
                rotateFace();
                rotateFace();
                rotateFace();
                s = "F";
                break;
            case 4:
                rotateLeft();
                rotateLeft();
                rotateLeft();
                s = "L";
                break;
            case 5:
                rotateRight();
                rotateRight();
                rotateRight();
                s = "R";
                break;
        }
        return s;
    }


    public String randomlyMoveForAnswer(){
        String moves = "";
        for(int i = 0; i<Node.MAX_DEPTH; ++i){
            moves += randomReverseRotation();
        }

        return (new StringBuilder(moves)).reverse().toString();
    }



    public String toString(){
        StringBuilder builder =new StringBuilder();

        builder.append("UP_____________\n");
        builder.append(faces[UP]);
        builder.append("DOWN___________\n");
        builder.append(faces[DOWN]);
        builder.append("FACE___________\n");
        builder.append(faces[FACE]);
        builder.append("BACK___________\n");
        builder.append(faces[BACK]);
        builder.append("LEFT___________\n");
        builder.append(faces[LEFT]);
        builder.append("RIGHT__________\n");
        builder.append(faces[RIGHT]);

        return builder.toString();
    }

}
