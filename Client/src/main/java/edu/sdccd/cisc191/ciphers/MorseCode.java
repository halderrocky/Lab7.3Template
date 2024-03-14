package edu.sdccd.cisc191.ciphers;

public class MorseCode {
    private static String[] code = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", "|"};
    public static String morToEng(String morCode) {
        String[] arr = morCode.split(" ");
        StringBuilder outputText = new StringBuilder();
        for (String morse : arr) {
            for (int j = 0; j < code.length; j++) {
                if (morse.equals(code[j])) {
                    outputText.append((char) (j + 'a'));
                    break;
                }
            }
        }
//        StringBuilder stringBuilder = new StringBuilder();
//        for(String str : stringArray)
//            stringBuilder.append(str);
        return outputText.toString();
    }

    // Defines the engToMor method, also prints space
    public static String engToMor(String engLang) {
        StringBuilder outputText = new StringBuilder();
        for (int i = 0; i < engLang.length(); i++) {
            char letter = engLang.charAt(i);
            if (Character.isLetter(letter) || Character.isDigit(letter)) {
                outputText.append(code[Character.toLowerCase(letter) - 'a'] + " ");
            } else if (letter == ' ') {
                outputText.append("| ");
            }
        }
        return outputText.toString();
    }
}

