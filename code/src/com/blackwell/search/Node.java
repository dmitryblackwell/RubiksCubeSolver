package com.blackwell.search;

import com.blackwell.cube.CubeCorners;
import com.blackwell.cube.Side;

public class Node {
    private int rotation;
    private CubeCorners cube;
    private Node[] nodes = new Node[6];

    public boolean isSolved(){
        return cube.isSolved();
    }

    Node(int rotation, CubeCorners c){
        this.rotation = rotation;
        cube = c;
    }
    public Node getSon(int i){
        return nodes[i];
    }
    public void setUpSons(){
        for(int i=0; i<6; ++i)
            nodes[i] = null;

        nodes[0] = new Node(Side.L.get(), (new CubeCorners(cube)).getLeft());
        nodes[1] = new Node(Side.R.get(), (new CubeCorners(cube)).getRight());
        nodes[2] = new Node(Side.F.get(), (new CubeCorners(cube)).getFace());
        nodes[3] = new Node(Side.B.get(), (new CubeCorners(cube)).getBack());
        nodes[4] = new Node(Side.U.get(), (new CubeCorners(cube)).getUp());
        nodes[5] = new Node(Side.D.get(), (new CubeCorners(cube)).getDown());
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
