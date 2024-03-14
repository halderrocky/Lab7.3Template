package edu.sdccd.cisc191.ciphers;

public class MorseCode {
    public static String morToEng(String[] code, String[] morCode) {
        StringBuilder outputText = new StringBuilder();
        for (String morse : morCode) {
            for (int j = 0; j < code.length; j++) {
                if (morse.equals(code[j])) {
                    outputText.append((char) (j + 'a'));
                    break;
                }
            }
        }
        return outputText.toString();
    }

    // Defines the engToMor method, also prints space
    public static String engToMor(String[] code, String engLang) {
        String[] arr = engLang.split(" ");
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
}
