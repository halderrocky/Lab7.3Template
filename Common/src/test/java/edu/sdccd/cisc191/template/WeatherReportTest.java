package edu.sdccd.cisc191.template;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherReportTest {

    @Test
    public void testGetDate() {
        Date date = new Date();
        WeatherReport report = new WeatherReport(date, "75°F, clear skies");
        assertEquals(date, report.getReportDate());
    }

    @Test
    public void testGetDescription() {
        WeatherReport report = new WeatherReport(new Date(), "75°F, clear skies");
        assertEquals("75°F, clear skies", report.getReportDetails());
    }
}
