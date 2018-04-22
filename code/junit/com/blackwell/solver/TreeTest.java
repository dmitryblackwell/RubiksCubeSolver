package com.blackwell.solver;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeTest {

    @Test
    public void Run(){
        final float COUNT = 10;
        float runningTime = 0;
        Tree tree;
        for(int i=0; i<COUNT; ++i){
            long start = System.nanoTime();
            tree = new Tree();
            runningTime += System.nanoTime()-start;
        }
        System.out.println( (runningTime*Math.pow(10,-9)) / COUNT + " sec");
    }

}