package com.blackwell.solver;


import com.blackwell.solver.rotations.GetRotations;

public class Main {
    public static void main(String[] args) {
        long start = System.nanoTime();
        new GetRotations();
        System.out.println("Time running: " + (System.nanoTime()-start)*Math.pow(10,-9) + " secs");
    }
}
