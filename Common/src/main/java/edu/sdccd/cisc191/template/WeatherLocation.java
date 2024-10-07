package edu.sdccd.cisc191.template;

import java.io.Serializable;

// Child class
class WeatherLocation extends Location implements Serializable {
    private String weatherCondition;

    public WeatherLocation(String name, String weatherCondition) {
        super(name);
        this.weatherCondition = weatherCondition;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    @Override
    public String toString() {
        return getName() + ": " + weatherCondition;
    }
}
