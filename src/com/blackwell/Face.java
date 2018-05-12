package com.blackwell;

public enum Face {
    FRONT(0,1,2,3,8,19,18,12),
    REAR(4,5,6,7,10,14,15,16),
    LEFT(7,0,3,4,11,12,13,14),
    RIGHT(1,6,5,2,9,16,17,19),
    TOP(7,6,1,0,10,9,8,11),
    BOTTOM(3,2,5,4,18,17,15,13);

    Face(int tl, int tr, int br, int bl, int t, int r, int b, int l) {
        top_left = tl;
        top_right = tr;
        bottom_right = br;
        bottom_left = bl;
        top = t;
        right = r;
        bottom = b;
        left = l;
    };

    final int top_left;
    final int top_right;
    final int bottom_right;
    final int bottom_left;
    final int top;
    final int right;
    final int bottom;
    final int left;
}
