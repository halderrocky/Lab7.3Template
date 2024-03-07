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
            final char[] input = new char[16];
            final byte[] output = new byte[64];
            @Override
            public void run() {
                int iteration = getGlobalId();
                /*long remComb = localNumCombs[0];
                for(int i=0; i<localFormat[0].length; i++) {
                    remComb /= localFormat[0][i].length;
                    currentPlain[i] = localFormat[0][i][(int) ((iteration/remComb)%localFormat[0][i].length)];
                }

                for(int i=0; i<localHash[0].length; i++) {
                    if(Arrays.equals(localHash[0][i], runMD4Digest(currentPlain)))
                        localPlainText[0][i] = String.valueOf(currentPlain);
                }*/
            }
            final int[] ROUND2 = {0,4,8,12,1,5,9,13,2,6,10,14,3,7,11,15};
            final int[] ROUND3 = {0,8,4,12,2,10,6,14,1,9,5,13,3,11,7,15};
            final byte[] messageBytes = new byte[64];
            final int[] buffer = {0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476};
            final int[] paddingLength = new int[1];
            final int[] x = new int[16];
            final int[] abcd = new int[4];

            public void runMD4Digest() {
                paddingLength[0] = 56-input.length%64;

                for(int i=0; i<input.length; i++)
                    messageBytes[i] = (byte) input[i];

                messageBytes[input.length] = (byte) 0x80; //Sets next byte to '10000000'
                for(int i=0; i<8; i++)
                    messageBytes[input.length+paddingLength[0]+i] = (byte) ((input.length*8) >>> (8*i));

                for(int iteration=0; iteration<messageBytes.length;) { //Each 16 words / 512 bits (64 bytes)
                    for(int i=0; i<16; i++)
                        x[i] = (messageBytes[iteration++]&0xff) | ((messageBytes[iteration++]&0xff) << 8) | ((messageBytes[iteration++]&0xff) << 16) | ((messageBytes[iteration++]&0xff) << 24);

                    abcd[0] = buffer[0];
                    abcd[1] = buffer[1];
                    abcd[2] = buffer[2];
                    abcd[3] = buffer[3];

                    for(int i=0; i<16; i+=4) {
                        abcd[0] = rot(abcd[0] + f(abcd[1],abcd[2],abcd[3]) + x[i], 3);
                        abcd[3] = rot(abcd[3] + f(abcd[0],abcd[1],abcd[2]) + x[i+1], 7);
                        abcd[2] = rot(abcd[2] + f(abcd[3],abcd[0],abcd[1]) + x[i+2], 11);
                        abcd[1] = rot(abcd[1] + f(abcd[2],abcd[3],abcd[0]) + x[i+3], 19);
                    }

                    for(int i=0; i<16; i+=4) {
                        abcd[0] = rot((abcd[0] + g(abcd[1],abcd[2],abcd[3]) + x[ROUND2[i]] + 0x5A827999), 3);
                        abcd[3] = rot((abcd[3] + g(abcd[0],abcd[1],abcd[2]) + x[ROUND2[i+1]] + 0x5A827999), 5);
                        abcd[2] = rot((abcd[2] + g(abcd[3],abcd[0],abcd[1]) + x[ROUND2[i+2]] + 0x5A827999), 9);
                        abcd[1] = rot((abcd[1] + g(abcd[2],abcd[3],abcd[0]) + x[ROUND2[i+3]] + 0x5A827999), 13);
                    }

                    for(int i=0; i<16; i+=4) {
                        abcd[0] = rot((abcd[0] + h(abcd[1],abcd[2],abcd[3]) + x[ROUND3[i]] + 0x6ED9EBA1), 3);
                        abcd[3] = rot((abcd[3] + h(abcd[0],abcd[1],abcd[2]) + x[ROUND3[i+1]] + 0x6ED9EBA1), 9);
                        abcd[2] = rot((abcd[2] + h(abcd[3],abcd[0],abcd[1]) + x[ROUND3[i+2]] + 0x6ED9EBA1), 11);
                        abcd[1] = rot((abcd[1] + h(abcd[2],abcd[3],abcd[0]) + x[ROUND3[i+3]] + 0x6ED9EBA1), 15);
                    }

                    buffer[0] += abcd[0];
                    buffer[1] += abcd[1];
                    buffer[2] += abcd[2];
                    buffer[3] += abcd[3];
                }

                for( int i=0; i<4; i++ ) {
                    for ( int j=0; j<4; j++ ) {
                        output[4*i + j] = (byte) (buffer[i] >>> (8*j));
                    }
                }
            }
            private int f (int x, int y, int z) {
                return ((x&y) | ((~x)&z));
            }

            private int g (int x, int y, int z) {
                return ((x&y) | (x&z) | (y&z));
            }

            private int h (int x, int y, int z) {
                return (x ^ y ^ z);
            }

            private int rot (int t, int s) {
                return t << s | t >>> (32-s);
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