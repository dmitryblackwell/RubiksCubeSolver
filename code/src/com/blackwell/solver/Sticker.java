package com.blackwell.solver;

public enum Sticker {
    W(0), G(1), R(2), Y(3), O(4), B(5);
    private byte n;

    public byte toByte() {
        return n;
    }

    Sticker(int n){
        this.n = (byte) n;
    }
}