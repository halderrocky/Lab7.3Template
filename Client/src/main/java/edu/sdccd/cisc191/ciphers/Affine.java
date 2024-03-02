package edu.sdccd.cisc191.ciphers;

import java.util.StringTokenizer;

public class Affine {
    public static String encode(String inputText, String key) {
        StringTokenizer newKey = new StringTokenizer(key, " ");
        int m = Integer.parseInt(newKey.nextToken());
        int b = Integer.parseInt(newKey.nextToken());
        return transformText(inputText, m, b);
    }

    private static String transformText(String inputText, int m, int b) {
        StringBuilder outputText = new StringBuilder();

        for(int i=0; i<inputText.length(); i++) {
            char c = inputText.charAt(i);
            int charNum = c - 97;
            int t = (m * charNum + b) % 26;
            char encodedChar = (char) (t + 97);
            outputText.append(encodedChar);
        }
        return outputText.toString();
    }
}
