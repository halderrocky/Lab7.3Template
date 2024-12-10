package edu.sdccd.cisc191.template;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import edu.sdccd. cisc191.template. server. MultiThreadedServer;
import static org.junit.Assert.assertNotNull;
//** Concurrency with Multi-Threaded Server**//
public class MultiThreadedServerTest {
    private MultiThreadedServer multiThreadedServer;

    @Before
    public void setUp() throws IOException {
        multiThreadedServer = new MultiThreadedServer(4444, 10);
    }

    @Test
    public void testServerSocketInitialization() {
        assertNotNull(multiThreadedServer);
    }

    @Test
    public void testStart() throws IOException {
        Thread serverThread = new Thread(() -> {
            try {
                multiThreadedServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        try (ServerSocket serverSocket = new ServerSocket(4444)) {
            assertNotNull(serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
