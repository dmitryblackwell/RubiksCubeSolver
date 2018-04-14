package com.blackwell.cube;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CubeCornersTest {
    private CubeCorners cube;
    @Before
    public void setUp() {
         cube = new CubeCorners();
    }

    @After
    public void tearDown() {
    }


    @Test
    public void rotateTest(){
        cube.left();
        cube.up();
        cube.right();
        cube.face();
        cube.down();
        cube.back();
        cube.up();
        String expected =   "U: [ O B Y G ]\n" +
                            "F: [ W R B O ]\n" +
                            "R: [ G W O Y ]\n" +
                            "D: [ G R B W ]\n" +
                            "L: [ W R Y B ]\n" +
                            "B: [ R G O Y ]\n";
        Assert.assertArrayEquals(expected.toCharArray(), cube.toString().toCharArray());
    }
}