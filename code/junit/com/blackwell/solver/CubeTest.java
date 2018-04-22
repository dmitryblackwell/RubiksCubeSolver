package com.blackwell.solver;

import com.blackwell.solver.cube.Cube;
import org.junit.Assert;
import org.junit.Test;

public class CubeTest {

    @Test
    public void RotationTest(){
        Cube c = new Cube();
        String expected = c.toString();

        String solution = c.scramble(10_000_000);
        c.solve(solution);

        String actual = c.toString();

        Assert.assertEquals(expected,actual);
    }
}