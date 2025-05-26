package com.escom.dualsimplex;

import static com.escom.dualsimplex.Relations.*;

public class Main {
    public static void main(String[] args) {
        int[] z = {5,3,1};
        int[][] a = {{2,1,4},{-8,-7,0},{1,5,2}};
        int[] b = {3,-6,5};
        Relations[] relations = {LT, GT, EQ};

        new DualSimplex(true, z, a, b, relations);
    }
}