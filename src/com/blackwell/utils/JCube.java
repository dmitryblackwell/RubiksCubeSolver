package com.blackwell.utils;

import java.util.Arrays;

public class JCube {
    public static final byte SIDES = 6;
    private static final byte STICKERS = 9;


    byte[][] cube = new byte[SIDES][STICKERS];


    public enum Side {
        U(0), F(1), R(2), D(3), L(4), B(5);
        private byte n;

        public byte toByte() {
            return n;
        }

        Side(int n){
            this.n = (byte) n;
        }
    }
    private enum Sticker {
        W(0), G(1), R(2), Y(3), O(4), B(5);
        private byte n;

        public byte toByte() {
            return n;
        }

        Sticker(int n){
            this.n = (byte) n;
        }
    }
    // UUUUUUUUU RRRRRRRRR FFFFFFFFF DDDDDDDDD LLLLLLLLL BBBBBBBBB
    // W(0), G(1), R(2), Y(3), O(4), B(5);
    // U     F     R     D     L     B

    public JCube(String cubeStr){

    }

    public JCube(){
        for(byte i=0; i<SIDES; ++i)
            for(byte j=0; j<STICKERS; ++j)
                cube[i][j] = i;
    }

    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();
        for(byte i=0; i<SIDES; ++i) {
            sb.append(Side.values()[i]).append(":[ ");
            for (byte j = 0; j < STICKERS; ++j) {
                sb.append(Sticker.values()[cube[i][j]]).append(" ");
                if (j==2 || j==5)
                    sb.append(" ");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }

    public void rotate(String s) {
        switch (s){
            case "U": rotate(Side.U, 1); break;
            case "U2": rotate(Side.U, 2); break;
            case "U'": rotate(Side.U, 3); break;

            case "R": rotate(Side.R, 1); break;
            case "R2": rotate(Side.R, 2); break;
            case "R'": rotate(Side.R, 3); break;

            case "F": rotate(Side.F, 1); break;
            case "F2": rotate(Side.F, 2); break;
            case "F'": rotate(Side.F, 3); break;

            case "D": rotate(Side.D, 1); break;
            case "D2": rotate(Side.D, 2); break;
            case "D'": rotate(Side.D, 3); break;

            case "L": rotate(Side.L, 1); break;
            case "L2": rotate(Side.L, 2); break;
            case "L'": rotate(Side.L, 3); break;

            case "B": rotate(Side.B, 1); break;
            case "B2": rotate(Side.B, 2); break;
            case "B'": rotate(Side.B, 3); break;


        }
    }

    public void rotate(Side s, int count){
        for(int i=0; i<count; ++i)
            rotate(s);
    }

    public void rotate(Side s){
        switch (s){
            case B: rotateBack(); break;
            case D: rotateDown(); break;
            case F: rotateFace(); break;
            case L: rotateLeft(); break;
            case R: rotateRight(); break;
            case U: rotateUp(); break;
        }
    }



    private void rotate(byte side, byte[][] s){
        // swap on side
        byte cornerTmp= cube[side][0];
        byte edgeTmp = cube[side][1];

        byte[] corners = {0, 6, 8, 2};
        byte[] edges   = {1, 3, 7, 5};

        for(int i = 0; i<3; ++i){
            cube[side][corners[i]] = cube[side][corners[i+1]];
            cube[side][edges[i]] = cube[side][edges[i+1]];
        }

        cube[side][2] = cornerTmp;
        cube[side][5] = edgeTmp;

        // save to tmp
        byte[] tmp = new byte[3];
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

    public void rotateLeft(){
        rotate( Side.L.toByte(),
                new byte[][] {  {Side.U.toByte(), 0,3,6},
                        {Side.B.toByte(), 8,5,2},
                        {Side.D.toByte(), 0,3,6},
                        {Side.F.toByte(), 0,3,6} });
        // U036 -> B852 -> D036 -> F036
        // ________________LEFT:
        // tmp  <- U036
        // U036 <- B852
        // B852 <- D036
        // D036 <- F036
        // F036 <- tmp
    }
    public void rotateRight(){
        rotate( Side.R.toByte(),
                new byte[][] {  {Side.U.toByte(), 2,5,8},
                        {Side.F.toByte(), 2,5,8},
                        {Side.D.toByte(), 2,5,8},
                        {Side.B.toByte(), 6,3,0} });
        // U258 -> F258 -> D258 -> B630
        // ________________RIGHT:
        // tmp  <- U258
        // U258 <- F258
        // F258 <- D258
        // D258 <- B630
        // B630 <- tmp
    }


    public void rotateUp(){
        rotate( Side.U.toByte(),
                new byte[][] {  {Side.F.toByte(), 0,1,2},
                        {Side.R.toByte(), 0,1,2},
                        {Side.B.toByte(), 0,1,2},
                        {Side.L.toByte(), 0,1,2} });
        // F012 -> R012 -> B012 -> L012
        // ________________UP:
        // tmp  <- F012
        // F012 <- R012
        // R012 <- B012
        // B012 <- L012
        // L012 <- tmp
    }

    public void rotateDown(){
        rotate( Side.D.toByte(),
                new byte[][] {  {Side.F.toByte(), 6,7,8},
                        {Side.L.toByte(), 6,7,8},
                        {Side.B.toByte(), 6,7,8},
                        {Side.R.toByte(), 6,7,8} });
        // F678 -> L678 -> B678 -> R678
        // ________________DOWN:
        // tmp  <- F678
        // F678 <- L678
        // L678 <- B678
        // B678 <- R678
        // R678 <- tmp
    }

    public void rotateFace() {
        rotate( Side.F.toByte(),
                new byte[][] {  {Side.U.toByte(), 6,7,8},
                        {Side.L.toByte(), 2,5,8},
                        {Side.D.toByte(), 0,1,2},
                        {Side.R.toByte(), 0,3,6} });
        // U678 -> L258 -> D012 -> R036
        // ________________FACE:
        // tmp  <- U678
        // U678 <- L258
        // L258 <- D012
        // D012 <- R036
        // R036 <- tmp
    }
    public void rotateBack(){
        rotate( Side.B.toByte(),
                new byte[][] {  {Side.U.toByte(), 0,1,2},
                        {Side.R.toByte(), 2,5,8},
                        {Side.D.toByte(), 6,7,8},
                        {Side.L.toByte(), 6,3,0} });
        // U012 -> R258 -> D678 -> L630
        // ________________BACK
        // tmp  <- U012
        // U012 <- R258
        // R258 <- D678
        // D678 <- L630
        // L630 <- tmp
    }

}
