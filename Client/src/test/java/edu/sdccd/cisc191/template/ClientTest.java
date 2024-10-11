package edu.sdccd.cisc191.template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest
{

    private ClientNetworking clientNetworking;

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    @BeforeEach
    public void setUp() {
        // Initialize the ClientNetworking with the server address and port
        clientNetworking = new ClientNetworking(SERVER_ADDRESS, SERVER_PORT);
    }

    @Test
    public void testSendRequest() {
        // Create a sample request to send
        Request request = new Request("GET_DATA", 0,42);

        try {
            // Send the request and receive a response
            Response response = clientNetworking.sendRequest(request);

            // Verify that the response is not null
            assertNotNull(response, "Response should not be null");
            // Verify that the response contains the expected result
            assertEquals("Expected Result", response.getResult(), "Expected result does not match");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}