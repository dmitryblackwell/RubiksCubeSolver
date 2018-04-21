package com.blackwell.corners.search;

import com.blackwell.corners.cube.CubeCorners;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class TreeCornerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void search(){ // 90 fucking percents!!!
        float total = 0;
        float correct = 0;
        for(int i = 0; i<100; ++i){
            correct += isSearchCorrect() ? 1f : 0f;
            total++;
        }
        System.out.println( "\nResult: " + (correct/total) * 100f +"%");
    }


    public boolean isSearchCorrect() {
        CubeCorners cube = new CubeCorners();
        String expected = cube.scramble(5);
        TreeCorner treeCorner = new TreeCorner(cube);
        treeCorner.search();

        try (BufferedReader in = new BufferedReader(new FileReader("solver/result.txt"))) {
            boolean found = false;
            String line;
            while ( (line = in.readLine()) != null) {
                if (line.equals(expected)) {
                    found = true;
                    break;
                }
            }
            return found;
        } catch (FileNotFoundException e) {
            System.out.println("404 - File not found.");
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return false;
    }
}