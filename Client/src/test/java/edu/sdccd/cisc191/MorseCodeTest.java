package edu.sdccd.cisc191;

import edu.sdccd.cisc191.ciphers.Caesar;
import edu.sdccd.cisc191.ciphers.MorseCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MorseCodeTest {
    private String plainText, cipherText;
    @Test
    void morToEng() {
        cipherText = ".- -... -.-. -.. . ..-. --. .... .. .--- -.- .-.. -- -. --- .--. --.- .-. ... - ..- ...- .-- -..- -.-- --..";
        assertEquals("abcdefghijklmnopqrstuvwxyz", MorseCode.morToEng(cipherText).toLowerCase());
    }

    @Test
    void engToMor() {
        cipherText = "abcdefghijklmnopqrstuvwxyz";
        assertEquals(".- -... -.-. -.. . ..-. --. .... .. .--- -.- .-.. -- -. --- .--. --.- .-. ... - ..- ...- .-- -..- -.-- --.. ", MorseCode.engToMor(cipherText).toLowerCase());
    }
}