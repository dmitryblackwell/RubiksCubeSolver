import com.blackwell.cube.CubeCorners;
import com.blackwell.search.Node;
import com.blackwell.search.Tree;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        CubeCorners cube = new CubeCorners();
        System.out.println(cube.scramble(5));
        System.out.println("____________");

        long start = System.nanoTime();

        Tree tree = new Tree(cube);
        tree.search();
        tree.printToFile();

        long end = System.nanoTime();
        System.out.println((end-start)*Math.pow(10,-9) + " seconds");
    }
}
