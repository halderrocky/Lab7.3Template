package edu.sdccd.cisc191.template;

import static org.junit.jupiter.api.Assertions.*;

class VehicleResponseTest {
    private VehicleResponse vehicleResponse;
    private VehicleRequest vehicleRequest;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        vehicleRequest = new VehicleRequest(2023, "Tesla", "Model S");
        vehicleResponse = new VehicleResponse(vehicleRequest, 15000, 69999, 5, 4, new String[]{"GPS", "Sunroof"});
    }

    @org.junit.jupiter.api.Test
    void getVehicleResponse() {
        assertEquals(vehicleResponse.toString(),
                "VehicleResponse[request=VehicleRequest[year=2023, make='Tesla', model='Model S'], " +
                        "milesOnVehicle=15000, price=69999, numberOfSeats=5, numberOfDoors=4, options=[GPS, Sunroof]]");
    }

    @org.junit.jupiter.api.Test
    void setVehicleResponse() {
        vehicleResponse.setMilesOnVehicle(20000);
        vehicleResponse.setPrice(64999);
        String[] newOptions = {"Leather Seats", "Bluetooth"};
        vehicleResponse.setOptions(newOptions);
        assertEquals(vehicleResponse.toString(),
                "VehicleResponse[request=VehicleRequest[year=2023, make='Tesla', model='Model S'], " +
                        "milesOnVehicle=20000, price=64999, numberOfSeats=5, numberOfDoors=4, options=[Leather Seats, Bluetooth]]");
    }
}