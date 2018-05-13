package com.blackwell;

public class EncodeCorner implements EncodeStrategy {

    public int doEncode(RubiksCube aCube) {
        int[] shiftFactors = new int[CORNER_LENGTH];
        int encoding = 0;

        for(int i = 0; i< CORNER_LENGTH; ++i){
            int position = aCube.state[i] / CORNER_DIVISOR;
            int shiftedValue = aCube.state[i] - shiftFactors[position];

            encoding += CORNER_WEIGHTS[i] * shiftedValue;

            for (int j= (position+1); j<shiftFactors.length; ++j)
                shiftFactors[j] += CORNER_DIVISOR;
        }

        return encoding;
    }

}
