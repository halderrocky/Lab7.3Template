package edu.sdccd.cisc191;

import edu.sdccd.cisc191.ciphers.Enigma;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnigmaTest {

    @Test
    public void testEnigmaEncryption() {
        Enigma enigma = new Enigma(new int[]{3,3,1}, new int[]{2,4,1}, new int[]{1,5,1}, 'B', "jo ce ba yl");
        System.out.println(enigma.encode("hello".toUpperCase()));
    }
}
