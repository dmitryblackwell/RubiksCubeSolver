package com.blackwell.solver;

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
