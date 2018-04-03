package com.blackwell;


public class Main {

    // TODO fix OutOfMemoryError
    /*
        Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at com.blackwell.Facelet.<init>(Facelet.java:17)
        at com.blackwell.Cube.<init>(Cube.java:23)
        at com.blackwell.Node.<init>(Node.java:24)
        at com.blackwell.Node.<init>(Node.java:40)
        at com.blackwell.Node.<init>(Node.java:44)
        at com.blackwell.Node.<init>(Node.java:44)
        at com.blackwell.Node.<init>(Node.java:44)
        at com.blackwell.Node.<init>(Node.java:40)
        at com.blackwell.Node.<init>(Node.java:40)
        at com.blackwell.Node.<init>(Node.java:44)
        at com.blackwell.Node.<init>(Node.java:41)
        at com.blackwell.Node.<init>(Node.java:44)
        at com.blackwell.Tree.<init>(Tree.java:13)
        at com.blackwell.Main.main(Main.java:25)
     */
    public static void main(String[] args) {
        Cube c = new Cube();

        String moves = "";
        for(int i=0; i<Node.MAX_DEPTH; ++i){
            moves += c.randomReverseRotation();
        }

        String correct = (new StringBuilder(moves)).reverse().toString();
        System.out.println(correct);
        //System.out.println("Start");
        //System.out.println(c);

        //Node n = new Node("",c);
        //for(int i=0;i<6;++i)
            //System.out.println(n.getSon(3).getRotations() +" : " + n.getSon(3).getCube());

        long start = System.currentTimeMillis();
        Tree tree = new Tree(c);
        long end = System.currentTimeMillis();

        System.out.println("");
        System.out.println( (end-start)/1000);
    }
}
