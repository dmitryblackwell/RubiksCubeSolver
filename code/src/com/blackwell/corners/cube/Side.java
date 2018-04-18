package com.blackwell.corners.cube;


public enum Side{
    U(0), F(1), R(2), D(3), L(4), B(5);
    private byte n;

    public byte get() {
        return n;
    }

    Side(int n){
        this.n = (byte) n;
    }
}