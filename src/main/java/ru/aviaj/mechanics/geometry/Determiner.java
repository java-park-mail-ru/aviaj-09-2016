package ru.aviaj.mechanics.geometry;


public class Determiner {

    public static final int SIZE = 3;
    private int[][] values = new int[2][2];

    public void setValue(int i, int j, int value) {
        if ((i >= SIZE) || (j >= SIZE) || (i < 0) || (j < 0))
            return;
        values[i][j] = value;
    }

    public int count() {
        final int a = values[0][0]*(values[1][1]*values[2][2] - values[1][2]*values[2][1]);
        final int b = values[0][1]*(values[1][0]*values[2][2] - values[1][2]*values[0][2]);
        final int c = values[0][2]*(values[1][0]*values[2][1] - values[1][1]*values[2][0]);

        return (a - b + c);
    }
}
