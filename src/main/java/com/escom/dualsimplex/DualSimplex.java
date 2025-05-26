package com.escom.dualsimplex;

import static com.escom.dualsimplex.Relations.*;
import java.util.Arrays;

public class DualSimplex {

    private final Boolean isMax;
    private int[] z;
    private int[][] a;
    private int[] b;
    private Relations[] relations;

    DualSimplex(Boolean isMax, int[] z, int[][] a, int[] b, Relations[] relations) {
        this.relations = relations;
        this.isMax = isMax;
        this.z = z;
        this.a = a;
        this.b = b;

        solve();
    }

    public void solve() {
        invert(a);
        System.out.println(Arrays.toString(b));
        getDual();
        System.out.println("=====================");
        a = invert(a);
        System.out.println(Arrays.toString(b));
    }

    private static int[][] invert(int[][] matrix){
        int[][] invertedMatrix = new int[matrix[0].length][matrix.length];
        int m = matrix.length;
        int n = matrix[0].length;

        System.out.println(Arrays.deepToString(matrix));

        for(int i = 0; i < m; i++){
            invertedMatrix[0][i] = matrix[i][0];
            for(int j = 1; j < n; j++) invertedMatrix[j][i] = matrix[i][j];
        }

        System.out.println(Arrays.deepToString(invertedMatrix));

        return invertedMatrix;
    }

    private void getDual(){
        Relations targetRelation = isMax ? GT : LT;
        Relations newRelation = isMax ? LT : GT;

        while (Arrays.stream(relations).anyMatch(r -> r == targetRelation)) {
            int pos = Arrays.stream(relations).toList().indexOf(targetRelation);
            relations[pos] = newRelation;

            for (int i = 0; i < a.length; i++) a[pos][i] = -a[pos][i];

            b[pos] = -b[pos];
        }

        while (Arrays.stream(relations).anyMatch(z -> z == EQ)){
            int pos = Arrays.stream(relations).toList().indexOf(EQ);
            relations[pos] = isMax ? LT : GT;

            int[][] newA = new int[a.length + 1][a[0].length];

            for (int i = 0; i < a.length; i++) System.arraycopy(a[i], 0, newA[i], 0, a[0].length);
            for (int i = 0; i < a[0].length; i++) newA[a.length][i] = -a[pos][i];

            int[] newB = new int[b.length+1];
            System.arraycopy(b, 0, newB, 0, b.length);
            newB[b.length] = -b[pos];

            a = newA;
            b = newB;
        }
    }
}
