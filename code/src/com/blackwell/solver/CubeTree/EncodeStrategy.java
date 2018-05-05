package com.blackwell.solver.CubeTree;

import com.blackwell.solver.cube.Cube;

public interface EncodeStrategy {
    int doEncode(Cube cube);

    int[] CORNER_WEIGHTS = {3_674_160, 174_960, 9_720, 648, 54, 6, 1, 0};
    int[] EDGE_WEIGHTS = {1_774_080, 80_640, 4_032, 224, 14, 1};
    enum CubieGroup {CORNER, EDGE_ONE, EDGE_TWO}
}
