package edu.sdccd.cisc191;

import edu.sdccd.cisc191.ciphers.Enigma;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnigmaTest {

    @Test
    public void testEnigmaEncryption() {
        Enigma enigma = new Enigma(new int[]{3,3,3}, new int[]{2,2,2}, new int[]{1,1,1}, 'B', "AB CD EF GH IJ");
        assertEquals("XKCCEGGHAX", enigma.encode("heil hitler".toUpperCase().replaceAll("[^A-Z]", "")));
        assertEquals("HEILHITLER", enigma.encode("XKCCEGGHAX".toUpperCase().replaceAll("[^A-Z]", "")));
    }
}
