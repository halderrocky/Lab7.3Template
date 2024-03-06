package edu.sdccd.cisc191.hashes;

import com.aparapi.Kernel;
import com.aparapi.Kernel;
import com.aparapi.Range;

import java.util.Arrays;
import java.util.HashMap;

public class MD4Engine {
    private char[][] format;
    private String[] plainTextList;
    private byte[][] hashBytes;

    private long numCombs = 1;

    public MD4Engine(String[] inputHashes, HashMap<Character, char[]> inFormatMap, String inFormat) {
        hashBytes = new byte[inputHashes.length][];
        hexToBytes(inputHashes);

        format = new char[inFormat.length()][];
        setFormat(inFormatMap, inFormat);
        plainTextList = new String[inputHashes.length];
    }

    public void runMD4Crack() {
        final char[][][] localFormat = {format};
        final long[] localNumCombs = {numCombs};
        final byte[][][] localHash = {hashBytes};
        final String[][] localPlainText = {plainTextList};
        final char[] currentPlain = new char[localFormat[0].length];

        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int iteration = getGlobalId();
                long remComb = localNumCombs[0];
                for(int i=0; i<localFormat[0].length; i++) {
                    remComb /= localFormat[0][i].length;
                    currentPlain[i] = localFormat[0][i][(int) ((iteration/remComb)%localFormat[0][i].length)];
                }

                for(int i=0; i<localHash[0].length; i++) {
                    if(Arrays.equals(localHash[0][i], MD4.runDigest(currentPlain)))
                        localPlainText[0][i] = String.valueOf(currentPlain);
                }

                if(iteration%1000000 == 0)
                    System.out.println(iteration/1000000);
            }
        };
        //Range range = Range.create(10);
        kernel.execute(256);

        plainTextList = localPlainText[0];
        kernel.dispose();
    }

    public void setFormat(HashMap<Character, char[]> inFormatMap, String inFormat) {
        for(int i=0; i<format.length; i++) {
            format[i] = inFormatMap.get(inFormat.charAt(i));
            numCombs *= format[i].length;
        }
    }

    public void hexToBytes(String[] hashList) {
        for(int i=0; i<hashList.length; i++) {
            int len = hashList[0].length();
            hashBytes[i] = new byte[len / 2];
            for (int j = 0; j < len; j += 2) {
                hashBytes[i][j/2] = (byte) ((Character.digit(hashList[0].charAt(j), 16) << 4)
                        + Character.digit(hashList[0].charAt(j+1), 16));
            }
        }
    }

    public String[] getPlainText() {
        return plainTextList;
    }
}