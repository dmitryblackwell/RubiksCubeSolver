package com.blackwell.solver.CubeTree;

import com.blackwell.solver.cube.Cube;
import com.blackwell.solver.cube.Side;

public class Node {
    private Node parent;
    private Cube cube;

    private Rotation rotation;
    private int heuristic;

    public Node(Node parent,Cube cube, Rotation rotation, int heuristic){
        this.parent = parent;
        this.rotation = rotation;
        this.cube = cube;
        this.heuristic = heuristic;
    }

    public int getHeuristic() { return heuristic; }
    public void setHeuristic(int heuristic) { this.heuristic = heuristic; }
    public Rotation getRotation() {
        return rotation;
    }
    public Cube getCube(){
        return cube;
    }
    public boolean isSolved() { return cube.isSolved(); }
}
