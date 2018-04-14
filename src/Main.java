import com.blackwell.cube.CubeCorners;
import com.blackwell.search.Node;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        CubeCorners cube = new CubeCorners();
//        cube.right();
//        cube.right();
//        cube.face();
//        cube.left();
//        cube.up();
//        cube.right();
//        cube.face();
//        cube.left();
//        cube.up();
//        cube.back();
//        cube.right();
//        cube.down();
//        cube.left();

        StringBuilder moves = new StringBuilder();
        Random R = new Random();
        for(int i=0; i<4; ++i) {
            switch (R.nextInt(6)) {
                case 0:
                    cube.up();
                    cube.up();
                    cube.up();
                    moves.append("U");
                    break;
                case 1:
                    cube.face();
                    cube.face();
                    cube.face();
                    moves.append("F");
                    break;
                case 2:
                    cube.right();
                    cube.right();
                    cube.right();
                    moves.append("R");
                    break;
                case 3:
                    cube.down();
                    cube.down();
                    cube.down();
                    moves.append("D");
                    break;
                case 4:
                    cube.left();
                    cube.left();
                    cube.left();
                    moves.append("L");
                    break;
                case 5:
                    cube.back();
                    cube.back();
                    cube.back();
                    moves.append("B");
            }
        }
        long before = System.currentTimeMillis();
        Node.search(cube);
        long after = System.currentTimeMillis();
        System.out.println(moves);
        System.out.println( (after-before) * Math.pow(10,-3));

        long end = System.currentTimeMillis();
        System.out.println( (end-start)/1000);
    }
}
