package edu.sdccd.cisc191.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VehicleResponse {
    private VehicleRequest request;
    private int milesOnVehicle;
    private int price;
    private int numberOfSeats;
    private int numberOfDoors;
    private String[] options;

    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(VehicleResponse vehicleResponse) throws Exception {
        return objectMapper.writeValueAsString(vehicleResponse);
    }

    public static VehicleResponse fromJSON(String input) throws Exception {
        return objectMapper.readValue(input, VehicleResponse.class);
    }

    protected VehicleResponse() {}

    public VehicleResponse(VehicleRequest request, int milesOnVehicle, int price, int numberOfSeats, int numberOfDoors, String[] options) {
        this.request = request;
        this.milesOnVehicle = milesOnVehicle;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
        this.numberOfDoors = numberOfDoors;
        this.options = options;
    }

    @Override
    public String toString() {
        return String.format(
                "VehicleResponse[request=%s, milesOnVehicle=%d, price=%d, numberOfSeats=%d, numberOfDoors=%d, options=%s]",
                request, milesOnVehicle, price, numberOfSeats, numberOfDoors, java.util.Arrays.toString(options));
    }

    // Getters and Setters
    public VehicleRequest getRequest() {
        return request;
    }

    public void setRequest(VehicleRequest request) {
        this.request = request;
    }

    public int getMilesOnVehicle() {
        return milesOnVehicle;
    }

    public void setMilesOnVehicle(int milesOnVehicle) {
        this.milesOnVehicle = milesOnVehicle;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}