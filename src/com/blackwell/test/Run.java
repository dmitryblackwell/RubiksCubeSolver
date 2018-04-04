package com.blackwell.test;

import java.util.Random;

public class Run {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for(int i=0;i<54;++i)
            sb.append((char)(r.nextInt(20)+65));
        System.out.println(sb);

        long before = System.currentTimeMillis();
        Node n = new Node(new StringBuilder("A"),sb);
        long after = System.currentTimeMillis();
        System.out.println(Node.NodeCount);
        System.out.println( (after - before) + " ms");
    }
}
