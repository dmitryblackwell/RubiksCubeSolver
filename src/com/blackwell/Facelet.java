package com.blackwell;

public class Facelet {
    private char[] face;


    /*
    0 1 2
    3 4 5
    6 7 8
     */
    public Facelet(char[] f) {
        face = new char[9];
        System.arraycopy(f,0,face,0,9);
    }
    public Facelet(Facelet f){
        face = new char[9];
        System.arraycopy(f.face,0,face,0,9);
    }
    public void setSide(int[] s, char[] sc){
        //if (side.length != 3)
            // throw something bad
        for(int i=0; i<s.length; ++i)
            face[s[i]] = sc[i];
    }
    public char[] getSide(int[] s){
        //if (side.length != 3)
            // throw something bad
        char[] side=new char[s.length];
        for (int i=0; i<s.length; ++i)
            side[i] = face[s[i]];

        return side;
    }
    public boolean isComplite(){
        char ch = face[0];
        for (int i=1; i<9; ++i)
            if (face[i] != ch)
                return false;
        return true;
    }
    public String toString(){
        String s = "";
        for(int i=1; i<=9; ++i){
            s+=face[i-1];
            if(i%3 == 0)
                s+="\n";
        }
        s += "\n";
        return s;
    }
    public void rotate(){
        char cornerTmp=face[0];
        face[0] = face[6];
        face[6] = face[8];
        face[8] = face[2];
        face[2] = cornerTmp;

        char edgeTmp = face[1];
        face[1] = face[3];
        face[3] = face[7];
        face[7] = face[5];
        face[5] = edgeTmp;
    }
}
