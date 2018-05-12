package com.blackwell;

public interface EncodeConsts {

    int[] CORNER_WEIGHTS = {3_674_160, 174_960, 9_720, 648, 54, 6, 1, 0};
    int[] EDGE_WEIGHTS = {1_774_080, 80_640, 4_032, 224, 14, 1};


    int CORNER_LENGTH = 8;
    int EDGE_LENGTH=12;

    int EDGE_DIVISOR = 2;
    int CORNER_DIVISOR = 3;
}
