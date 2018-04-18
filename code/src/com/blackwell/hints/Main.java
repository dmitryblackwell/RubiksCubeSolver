package com.blackwell.hints;

public class Main {


    // TODO delete this folder: hints


    public static void main(String[] args) {
        Cube c = new Cube();

        String solution = c.randomlyMoveForAnswer();

        long start = System.currentTimeMillis();

        Node n = new Node("",c);
        System.out.println( solution.equals(Node.solution));
        long end = System.currentTimeMillis();

        System.out.println( (end-start)/1000);
    }
}
