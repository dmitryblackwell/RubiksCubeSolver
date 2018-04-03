package com.blackwell;

public class Facelet {
    public static final int FACE_LENGTH = 9;
    private char[] face = new char[FACE_LENGTH];

    Facelet(char[] f) {
        System.arraycopy(f,0,face,0,9);
    }
    Facelet(Facelet f){
        this(f.face);
    }

    public char get(int i){
        return face[i];
    }
    public char[] get(int i1,int i2,int i3){
        return new char[]{face[i1], face[i2], face[i3]};
    }
    public void set(int i,char c){
        face[i] = c;
    }
    public void set(int i1,int i2,int i3, char[] ch){
        face[i1] = ch[0];
        face[i2] = ch[1];
        face[i3] = ch[2];
    }

    boolean isComplete(){
        char ch = face[0];
        for (int i=1; i<9; ++i)
            if (face[i] != ch)
                return false;
        return true;
    }
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(int i=1; i<=9; ++i){
            builder.append(face[i-1]);
            if(i%3 == 0)
                builder.append("\n");
        }
        builder.append("\n");
        return builder.toString();
    }

    void rotate(){
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
