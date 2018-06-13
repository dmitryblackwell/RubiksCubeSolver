package com.blackwell;

import com.blackwell.utils.Cube;
import com.blackwell.utils.Tools;
import org.junit.Assert;
import org.junit.Test;

public class SearchTest {

    @Test
    public void SearchForSolution(){
        for(int i=0; i<10;++i){
            String cubeStr = Tools.randomCube(); // getting random cube
            Cube cube = new Cube(cubeStr);

            String[] moves = Search.solution(cubeStr).split(" "); // solving it
            for(String move : moves) // rotating
                cube.rotate(move);

            Assert.assertTrue(cube.isSolved());
        }
    }
}
