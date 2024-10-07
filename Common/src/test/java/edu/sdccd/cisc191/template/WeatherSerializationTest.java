package edu.sdccd.cisc191.template;

import org.junit.jupiter.api.Test;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherSerializationTest {

    @Test
    public void testWeatherLocationSerialization() throws IOException, ClassNotFoundException {
        // Create an instance of WeatherLocation with two parameters
        WeatherLocation originalLocation = new WeatherLocation("San Diego", "Sunny");

        // Serializes the WeatherLocation object
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(originalLocation);
        out.flush();

        // Deserializes the WeatherLocation object
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        WeatherLocation deserializedLocation = (WeatherLocation) in.readObject();

        // Verifies the data
        assertEquals(originalLocation.getName(), deserializedLocation.getName());
        assertEquals(originalLocation.getWeatherCondition(), deserializedLocation.getWeatherCondition());
    }

    @Test
    public void testWeatherReportSerialization() throws IOException, ClassNotFoundException {
        // Create an instance of WeatherReport
        WeatherReport originalReport = new WeatherReport(new java.util.Date(), "Clear skies, 75°F");

        // Serializes the WeatherReport object
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(originalReport);
        out.flush();

        // Deserializes the WeatherReport object
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        WeatherReport deserializedReport = (WeatherReport) in.readObject();

        // Verifies the data
        assertEquals(originalReport.getReportDate(), deserializedReport.getReportDate());
        assertEquals(originalReport.getReportDetails(), deserializedReport.getReportDetails());
    }
}

