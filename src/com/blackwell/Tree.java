package com.blackwell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Tree {
    Node n;

    public Tree(Cube c){
        Cube thisCube = new Cube(c);
        n = new Node("", thisCube);
        run(n);
    }
    private void log(String s){
        try {
            Files.write(Paths.get("log.txt"), s.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            System.out.println(e);
            //exception handling left as an exercise for the reader
        }
    }

    public void run(Node n){
        //log(n.isNodeSolved()+" "+n.getRotations()+"___"+n.getRotations().length()+"___"+n.getCube()+"\r\n");
//        System.out.println("_________________________________"+n.getRotations()+"_________________________________");
//        System.out.println(n.getCube());
//        System.out.println("");


        if (n.isNodeSolved()){
            System.out.println(n.getRotations());
            //System.out.println(("__________________FUCK__________________"));
        }
        else
            for (int i=0; i<6; ++i)
                if(n.getSon(i) != null)
                    run(n.getSon(i));
    }
}
