package com.blackwell.cube;

import java.util.Arrays;

public class CubeCorners {
    enum Color {
        W(0), G(1), R(2), Y(3), O(4), B(5);
        private byte n;
        public byte get() {return n;}
        Color(int n){this.n = (byte) n;}
    }
    private byte[][] cube= new byte[6][4];

    public CubeCorners(){
        for(int i=0; i<6; ++i){
            Arrays.fill( cube[Side.values()[i].get()], Color.values()[i].get());
        }
    }
    public CubeCorners(CubeCorners c){
        setCubeTo(c);
    }
    public void setCubeTo(CubeCorners c){
        for(int i=0; i<6; ++i){
            System.arraycopy(c.cube[i],0,cube[i],0,4);
        }
    }

    class Line{
        public int s;
        public int v1;
        public int v2;
        public Line(Side side, int v1,int v2){
            s = side.get();
            this.v1 = v1;
            this.v2 = v2;
        }
    }

    private void rotate(int s, Line a, Line b, Line c, Line d){

        byte tmp = cube[s][0];
        cube[s][0] = cube[s][3];
        cube[s][3] = cube[s][2];
        cube[s][2] = cube[s][1];
        cube[s][1] = tmp;

        //U03 -> B21 -> D03 -> F03

        //tmp = U03
        byte v1 = cube[a.s][a.v1];
        byte v2 = cube[a.s][a.v2];

        //U03 = B21
        cube[a.s][a.v1] = cube[b.s][b.v1];
        cube[a.s][a.v2] = cube[b.s][b.v2];

        //B21 = D03
        cube[b.s][b.v1] = cube[c.s][c.v1];
        cube[b.s][b.v2] = cube[c.s][c.v2];

        //D03 = F03
        cube[c.s][c.v1] = cube[d.s][d.v1];
        cube[c.s][c.v2] = cube[d.s][d.v2];

        //F03 = tmp
        cube[d.s][d.v1] = v1;
        cube[d.s][d.v2] = v2;
    }
    public void left(){
        //U03 -> B21 -> D03 -> F03
        rotate( Side.L.get(),
                new Line(Side.U, 0, 3),
                new Line(Side.B, 2, 1),
                new Line(Side.D, 0, 3),
                new Line(Side.F, 0, 3));
        //tmp = U03
        //U03 = B21
        //B21 = D03
        //D03 = F03
        //F03 = tmp
    }

    public void right(){
        // U12 -> F12 -> D12 -> B30
        rotate( Side.R.get(),
                new Line(Side.U, 1, 2),
                new Line(Side.F, 1, 2),
                new Line(Side.D, 1, 2),
                new Line(Side.B, 3, 0));
        // tmp = U12
        // U12 = F12
        // F12 = D12
        // D12 = B30
        // B30 = tmp
    }

    public void face(){
        // U23 -> L12 -> D01 -> R30
        rotate( Side.F.get(),
                new Line(Side.U, 2, 3),
                new Line(Side.L, 1, 2),
                new Line(Side.D, 0, 1),
                new Line(Side.R, 3, 0));
        // tmp = U23
        // U23 = L12
        // L12 = D01
        // D01 = R30
        // R30 = tmp
    }

    public void back(){
        // U01 -> R12 -> D23 -> L30
        rotate( Side.B.get(),
                new Line(Side.U, 0, 1),
                new Line(Side.R, 1, 2),
                new Line(Side.D, 2, 3),
                new Line(Side.L, 3, 0));
        // tmp = U01
        // U01 = R12
        // R12 = D23
        // D23 = L30
        // L30 = tmp
    }

    public void up(){
        // F01 -> R01 -> B01 -> L01
        rotate( Side.U.get(),
                new Line(Side.F, 0, 1),
                new Line(Side.R, 0, 1),
                new Line(Side.B, 0, 1),
                new Line(Side.L, 0, 1));
        // tmp = F01
        // F01 = R01
        // R01 = B01
        // B01 = L01
        // L01 = tmp
    }

    public void down(){
        // F23 -> L23 -> B23 -> R23
        rotate( Side.D.get(),
                new Line(Side.F, 2, 3),
                new Line(Side.L, 2, 3),
                new Line(Side.B, 2, 3),
                new Line(Side.R, 2, 3));
        // tmp = F23
        // F23 = L23
        // L23 = B23
        // B23 = R23
        // R23 = tmp
    }

    public CubeCorners getLeft(){
        left();
        return this;
    }
    public CubeCorners getRight(){
        right();
        return this;
    }
    public CubeCorners getFace(){
        face();
        return this;
    }
    public CubeCorners getBack(){
        back();
        return this;
    }
    public CubeCorners getUp(){
        up();
        return this;
    }
    public CubeCorners getDown(){
        down();
        return this;
    }

    public boolean isSolved(){
        for(int i=0; i<6; ++i) {
            for (int j=0; j<4; ++j){
                if (cube[i][j] != Color.values()[i].get())
                    return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<6; ++i){
            builder.append(Side.values()[i]).append(": [ ");
            for (int j=0; j<4; ++j){
                builder.append(Color.values()[cube[i][j]]).append(" ");
            }
            builder.append("]\n");
        }
        return builder.toString();
    }

    public boolean equals(CubeCorners c) {
        for(int i=0; i<6; ++i){
            for(int j=0; j<4; ++j){
                if(cube[i][j] != c.cube[i][j])
                    return false;
            }
        }
        return true;
    }
}
