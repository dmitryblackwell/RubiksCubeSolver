package com.blackwell;

public class CubeNode {
    CubeNode parent;
    RubiksCube state;
    Rotation rotation;
    Face face;
    int heuristic = 0;
    int permId = -1;

    public CubeNode(CubeNode parent, RubiksCube state, Rotation rotation, Face face, int heuristic, int permId) {
        this.parent = parent;
        this.state = state;
        this.rotation = rotation;
        this.face = face;
        this.heuristic = heuristic;
        this.permId = permId;
    }
}
