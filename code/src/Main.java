import com.blackwell.cube.CubeCorners;
import com.blackwell.search.Node;
import com.blackwell.search.Tree;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        CubeCorners cube = new CubeCorners();

        Tree tree = new Tree(cube);
        System.out.println(tree);
    }
}
