package edu.sdccd.cisc191.ciphers;

public class Atbash {
    public static String encrypt(String text) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char encryptedChar = (char) ('Z' - (Character.toUpperCase(character) - 'A'));
                result.append(Character.isLowerCase(character) ? Character.toLowerCase(encryptedChar) : encryptedChar);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }
    public static String decrypt(String text) {
        return encrypt(text); // Atbash is its own inverse
    }
}
