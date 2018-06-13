package com.blackwell.utils;

public class Cube {
    private static final byte SIDES = 6;
    private static final byte STICKERS = 9;
    private static final String SOLVED_STATE = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB";

    private enum Side { U, F, R, D, L, B }
    private char[][] cube = new char[SIDES][STICKERS];

    public Cube(String c){
        for(int i=0; i<SIDES; ++i)
            cube[i] = c.substring(i*STICKERS, (i+1)*STICKERS).toCharArray();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<SIDES; ++i)
            for(int j=0; j<STICKERS; ++j)
                sb.append(cube[i][j]);
        return sb.toString();
    }

    /**
     * Find and make alternative rotation.
     * For example: alternative rotation for R' is R,
     * for D is D'
     * for F2 is F2
     * @param rotation axis and power of the move
     */
    public void alternative(String rotation){
        switch (rotation){
            case "U'": rotate("U"); break;
            case "U": rotate("U'"); break;

            case "R": rotate("R'"); break;
            case "R'": rotate("R"); break;

            case "F": rotate("F'"); break;
            case "F'": rotate("F"); break;

            case "D": rotate("D'"); break;
            case "D'": rotate("D"); break;

            case "L": rotate("L'"); break;
            case "L'": rotate("L"); break;

            case "B": rotate("B'"); break;
            case "B'": rotate("B"); break;

            default: rotate(rotation); break;


        }
    }

    public boolean isSolved(){
        return SOLVED_STATE.equals(toString());
    }

    public void rotate(String rotation){
        //System.out.println("Rotating: " + rotation);
        switch (rotation){
            case "U'": rotate(Side.U);
            case "U2": rotate(Side.U);
            case "U": rotate(Side.U); break;

            case "R'": rotate(Side.R);
            case "R2": rotate(Side.R);
            case "R": rotate(Side.R); break;

            case "F'": rotate(Side.F);
            case "F2": rotate(Side.F);
            case "F": rotate(Side.F); break;

            case "D'": rotate(Side.D);
            case "D2": rotate(Side.D);
            case "D": rotate(Side.D); break;

            case "L'": rotate(Side.L);
            case "L2": rotate(Side.L);
            case "L": rotate(Side.L); break;

            case "B'": rotate(Side.B);
            case "B2": rotate(Side.B);
            case "B": rotate(Side.B); break;

        }
    }



    private void rotate(Side s){
        switch (s){
            case B: rotateBack(); break;
            case D: rotateDown(); break;
            case F: rotateFace(); break;
            case L: rotateLeft(); break;
            case R: rotateRight(); break;
            case U: rotateUp(); break;
        }
    }
    private static final int U = 0;
    private static final int R = 1;
    private static final int F = 2;
    private static final int D = 3;
    private static final int L = 4;
    private static final int B = 5;

    private void rotate(int side, byte[][] s){
        // swap on side
        char cornerTmp= cube[side][0];
        char edgeTmp = cube[side][1];

        byte[] corners = {0, 6, 8, 2};
        byte[] edges   = {1, 3, 7, 5};

        for(int i = 0; i<3; ++i){
            cube[side][corners[i]] = cube[side][corners[i+1]];
            cube[side][edges[i]] = cube[side][edges[i+1]];
        }

        cube[side][2] = cornerTmp;
        cube[side][5] = edgeTmp;

        // save to tmp
        char[] tmp = new char[3];
        for(int i=1; i<=3; ++i)
            tmp[i-1] = cube[s[0][0]][s[0][i]];

        // swap
        for(int j=0; j<3; ++j)
            for(int i=1; i<=3; ++i)
                cube[s[j][0]][s[j][i]] = cube[s[j+1][0]][s[j+1][i]];

        // extract from tmp
        for(int i=1; i<=3; ++i)
            cube[s[3][0]][s[3][i]] = tmp[i-1];
    }

    private void rotateLeft(){
        rotate(L,
                new byte[][] {  {U, 0,3,6},
                        {B, 8,5,2},
                        {D, 0,3,6},
                        {F, 0,3,6} });
        // U036 -> B852 -> D036 -> F036
        // ________________LEFT:
        // tmp  <- U036
        // U036 <- B852
        // B852 <- D036
        // D036 <- F036
        // F036 <- tmp
    }

    private void rotateRight(){
        rotate( R,
                new byte[][] {  {U, 2,5,8},
                        {F, 2,5,8},
                        {D, 2,5,8},
                        {B, 6,3,0} });
        // U258 -> F258 -> D258 -> B630
        // ________________RIGHT:
        // tmp  <- U258
        // U258 <- F258
        // F258 <- D258
        // D258 <- B630
        // B630 <- tmp
    }


    private void rotateUp(){
        rotate( U,
                new byte[][] {  {F, 0,1,2},
                        {R, 0,1,2},
                        {B, 0,1,2},
                        {L, 0,1,2} });
        // F012 -> R012 -> B012 -> L012
        // ________________UP:
        // tmp  <- F012
        // F012 <- R012
        // R012 <- B012
        // B012 <- L012
        // L012 <- tmp
    }

    private void rotateDown(){
        rotate( D,
                new byte[][] {  {F, 6,7,8},
                        {L, 6,7,8},
                        {B, 6,7,8},
                        {R, 6,7,8} });
        // F678 -> L678 -> B678 -> R678
        // ________________DOWN:
        // tmp  <- F678
        // F678 <- L678
        // L678 <- B678
        // B678 <- R678
        // R678 <- tmp
    }

    private void rotateFace() {
        rotate( F,
                new byte[][] {  {U, 6,7,8},
                        {L, 2,5,8},
                        {D, 0,1,2},
                        {R, 0,3,6} });
        // U678 -> L258 -> D012 -> R036
        // ________________FACE:
        // tmp  <- U678
        // U678 <- L258
        // L258 <- D012
        // D012 <- R036
        // R036 <- tmp

        char tmp = cube[D][0];
        cube[D][0] = cube[D][2];
        cube[D][2] = tmp;

        tmp = cube[U][8];
        cube[U][8] = cube[U][6];
        cube[U][6] = tmp;
    }
    private void rotateBack(){
        rotate( B,
                new byte[][] {  {U, 0,1,2},
                        {R, 2,5,8},
                        {D, 6,7,8},
                        {L, 6,3,0} });
        // U012 -> R258 -> D678 -> L630
        // ________________BACK
        // tmp  <- U012
        // U012 <- R258
        // R258 <- D678
        // D678 <- L630
        // L630 <- tmp
        char tmp = cube[D][6];
        cube[D][6] = cube[D][8];
        cube[D][8] = tmp;

        tmp = cube[R][2];
        cube[R][2] = cube[R][8];
        cube[R][8] = tmp;
    }
}
