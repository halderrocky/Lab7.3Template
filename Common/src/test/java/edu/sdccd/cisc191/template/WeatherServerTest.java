package edu.sdccd.cisc191.template;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherServerTest {

    @Test
    public void testServerClientInteraction() throws IOException, ClassNotFoundException {
        // Send client request
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("San Diego"); // Request for San Diego

            // Server responds
            WeatherReport[] reports = (WeatherReport[]) in.readObject();

            assertNotNull(reports);
            assertEquals(2, reports.length);
            assertEquals("75F, clear skies, no precipitation", reports[0].getReportDetails());
        }
    }
}
