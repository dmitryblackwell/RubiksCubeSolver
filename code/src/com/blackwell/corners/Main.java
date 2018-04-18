package com.blackwell.corners;

import com.blackwell.corners.cube.CubeCorners;
import com.blackwell.corners.search.TreeCorner;

public class Main {
    public static void main(String[] args) {
        CubeCorners cube = new CubeCorners();
        String answer = cube.scramble(5);

        System.out.print("Scramble: ");
        for(int i=answer.length()-1; i>=0; --i)
            System.out.print(answer.charAt(i) +"` ");

        System.out.println("Answer: " + answer);
        System.out.println("____________");

        long start = System.nanoTime();

        TreeCorner treeCorner = new TreeCorner(cube);

        System.out.println( (System.nanoTime() - start)*Math.pow(10,-9) + " seconds");

        treeCorner.search();
        treeCorner.printToFile();

        System.out.println( (System.nanoTime()-start)*Math.pow(10,-9) + " seconds");
    }
}
