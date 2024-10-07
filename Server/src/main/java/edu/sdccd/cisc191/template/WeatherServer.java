package edu.sdccd.cisc191.template;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class WeatherServer {

    private static WeatherLocation[] cities = new WeatherLocation[2]; // One-dimensional array of cities
    private static WeatherReport[][] weatherReports = new WeatherReport[2][2]; // Two-dimensional array of weather reports

    public static void main(String[] args) {
        populateWeatherLocations();
        loadWeatherReports();

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server running at port 12345");
            while (true) {
                Socket socket = serverSocket.accept();
                handleClientRequest(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientRequest(Socket socket) {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Client requests location
            String requestedCity = (String) in.readObject();

            // Matches to WeatherLocation
            for (int i = 0; i < cities.length; i++) {
                if (cities[i].getName().equals(requestedCity)) {
                    // Send weather reports back
                    out.writeObject(weatherReports[i]);
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void populateWeatherLocations() {
        cities[0] = new WeatherLocation("San Diego", "Sunny");
        cities[1] = new WeatherLocation("New York", "Rainy");
    }

    private static void loadWeatherReports() {
        weatherReports[0][0] = new WeatherReport(new Date(), "75F, clear skies, no precipitation");
        weatherReports[0][1] = new WeatherReport(new Date(), "70F, light breeze, no precipitation");

        weatherReports[1][0] = new WeatherReport(new Date(), "60F, overcast, moderate rain");
        weatherReports[1][1] = new WeatherReport(new Date(), "58F, windy, heavy rain");
    }
}

