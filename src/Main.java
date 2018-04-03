import com.blackwell.Cube;
import com.blackwell.Node;
import com.blackwell.Tree;

public class Main {

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
