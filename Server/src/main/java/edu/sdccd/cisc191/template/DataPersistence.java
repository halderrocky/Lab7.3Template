package edu.sdccd.cisc191.template;

import java.io.*;

public class DataPersistence {
    private static final String ARRAY_FILE = "array.dat";
    private static final String ARRAY2D_FILE = "array2D.dat";

    public void saveArray(int[] array) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARRAY_FILE))) {
            oos.writeObject(array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] loadArray() {
        File file = new File(ARRAY_FILE);
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARRAY_FILE))) {
            return (int[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveArray2D(int[][] array2D) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARRAY2D_FILE))) {
            oos.writeObject(array2D);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] loadArray2D() {
        File file = new File(ARRAY2D_FILE);
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARRAY2D_FILE))) {
            return (int[][]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
