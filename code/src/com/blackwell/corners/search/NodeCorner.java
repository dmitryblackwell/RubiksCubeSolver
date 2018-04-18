package com.blackwell.corners.search;

import com.blackwell.corners.cube.CubeCorners;
import com.blackwell.corners.cube.Side;

public class NodeCorner {
    private int rotation;
    private CubeCorners cube;
    private NodeCorner[] nodeCorners = new NodeCorner[6];

    public boolean isSolved(){
        return cube.isSolved();
    }

    NodeCorner(int rotation, CubeCorners c){
        this.rotation = rotation;
        cube = c;
    }
    public NodeCorner getSon(int i){
        return nodeCorners[i];
    }
    public void setUpSons(){
        for(int i=0; i<6; ++i)
            nodeCorners[i] = null;

        nodeCorners[0] = new NodeCorner(Side.L.get(), (new CubeCorners(cube)).getLeft());
        nodeCorners[1] = new NodeCorner(Side.R.get(), (new CubeCorners(cube)).getRight());
        nodeCorners[2] = new NodeCorner(Side.F.get(), (new CubeCorners(cube)).getFace());
        nodeCorners[3] = new NodeCorner(Side.B.get(), (new CubeCorners(cube)).getBack());
        nodeCorners[4] = new NodeCorner(Side.U.get(), (new CubeCorners(cube)).getUp());
        nodeCorners[5] = new NodeCorner(Side.D.get(), (new CubeCorners(cube)).getDown());
    }

    public int getRotation() {
        return rotation;
    }

    @Override
    public String toString() {
        return String.valueOf(rotation) + ": \n" + cube + (isSolved() ? "solved" : "") + "\n";
    }

    int getHeight(int height){
        if( getSon(0) != null)
            return getSon(0).getHeight(++height);
        return height;
    }
}
