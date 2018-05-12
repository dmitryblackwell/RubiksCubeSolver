package com.blackwell;

import java.util.Arrays;
public class RubiksCube implements EncodeConsts{

    byte[] state;
    public RubiksCube(){
        this.state = new byte[Cubie.values().length];
        setToSolvedState();
    }
    public RubiksCube(byte[] state){
        this.state = Arrays.copyOf(state,state.length);
    }

    public RubiksCube performRotation(Rotation rotation, Face face) {
        switch (rotation){
            case CLOCKWISE:
                return rotateCW(face);
            case COUNTER_CLOCKWISE:
                return rotateCCW(face);
            case HALF_TURN:
                return rotateHT(face);
        }
        return null;
    }

    private RubiksCube rotateCW (Face face) { return rotate(face, Rotation.CLOCKWISE); }
    private RubiksCube rotateCCW (Face face) { return rotate(face, Rotation.COUNTER_CLOCKWISE); }
    private RubiksCube rotateHT (Face face) { return rotate(face, Rotation.HALF_TURN); }

    public RubiksCube rotate(Face face, Rotation rotation){
        int offset = 0;
        if (face == Face.LEFT || face == Face.RIGHT)
            offset = 1;
        if (face == Face.TOP || face == Face.BOTTOM)
            offset = 2;

        return doRotate(rotation, face, offset,
                findCubieAtPosition(face.top_left),
                findCubieAtPosition(face.top_right),
                findCubieAtPosition(face.bottom_right),
                findCubieAtPosition(face.bottom_left),
                findCubieAtPosition(face.top),
                findCubieAtPosition(face.right),
                findCubieAtPosition(face.bottom),
                findCubieAtPosition(face.left));
    }

    private int findCubieAtPosition(int pos) {
        int index = -1;
        int start, end, divisor, normalizer;

        Cubie[] cubies = Cubie.values();
        if (pos >= CORNER_LENGTH){
            start = CORNER_LENGTH;
            end = cubies.length;
            divisor = EDGE_DIVISOR;
            normalizer = CORNER_LENGTH;
        }
        else {
            start = 0;
            end = CORNER_LENGTH;
            divisor = CORNER_DIVISOR;
            normalizer = 0;
        }

        for (int i=start; i<end; ++i){
            index = cubies[i].ordinal();

            int cubiePos = state[index] / divisor + normalizer;
            if (cubiePos == pos)
                break;
        }
        return index;
    }

