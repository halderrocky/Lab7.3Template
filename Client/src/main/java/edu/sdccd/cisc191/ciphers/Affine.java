package edu.sdccd.cisc191.ciphers;

import java.util.StringTokenizer;

public class Affine {
    public static String encode(String inputText, String key) {
        //divides key into two inputs (m,b)
        StringTokenizer newKey = new StringTokenizer(key, ",");
        int m = Integer.parseInt(newKey.nextToken());
        int b = Integer.parseInt(newKey.nextToken());

        //iterates through each character and shifts according to the key
        StringBuilder outputText = new StringBuilder();
        for (int i = 0; i < inputText.length(); i++) {
            char c = inputText.charAt(i);
            int charNum = c - 'a';
            int t = (m * charNum + b) % 26;
            char encodedChar = (char) (t + 'a');
            outputText.append(encodedChar);
        }
        return outputText.toString();
    }

    public static String decode(String inputText, String key) {
        StringTokenizer newKey = new StringTokenizer(key, ",");
        int m = Integer.parseInt(newKey.nextToken());
        int b = Integer.parseInt(newKey.nextToken());

        StringBuilder outputText = new StringBuilder();
        for (int i = 0; i < inputText.length(); i++) {
            char c = inputText.charAt(i);
            int charNum = c - 'a';
            int t = (inverseKey(m, 26) * (charNum - b)) % 26;
            if(t<0){
                t += 26;
            }
            char encodedChar = (char) (t + 'a');
            outputText.append(encodedChar);
        }
        return outputText.toString();
    }

    private static int inverseKey(int key, int n) {
        int inverse = 1;
        key = key % n;
        for (int i = 1; i < n; i++) {
            if ((key * i) % n == 1) {
                inverse = i;
            }
        }
        return inverse;
    }
}