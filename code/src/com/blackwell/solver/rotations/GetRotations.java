package com.blackwell.solver.rotations;

import com.blackwell.solver.CubeTree.Node;
import com.blackwell.solver.cube.Cube;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class GetRotations {

    private static final int[] rotations = { 1,2,3,4,5,6 };
    private static final int MAX_DEPTH  = 15;
    private static final String EOL = System.lineSeparator();

    private BufferedWriter out;

    public GetRotations() {
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data/solver/rotations.txt")));
            rotate(0,0);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rotate(long result, int depth) throws IOException {
        if (depth == MAX_DEPTH)
            return;

        for(int i=0; i<6; ++i) {
            long tmp = result*10 + rotations[i];
            out.append(String.valueOf(tmp)).append(EOL);
            rotate(tmp, depth + 1);
        }
    }
}
