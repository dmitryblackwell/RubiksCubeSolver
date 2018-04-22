package com.blackwell.solver.CubeTree;

import com.blackwell.solver.cube.Cube;
import com.blackwell.solver.cube.Side;

public class Node {
    private byte rotation;
    private Cube cube;
    private Node[] nodes = new Node[Cube.SIDES];

    public Node(Cube c, byte r){
        rotation = r;
        cube = c;
    }


    public void setSons(){
        for(byte i=0; i<Cube.SIDES; ++i){
            Cube tmp = new Cube(cube);
            tmp.rotate(Side.values()[i]);
            nodes[i] = new Node(tmp, i);
        }
    }

    public byte getRotation() {
        return rotation;
    }

    public Node getSon(int i){
        return nodes[i];
    }

    public void setSon(int i, Node n) { nodes[i] = n; }

    public String getCube(){
        return cube.toDbString();
    }
}
