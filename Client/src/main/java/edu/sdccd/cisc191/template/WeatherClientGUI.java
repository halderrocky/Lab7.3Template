package edu.sdccd.cisc191.template;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class WeatherClientGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label locationLabel = new Label("Select a City:");
        ComboBox<String> locationCB = new ComboBox<>();
        ObservableList<String> locations = FXCollections.observableArrayList("San Diego", "New York");
        locationCB.setItems(locations);

        Button getWeatherButton = new Button("Get Weather Report");

        Label resultLabel = new Label();

        getWeatherButton.setOnAction(e -> {
            String selectedCity = locationCB.getValue();
            if (selectedCity != null) {
                WeatherReport[] reports = requestWeatherReports(selectedCity);
                if (reports != null) {
                    StringBuilder resultText = new StringBuilder("Weather Reports for " + selectedCity + ":\n");
                    for (WeatherReport report : reports) {
                        resultText.append(report).append("\n");
                    }
                    resultLabel.setText(resultText.toString());
                } else {
                    resultLabel.setText("No reports available.");
                }
            }
        });

        GridPane grid = new GridPane();
        grid.add(locationLabel, 0, 0);
        grid.add(locationCB, 1, 0);
        grid.add(getWeatherButton, 1, 1);
        grid.add(resultLabel, 1, 2);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Weather Forecast Client");
        primaryStage.show();
    }

    private WeatherReport[] requestWeatherReports(String city) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Send the selected city name to the server
            out.writeObject(city);

            // Receive weather reports from the server
            return (WeatherReport[]) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

