package com.blackwell;

import java.util.Arrays;
import java.util.Random;

public class Cube {
    Facelet up;
    Facelet down;
    Facelet face;
    Facelet back;
    Facelet left;
    Facelet right;

    public Cube(char[] u, char[] d, char[] f, char[] b, char[] l, char[] r){
        up = new Facelet(u);
        down = new Facelet(d);
        face = new Facelet(f);
        back = new Facelet(b);
        left = new Facelet(l);
        right = new Facelet(r);
    }
    public Cube(Cube c){
        up = new Facelet(c.up);
        down =new Facelet( c.down);
        face =new Facelet( c.face);
        back = new Facelet(c.back);
        left = new Facelet(c.left);
        right =new Facelet( c.right);
    }
    public Cube(){
        char[] u = new char[9];
        Arrays.fill(u,'w');
        char[] d = new char[9];
        Arrays.fill(d,'y');
        char[] f = new char[9];
        Arrays.fill(f,'g');
        char[] b = new char[9];
        Arrays.fill(b,'b');
        char[] l = new char[9];
        Arrays.fill(l,'o');
        char[] r = new char[9];
        Arrays.fill(r,'r');
        up = new Facelet(u);
        down = new Facelet(d);
        face = new Facelet(f);
        back = new Facelet(b);
        left = new Facelet(l);
        right = new Facelet(r);
    }
    public boolean isSoved(){
        return (up.isComplite() &&
                down.isComplite() &&
                face.isComplite() &&
                back.isComplite() &&
                left.isComplite() &&
                right.isComplite() );
    }

    public Cube rotateLeft(){
        left.rotate();
        char[] tmp= up.getSide(Side.LEFT);
        up.setSide(Side.LEFT,back.getSide(Side.RIGHT_REVERSE));
        back.setSide(Side.RIGHT_REVERSE, down.getSide(Side.LEFT));
        down.setSide(Side.LEFT, face.getSide(Side.LEFT));
        face.setSide(Side.LEFT,tmp);

        return this;
    }
    public Cube rotateRight(){
        right.rotate();
        char[] tmp = up.getSide(Side.RIGHT);
        up.setSide(Side.RIGHT, face.getSide(Side.RIGHT));
        face.setSide(Side.RIGHT,down.getSide(Side.RIGHT));
        down.setSide(Side.RIGHT,back.getSide(Side.LEFT_REVERSE));
        back.setSide(Side.LEFT_REVERSE,tmp);
        return this;
    }

    public Cube rotateUp(){
        up.rotate();
        char[] tmp = face.getSide(Side.TOP);
        face.setSide(Side.TOP, right.getSide(Side.TOP));
        right.setSide(Side.TOP,back.getSide(Side.TOP));
        back.setSide(Side.TOP, left.getSide(Side.TOP));
        left.setSide(Side.TOP, tmp);
        return this;
    }

    public Cube rotateDown(){
        down.rotate();
        char[] tmp = face.getSide(Side.BOTTOM);
        face.setSide(Side.BOTTOM, left.getSide(Side.BOTTOM));
        left.setSide(Side.BOTTOM, back.getSide(Side.BOTTOM));
        back.setSide(Side.BOTTOM, right.getSide(Side.BOTTOM));
        right.setSide(Side.BOTTOM, tmp);
        return this;
    }

    public Cube rotateFace(){
        face.rotate();
        char[] tmp = up.getSide(Side.BOTTOM); // tmp <- U678
        up.setSide(Side.BOTTOM,left.getSide(Side.RIGHT)); // U678 <- L258
        left.setSide(Side.RIGHT,down.getSide(Side.TOP)); // L258 <- D012
        down.setSide(Side.TOP, right.getSide(Side.LEFT)); // D012 <- R036
        right.setSide(Side.LEFT,tmp); // R036 <- tmp
        return this;
    }

    public Cube rotateBack(){
        back.rotate();
        char[] tmp = up.getSide(Side.TOP); // tmp <- U012
        up.setSide(Side.TOP,right.getSide(Side.RIGHT)); // U012 <- R258
        right.setSide(Side.RIGHT,down.getSide(Side.BOTTOM)); // R258 <- D678
        down.setSide(Side.BOTTOM, left.getSide(Side.LEFT)); // D678 <- L036
        left.setSide(Side.LEFT_REVERSE,tmp); // L630 <- tmp
        return this;
    }


    public String randomReverseRotation(){
        Random R = new Random();
        String s = "";
        switch (R.nextInt(6)){
            case 0:
                rotateDown();
                rotateDown();
                rotateDown();
                s = "D";
                break;
            case 1:
                rotateUp();
                rotateUp();
                rotateUp();
                s = "U";
                break;
            case 2:
                rotateBack();
                rotateBack();
                rotateBack();
                s = "B";
                break;
            case 3:
                rotateFace();
                rotateFace();
                rotateFace();
                s = "F";
                break;
            case 4:
                rotateLeft();
                rotateLeft();
                rotateLeft();
                s = "L";
                break;
            case 5:
                rotateRight();
                rotateRight();
                rotateRight();
                s = "R";
                break;
        }
        return s;
    }






    public String toString(){
        String s ="";
        s += "UP_____________\n";
        s += up;
        s += "DOWN___________\n";
        s += down;

        s += "FACE___________\n";
        s += face;
        s += "BACK___________\n";
        s += back;

        s += "RIGHT__________\n";
        s += right;
        s += "LEFT___________\n";
        s += left;

        return s;  }

}
