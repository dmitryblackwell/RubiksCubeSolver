package com.blackwell;

public class EncodeEdge implements EncodeStrategy {

    private int groupStart;
    private int groupEnd;

    public EncodeEdge(CubieGroup group) {

        if (group == CubieGroup.EDGE_ONE){
            groupStart = 8;
            groupEnd = 14;
        }
        else if (group == CubieGroup.EDGE_TWO){
            groupStart = 14;
            groupEnd = 20;
        }
    }

    @Override
    public int doEncode(RubiksCube aCube) {
        int[] shiftFactors = new int[EDGE_LENGTH];
        int encoding = 0;

        for(int i=groupStart, k=0; i<groupEnd; ++i, ++k){
            int position = aCube.state[i] / EDGE_DIVISOR;

            encoding += EDGE_WEIGHTS[k] * (aCube.state[i] - shiftFactors[position]);
            for(int j = (position+1); j<shiftFactors.length; ++j)
                shiftFactors[j] += EDGE_DIVISOR;
        }

        return encoding;
    }

}
