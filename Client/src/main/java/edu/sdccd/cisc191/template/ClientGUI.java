package edu.sdccd.cisc191.template;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientGUI extends Application {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    private TextField indexField;
    private TextField valueField;
    private TextArea resultArea;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    @Override
    public void start(Stage primaryStage) {
        // Title
        primaryStage.setTitle("Client GUI - Array Operations");

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Index Label and Field
        Label indexLabel = new Label("Index:");
        GridPane.setConstraints(indexLabel, 0, 0);
        indexField = new TextField();
        indexField.setPromptText("Enter index");
        GridPane.setConstraints(indexField, 1, 0);

        // Value Label and Field
        Label valueLabel = new Label("Value:");
        GridPane.setConstraints(valueLabel, 0, 1);
        valueField = new TextField();
        valueField.setPromptText("Enter value");
        GridPane.setConstraints(valueField, 1, 1);

        // Buttons
        Button getButton = new Button("Get Value");
        GridPane.setConstraints(getButton, 0, 2);
        getButton.setOnAction(e -> handleGetValue());

        Button setButton = new Button("Set Value");
        GridPane.setConstraints(setButton, 1, 2);
        setButton.setOnAction(e -> handleSetValue());

        // Result Area
        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefHeight(200);
        GridPane.setConstraints(resultArea, 0, 3, 2, 1);

        // Add all to grid
        grid.getChildren().addAll(indexLabel, indexField, valueLabel, valueField, getButton, setButton, resultArea);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            resultAreaAppend("Connected to server at " + SERVER_ADDRESS + ":" + SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            resultAreaAppend("Failed to connect to server.");
        }
    }

    private void handleGetValue() {
        try {
            int index = Integer.parseInt(indexField.getText());
            Request request = new Request("GET", index, 0);
            sendRequest(request);
            Response response = receiveResponse();
            if (response != null) {
                resultAreaAppend("Value at index " + index + ": " + response.getResult());
            }
        } catch (NumberFormatException e) {
            resultAreaAppend("Invalid index input.");
        }
    }

    private void handleSetValue() {
        try {
            int index = Integer.parseInt(indexField.getText());
            int value = Integer.parseInt(valueField.getText());
            Request request = new Request("SET", index, value);
            sendRequest(request);
            Response response = receiveResponse();
            if (response != null) {
                resultAreaAppend("Set value at index " + index + " to " + value);
            }
        } catch (NumberFormatException e) {
            resultAreaAppend("Invalid input.");
        }
    }

    private void sendRequest(Request request) {
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            resultAreaAppend("Error sending request.");
        }
    }

    private Response receiveResponse() {
        try {
            return (Response) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            resultAreaAppend("Error receiving response.");
            return null;
        }
    }

    private void resultAreaAppend(String message) {
        resultArea.appendText(message + "\n");
    }

    @Override
    public void stop() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
            resultAreaAppend("Disconnected from server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
