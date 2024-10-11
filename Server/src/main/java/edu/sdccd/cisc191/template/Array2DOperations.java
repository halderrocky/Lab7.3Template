package edu.sdccd.cisc191.template;

import java.util.Arrays;

public class Array2DOperations {
    private int[][] array2D;

    public Array2DOperations(int rows, int cols) {
        array2D = new int[rows][cols];
        int counter = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array2D[i][j] = counter++;
            }
        }
    }
    public int getAtIndex(int row, int col) throws IndexOutOfBoundsException {
            return array2D[row][col];
    }

    public void setAtIndex(int row, int col, int value) throws IndexOutOfBoundsException {
        array2D[row][col] = value;
    }

    public int findIndexOf(int value) {
        for (int i = 0; i < array2D.length; i++) {
            for (int j = 0; j < array2D[i].length; j++) {
                if (array2D[i][j] == value) return i * array2D[i].length + j;
            }
        }
        return -1;
    }

    public void printAll() {
        for (int[] row : array2D) {
            System.out.println(Arrays.toString(row));
        }
    }

    public void deleteAtIndex(int row, int col) throws IndexOutOfBoundsException {
        array2D[row][col] = 0; // Simple delete by setting to zero
    }

    public void expand(int newRows, int newCols) {
        int currentRows = array2D.length;
        int currentCols = array2D[0].length;

        array2D = Arrays.copyOf(array2D, newRows);
        for (int i = currentRows; i < newRows; i++) {
            array2D[i] = new int[newCols];
            Arrays.fill(array2D[i], 0);
        }

        for (int i = 0; i < newRows; i++) {
            array2D[i] = Arrays.copyOf(array2D[i], newCols);
            for (int j = currentCols; j < newCols; j++) {
                array2D[i][j] = 0;
            }
        }
    }

    public void shrink(int newRows, int newCols) {
        if (newRows > array2D.length || newCols > array2D[0].length) return;

        array2D = Arrays.copyOf(array2D, newRows);
        for (int i = 0; i < newRows; i++) {
            array2D[i] = Arrays.copyOf(array2D[i], newCols);
        }
    }

    public int[][] getArray2D() {
        return array2D;
    }

    public void setArray2D(int[][] newArray2D) {
        this.array2D = newArray2D;
    }
}
