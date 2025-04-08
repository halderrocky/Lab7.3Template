package edu.sdccd.cisc191.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehicleRequestTest {
    private VehicleRequest vehicleRequest;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        vehicleRequest = new VehicleRequest(2023, "Tesla", "Model S");
    }

    @org.junit.jupiter.api.Test
    void getVehicleRequest() {
        assertEquals(vehicleRequest.toString(), "VehicleRequest[year=2023, make='Tesla', model='Model S']");
    }

    @org.junit.jupiter.api.Test
    void setVehicleRequest() {
        vehicleRequest.setYear(2022);
        vehicleRequest.setMake("Ford");
        vehicleRequest.setModel("Mustang");
        assertEquals(vehicleRequest.toString(), "VehicleRequest[year=2022, make='Ford', model='Mustang']");
    }
}