    private RubiksCube doRotate(Rotation aDirection, Face aFace, int offset,
                                int tlCubieIdx, int trCubieIdx, int brCubieIdx, int blCubieIdx,
                                int topCubieIdx, int rightCubieIdx, int bottomCubieIdx, int leftCubieIdx) {
        byte[] toReturn = new byte[Cubie.values().length];

        for(int i=0; i<state.length; ++i)
            toReturn[i] = state[i];

        int offsetOne = 0;
        int offsetTwo = 0;

        switch (offset){
            case 0:
                offsetOne = 0;
                offsetTwo = 0;
                break;
            case 1:
                offsetOne = 1;
                offsetTwo = 2;
                break;
            case 2:
                offsetOne = 2;
                offsetTwo = 1;
                break;
        }


        // Calculate the orientations of the corner cubies
        int tlOrien = state[tlCubieIdx] % 3;
        int trOrien = state[trCubieIdx] % 3;
        int brOrien = state[brCubieIdx] % 3;
        int blOrien = state[blCubieIdx] % 3;

        // Calculate the orientations of the edge cubies.
        int topOrien = state[topCubieIdx] % 2;
        int rightOrien = state[rightCubieIdx] % 2;
        int bottomOrien = state[bottomCubieIdx] % 2;
        int leftOrien = state[leftCubieIdx] % 2;

        // If this is a left or right face than we have to switch the orientation of the edge cubies.
        if (aFace == Face.LEFT || aFace == Face.RIGHT) {
            topOrien = (topOrien + 1) % 2;
            bottomOrien = (bottomOrien + 1) % 2;
            leftOrien = (leftOrien + 1) % 2;
            rightOrien = (rightOrien + 1) % 2;
        }

        if (aDirection == Rotation.CLOCKWISE) {

            // Rotate the corner cubies
            toReturn[tlCubieIdx] = (byte) ((aFace.top_right * 3) + ((tlOrien + offsetOne) % 3));
            toReturn[trCubieIdx] = (byte) ((aFace.bottom_right * 3) + ((trOrien + offsetTwo) % 3));
            toReturn[brCubieIdx] = (byte) ((aFace.bottom_left * 3) + ((brOrien + offsetOne) % 3));
            toReturn[blCubieIdx] = (byte) ((aFace.top_left * 3) + ((blOrien + offsetTwo) % 3));

            // Rotate the edge cubies
            toReturn[topCubieIdx] = (byte) ((aFace.right -8) * 2 + topOrien);
            toReturn[rightCubieIdx] = (byte) ((aFace.bottom-8) * 2 + rightOrien);
            toReturn[bottomCubieIdx] = (byte) ((aFace.left-8) * 2 + bottomOrien);
            toReturn[leftCubieIdx] = (byte) ((aFace.top-8) * 2 + leftOrien);

        } else if (aDirection == Rotation.COUNTER_CLOCKWISE) {

            // Rotate the corner cubies
            toReturn[tlCubieIdx] = (byte) ((aFace.bottom_left * 3) + ((tlOrien + offsetOne) % 3));
            toReturn[trCubieIdx] = (byte) ((aFace.top_left * 3) + ((trOrien + offsetTwo) % 3));
            toReturn[brCubieIdx] = (byte) ((aFace.top_right * 3) + ((brOrien + offsetOne) % 3));
            toReturn[blCubieIdx] = (byte) ((aFace.bottom_right * 3) + ((blOrien + offsetTwo) % 3));

            // Rotate the edge cubies
            toReturn[topCubieIdx] = (byte) ((aFace.left-8) * 2 + topOrien);
            toReturn[rightCubieIdx] = (byte) ((aFace.top-8) * 2 + rightOrien);
            toReturn[bottomCubieIdx] = (byte) ((aFace.right-8) * 2 + bottomOrien);
            toReturn[leftCubieIdx] = (byte) ((aFace.bottom-8)  * 2 + leftOrien);
        }

        return new RubiksCube(toReturn);
    }

    public boolean isSolved(){
        for(byte i=0; i<EncodeStrategy.CORNER_LENGTH; ++i)
            if (state[i] != (byte) (i*3))
                return false;

        for(byte i=EncodeStrategy.CORNER_LENGTH; i<state.length; ++i)
            if (state[i] != (byte) ((i-EncodeStrategy.CORNER_LENGTH)*2))
                return false;

        return true;
    }
    private void setToSolvedState(){
        for(byte i=0; i<EncodeStrategy.CORNER_LENGTH; ++i)
            state[i] = (byte) (i*3);

        for(byte i=EncodeStrategy.CORNER_LENGTH; i<state.length; ++i)
            state[i] = (byte) ((i-EncodeStrategy.CORNER_LENGTH)*2);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[ ");
        for (int x : state)
            sb.append(x).append(" ");
        sb.append(" ]");

        return sb.toString();
    }

    public int getCornerEncoding() { return cornerEncoder.doEncode(this); }
    public int getEdgeOneEncoding() { return edgeOneEncoder.doEncode(this); }
    public int getEdgeTwoEncoding() { return edgeTwoEncoder.doEncode(this); }

    private static EncodeStrategy cornerEncoder = new EncodeCorner();
    private static EncodeStrategy edgeOneEncoder = new EncodeEdge(CubieGroup.EDGE_ONE);
    private static EncodeStrategy edgeTwoEncoder = new EncodeEdge(CubieGroup.EDGE_TWO);
}
