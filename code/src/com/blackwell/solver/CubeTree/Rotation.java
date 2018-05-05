package com.blackwell.solver.CubeTree;

import com.blackwell.solver.cube.Cube;
import com.blackwell.solver.cube.PowerOfTheMove;
import com.blackwell.solver.cube.Side;

public class Rotation {
    private Side s;
    private PowerOfTheMove po;

    public Rotation(Side s, PowerOfTheMove po) {
        this.s = s;
        this.po = po;
    }

    public int getSideInt() { return s.ordinal(); }

    public Side getSide() { return s; }

    public int getPowerOfTheMoveInt() { return po.ordinal(); }

    public static Rotation[] getPossibleRotations(){
        Rotation[] possibleRotations = new Rotation[18];
        for(int i=0; i<Cube.SIDES; ++i)
            for(int j=0; j<3; ++j)
                possibleRotations[i*3 + j] = new Rotation(Side.values()[i], PowerOfTheMove.values()[j]);
        return possibleRotations;
    }
    // 0 1 2
    // 3 4 5
    // 6 7 8
    // 9 10 11
    // 12

}

