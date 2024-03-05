package edu.sdccd.cisc191;

import edu.sdccd.cisc191.ciphers.Atbash;
import edu.sdccd.cisc191.ciphers.Caesar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtbashTest {
    private String plainText, cipherText;
    private String key;
    @Test
    void encrypt() {
        plainText = "abcdefghijklmnopqrstuvwxyz";
        key = "2";
        assertEquals("zyxwvutsrqponmlkjihgfedcba", Atbash.encrypt(plainText));
    }

    @Test
    void decrypt() {
        plainText = "zyxwvutsrqponmlkjihgfedcba";
        key = "2";
        assertEquals("abcdefghijklmnopqrstuvwxyz", Atbash.decrypt(plainText));
    }
}