package edu.sdccd.cisc191.hashes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**************************************************************************
 * Engine to bruteforce MD4 Hashes
 * @author Oliver Tran
 *************************************************************************/
public class MD4Engine {
    private char[][] format;
    private final ConcurrentHashMap<String, String> crackedPasswords = new ConcurrentHashMap<>();
    private byte[][] hashBytes;

    private long numCombs = 1;
    private final int NUM_THREADS;
    private AtomicLong progress = new AtomicLong();

    /**************************************************************************
     * Constructor
     * @param inputHashes List of hashes to crack
     * @param inFormatMap Map keying the format to its alphabet
     * @param inFormat The format of the passwords to check for
     * @param numThreads The number of threads to allocate
     *************************************************************************/
    public MD4Engine(String[] inputHashes, HashMap<Character, char[]> inFormatMap, String inFormat, int numThreads) {
        NUM_THREADS = numThreads;
        hashBytes = new byte[inputHashes.length][];
        hexToBytes(inputHashes);
        format = new char[inFormat.length()][];
        setFormat(inFormatMap, inFormat);
    }

    /**************************************************************************
     * Main method to run the MD4 crack
     *************************************************************************/
    public void runMD4Crack() {
        //Divides work for each of the worker threads
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        for(int i=1; i<=NUM_THREADS; i++) {
            long workload = numCombs/NUM_THREADS;
            MD4Worker md4Worker = new MD4Worker(workload * (i-1), workload*i);
            executor.execute(md4Worker);
        }

        executor.shutdown();
        try {
            boolean terminated = executor.awaitTermination(60, TimeUnit.MINUTES);
            if (!terminated) {
                throw new RuntimeException("Runtime exceeded 1 hour");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFormat(HashMap<Character, char[]> inFormatMap, String inFormat) {
        for(int i=0; i<format.length; i++) {
            format[i] = inFormatMap.get(inFormat.charAt(i));
            numCombs *= format[i].length;
        }
    }

    private void hexToBytes(String[] hashList) {
        for(int i=0; i<hashList.length; i++) {
            int len = hashList[0].length();
            hashBytes[i] = new byte[len / 2];
            for (int j = 0; j < len; j += 2) {
                hashBytes[i][j/2] = (byte) ((Character.digit(hashList[i].charAt(j), 16) << 4)
                        + Character.digit(hashList[i].charAt(j+1), 16));
            }
        }
    }

    /**************************************************************************
     * Getter to get map of hashes to cracked hash message
     * @return The map pairing hashes to its plaintext
     *************************************************************************/
    public HashMap<String, String> getCrackedPasswords() {
        HashMap<String, String> ret = new HashMap<>(crackedPasswords);
        return ret;
    }

    private class MD4Worker implements Runnable {
        private final MD4 md4 = new MD4();
        private final long iterationStart;
        private final long iterationEnd;

        public MD4Worker(long inStart, long inEnd) {
            this.iterationStart = inStart;
            this.iterationEnd = inEnd;
        }

        @Override
        public void run() {
            char[] currentPlain = new char[format.length];
            for(long iteration=iterationStart; iteration<iterationEnd; iteration++) {
                long remComb = numCombs;
                for(int i=0; i<format.length; i++) {
                    remComb /= format[i].length;
                    currentPlain[i] = format[i][(int) ((iteration/remComb)%format[i].length)];
                }

                checkHash(String.valueOf(currentPlain));
                synchronized (this) {
                    if(progress.incrementAndGet()%1000000 == 0)
                        System.out.println(progress);
                }
            }
        }

        /**************************************************************************
         * Checks to see if the hash matches any of the passed hashes
         *************************************************************************/
        private void checkHash(String plainText) {
            byte[] hash = md4.runDigest(plainText);
            for(byte[] hashByte : hashBytes) {
                if (Arrays.equals(hashByte, hash)){
                    //TODO Improve performance
                    StringBuilder hexString = new StringBuilder(hashByte.length*2);
                    for(byte b: hashByte)
                        hexString.append(String.format("%02x", b));

                    MD4Engine.this.crackedPasswords.putIfAbsent(hexString.toString(), plainText);
                }
            }
        }
    }
}

