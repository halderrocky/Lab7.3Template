package edu.sdccd.cisc191.template;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonTest {

    @Test
    void testRequestCreation() {
        Request req = new Request("GET", 5, 0);
        assertEquals("GET", req.getOperation());
        assertEquals(5, req.getIndex());
        assertEquals(0, req.getValue());
    }

    @Test
    void testResponseCreation() {
        Response res = new Response("Success");
        assertEquals("Success", res.getResult());
    }
}
