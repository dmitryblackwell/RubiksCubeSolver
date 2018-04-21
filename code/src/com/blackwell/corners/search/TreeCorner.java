package com.blackwell.corners.search;

import com.blackwell.corners.cube.CubeCorners;
import com.blackwell.corners.cube.Side;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TreeCorner {
    private static final int DEEP_MAX = 19;
    private static final String TREE_DATA_FILE = "solver/corners/TreeCorners.txt";
    private static final String RESULT_FILE = "solver/corners/ResultCorners.txt";

    NodeCorner root;
    public TreeCorner(CubeCorners c){
        root = new NodeCorner(9,c);
        setUp(root, 0);
    }
    private void setUp(NodeCorner n, int deep){
        if (deep > DEEP_MAX)
            return;

        n.setUpSons();
        for(int i=0; i<6; ++i)
            setUp(n.getSon(i), ++deep);
    }
    private StringBuilder solution;
    private static final String[] SIDES_IN_ROW = {"UUUU", "FFFF", "RRRR", "DDDD", "LLLL", "BBBB"};
    public void search(){
        solution = new StringBuilder();
        search(root,0);

        String[] results = solution.toString().split("_");
        Set<String> set = new HashSet<>();
        for(int i=0; i<results.length; ++i){
            for(int j=0; j<6; ++j)
                results[i] = results[i].replace(SIDES_IN_ROW[j],"");
            set.add(results[i]);
        }

        solution = new StringBuilder();
        Iterator it = set.iterator();
        while(it.hasNext())
            solution.append(it.next()).append(System.lineSeparator());

        try (BufferedWriter out = new BufferedWriter(new FileWriter(RESULT_FILE))) {
            out.write(solution.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(solution);
        }
    }
    private void printReadableResult(long result){
        System.out.println(getReadableResult(result));
    }
    private String getReadableResult(long result){
        String str = String.valueOf(result);
        for(int i=0; i<6; ++i) {
            char num = String.valueOf(i).charAt(0);
            char res = Side.values()[i].toString().charAt(0);
            str = str.replace(num,res);
        }
        return str;
    }
    private void search(NodeCorner n, long result){
        for(int i=0; i<6; ++i){
            if (n.getSon(i) != null && n.getSon(i).isSolved()) {
                //printReadableResult(result*10 + n.getSon(i).getRotation());
                solution.append(getReadableResult(result*10 + n.getSon(i).getRotation())).append("_");
                return;
            }
        }
        for(int i=0; i<6; ++i){
            if (n.getSon(i) != null)
                search(n.getSon(i),result*10 + n.getSon(i).getRotation());
        }
    }
    private void printToFile(BufferedWriter out, NodeCorner n, long result) throws IOException {
        out.write(getReadableResult(result));
        out.newLine();
        out.write(n.toString());
        for (int i = 0; i < 6; ++i)
            if(n.getSon(i) != null)
                printToFile(out, n.getSon(i),result*10 + n.getSon(i).getRotation());
    }

    public void printToFile(){
        try (BufferedWriter out = new BufferedWriter(new FileWriter(TREE_DATA_FILE))) {
            printToFile(out,root,root.getRotation());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
