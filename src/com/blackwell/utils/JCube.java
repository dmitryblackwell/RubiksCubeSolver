package com.blackwell.utils;


import java.util.Arrays;

public class JCube {
    String cube;
    public JCube(String cube) {
        this.cube = cube;
    }

//    public void rotate(String rotation){
//        System.out.println("Rotating: " + rotation);
//        switch (rotation){
//            case "U'": up();
//            case "U2": up();
//            case "U": up(); break;
//
//            case "R'": right();
//            case "R2": right();
//            case "R": right(); break;
//
//            case "F'": face();
//            case "F2":face();
//            case "F": face(); break;
//
//
//        }
//    }

    // UUUUUUUUU RRRRRRRRR FFFFFFFFF DDDDDDDDD LLLLLLLLL BBBBBBBBB


    @Override
    public String toString() {
        return cube;
    }

    private static final int U = 0;
    private static final int R = 1;
    private static final int F = 2;
    private static final int D = 3;
    private static final int L = 4;
    private static final int B = 5;

    private static final byte SIDES = 6;
    private static final byte STICKERS = 9;

    private void arrayToString(char[][] c){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<SIDES; ++i)
            for(int j=0; j<STICKERS; ++j)
                sb.append(c[i][j]);
        cube = sb.toString();
    }
    private void up(){
//        String face = rotateFace(cube.substring(0,STICKERS));
//        System.out.println("Face: "+face);
//        cube = face + cube.substring(STICKERS,SIDES*STICKERS);
//
//        System.out.println("Cube: "+cube);
//
//        char[][] c = new char[SIDES][STICKERS];
//        for(int i=0; i<SIDES; ++i)
//            c[i] = cube.substring(i*STICKERS, (i+1)*STICKERS).toCharArray();
//
//        System.out.println("C: "+ Arrays.deepToString(c));
//        char[] tmp = new char[3];
//        System.arraycopy(c[F], 0, tmp, 0, tmp.length);
//        System.arraycopy(c[R], 0, c[F], 0, tmp.length);
//        System.arraycopy(c[B], 0, c[R], 0, tmp.length);
//        System.arraycopy(c[L], 0, c[B], 0, tmp.length);
//        System.arraycopy(tmp, 0, c[L], 0, tmp.length);
//        arrayToString(c);
    }

    private char[][] stringToArray(String cube){
        char[][] c = new char[SIDES][STICKERS];
        for(int i=0; i<SIDES; ++i)
            c[i] = cube.substring(i*STICKERS, (i+1)*STICKERS).toCharArray();
        return c;
    }

    private void right(){
        String face = rotateFace(cube.substring(STICKERS,2*STICKERS));
        System.out.println("Face: "+face);
        cube = cube.substring(0, STICKERS) + face + cube.substring(STICKERS*2,SIDES*STICKERS);

        System.out.println("Cube: "+cube);
        char[][] c = stringToArray(cube);

        System.out.println("C: "+ Arrays.deepToString(c));
        char[] tmp = new char[3];

        for(int i=2; i<9; i+=3)
            tmp[i/3] = c[U][i];

        for(int i=2; i<9; i+=3)
            c[U][i] = c[F][i];

        for(int i=2; i<9; i+=3)
            c[F][i] = c[D][i];







        arrayToString(c);

    }
    private void face(){

    }
    private void down(){

    }
    private void left(){

    }
    private void back(){

    }

    private String rotateFace(String rotate){
        char[] face = new char[9];

        for(int i=0; i<face.length; ++i)
            face[i] = rotate.charAt(i);

        rotateFace(face);
        StringBuilder sb = new StringBuilder();
        for (char aFace : face) sb.append(aFace);

        return sb.toString();
    }

    private void rotateFace(char[] face){
        char cornerTmp=face[0];
        face[0] = face[6];
        face[6] = face[8];
        face[8] = face[2];
        face[2] = cornerTmp;

        char edgeTmp = face[1];
        face[1] = face[3];
        face[3] = face[7];
        face[7] = face[5];
        face[5] = edgeTmp;
    }


}

// Porcelain Black And The Tramps - Gasoline