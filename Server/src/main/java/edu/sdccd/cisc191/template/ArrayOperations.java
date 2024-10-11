package edu.sdccd.cisc191.template;

import java.util.Arrays;

public class ArrayOperations {
    private int[] array;

    public ArrayOperations(int size) {
        array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i + 1;
        }
    }

    public int getAtIndex(int index) throws IndexOutOfBoundsException {
        return array[index];
    }

    public void setAtIndex(int index, int value) throws IndexOutOfBoundsException {
        array[index] = value;
    }

    public int findIndexOf(int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) return i;
        }
        return -1;
    }

    public void printAll() {
        System.out.println(Arrays.toString(array));
    }

    public void deleteAtIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= array.length) throw new IndexOutOfBoundsException();
        array[index] = 0; // Simple delete by setting to zero
    }

    public void expand(int newSize) {
        array = Arrays.copyOf(array, newSize);
        for (int i = array.length - (newSize - array.length); i < newSize; i++) {
            array[i] = 0;
        }
    }

    public void shrink(int newSize) {
        if (newSize > array.length) return;
        array = Arrays.copyOf(array, newSize);
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] newArray) {
        this.array = newArray;
    }
}
