package edu.sdccd.cisc191.template;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherLocationTest {

    @Test
    public void testGetName() {
        WeatherLocation location = new WeatherLocation("San Diego", "Sunny");
        assertEquals("San Diego", location.getName());
    }

    @Test
    public void testGetWeather() {
        WeatherLocation location = new WeatherLocation("San Diego", "Sunny");
        assertEquals("Sunny", location.getWeatherCondition());
    }
}

