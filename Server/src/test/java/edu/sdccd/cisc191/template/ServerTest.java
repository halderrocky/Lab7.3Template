package edu.sdccd.cisc191.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    private ArrayOperations arrayOps;

    @BeforeEach
    void setUp() {
        arrayOps = new ArrayOperations(5);
    }

    @Test
    void testGetAtIndex() {
        assertEquals(1, arrayOps.getAtIndex(0));
        assertEquals(5, arrayOps.getAtIndex(4));
    }

    @Test
    void testSetAtIndex() {
        arrayOps.setAtIndex(2, 10);
        assertEquals(10, arrayOps.getAtIndex(2));
    }

    @Test
    void testFindIndexOf() {
        assertEquals(1, arrayOps.findIndexOf(2));
        assertEquals(-1, arrayOps.findIndexOf(100));
    }

    @Test
    void testDeleteAtIndex() {
        arrayOps.deleteAtIndex(3);
        assertEquals(0, arrayOps.getAtIndex(3));
    }

    @Test
    void testExpand() {
        arrayOps.expand(7);
        assertEquals(7, arrayOps.getArray().length);
        assertEquals(0, arrayOps.getArray()[5]);
    }

    @Test
    void testShrink() {
        arrayOps.shrink(3);
        assertEquals(3, arrayOps.getArray().length);
        assertThrows(IndexOutOfBoundsException.class, () -> arrayOps.getAtIndex(4));
    }
}
