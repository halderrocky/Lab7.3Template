package edu.sdccd.cisc191.hashes;

import java.util.Arrays;
import java.util.HashMap;

public class MD4Engine {
    private MD4 md4 = new MD4();
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
        char[] currentPlain = new char[format.length];
        for(long iteration=0; iteration<numCombs; iteration++) {
            long remComb = numCombs;
            for(int i=0; i<format.length; i++) {
                remComb /= format[i].length;
                currentPlain[i] = format[i][(int) ((iteration/remComb)%format[i].length)];
            }

            checkHash(String.valueOf(currentPlain));

            if(iteration%1000000 == 0)
                System.out.println(iteration/1000000);
        }
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

    private void checkHash(String plainText) {
        for(int i=0; i<hashBytes.length; i++) {
            if(Arrays.equals(hashBytes[i], md4.runDigest(plainText)))
                plainTextList[i] = plainText;
        }
    }

    public String[] getPlainText() {
        return plainTextList;
    }
}
