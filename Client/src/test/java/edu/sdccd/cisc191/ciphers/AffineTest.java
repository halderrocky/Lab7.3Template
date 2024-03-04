package edu.sdccd.cisc191.ciphers;

import edu.sdccd.cisc191.ciphers.Caesar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AffineTest {

    private String plainText, cipherText;
    private String key;

    @Test
    void encode() {
        plainText = "abcdefghijklmnopqrstuvwxyz";
        key = "3,2";
        assertEquals("cfiloruxadgjmpsvybehknqtwz", Affine.encode(plainText,key).toLowerCase());
    }

    @Test
    void decode() {
        cipherText = "cfiloruxadgjmpsvybehknqtwz";
        key = "3,2";
        assertEquals("abcdefghijklmnopqrstuvwxyz", Affine.decode(cipherText, key).toLowerCase());
    }
}