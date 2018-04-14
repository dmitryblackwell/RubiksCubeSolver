### RubiksCubeSolver

# Awesome things are coming...

```java
public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        CubeCorners cube = new CubeCorners();
        Node.search(cube);
        System.out.println(moves);

        long end = System.currentTimeMillis();
        System.out.println( (end-start)/1000);
    }
}
```
