package edu.sdccd.cisc191.hashes;

public class MD4 {
    private static final int INIT_BUFFER_0 = 0x67452301;
    private static final int INIT_BUFFER_1 = 0xefcdab08;
    private static final int INIT_BUFFER_2 = 0x98badcfe;
    private static final int INIT_BUFFER_3 = 0x10325476;
    private static final int[] ROUND_CONST = {0, 0x5A827999, 0x6ED9EBA1};
    private static final int[][] S_CONST = {
            {3,7,11,19},
            {3,5,9,13},
            {3,9,11,15}
    };
    private static final int[][] I_CONST = {
            {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
            {0,4,8,12,1,5,9,13,2,6,10,14,3,7,11,15},
            {0,8,4,12,2,10,6,14,1,9,5,13,3,11,7,15}
    };

    private int[] buffer = new int[4];
    private int textLength = 0;

    public MD4() {
        buffer[0] = INIT_BUFFER_0;
        buffer[1] = INIT_BUFFER_1;
        buffer[2] = INIT_BUFFER_2;
        buffer[3] = INIT_BUFFER_3;
    }

    public byte[] runDigest(String inputText) {
        textLength = inputText.length();
        int paddingLength = textLength%64;
        if(paddingLength < 56)
            paddingLength = 56-paddingLength;
        else
            paddingLength = 120-paddingLength;

        byte[] padding = new byte[paddingLength + 8];
        padding[0] = (byte) 0x80;

        for(int i=0; i<8; i++)
            padding[paddingLength + i] = (byte) ((textLength*8) >>> (8*i));

        return
    }
}
