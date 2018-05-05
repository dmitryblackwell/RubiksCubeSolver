package com.blackwell.solver;


import com.blackwell.solver.CubeTree.CubeSolver;
import com.blackwell.solver.cube.Cube;

public class Main {
    public static void main(String[] args) {
        long start = System.nanoTime();

        Cube cube = new Cube();
        System.out.println(cube.isSolved());
        String answer = cube.scramble(5);
        System.out.println(cube.isSolved());

        String solution = CubeSolver.findOptimalSolution(cube);

        System.out.printf("Answer: %s; %n Solution: %s; %n", answer, solution);
        System.out.println("Time running: " + (System.nanoTime()-start)*Math.pow(10,-9) + " secs");
    }
}
