package edu.sdccd.cisc191.template;

import java.net.*;
import java.io.*;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.control.Alert.AlertType;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This program opens a connection to a computer specified
 * as the first command-line argument.  If no command-line
 * argument is given, it prompts the user for a computer
 * to connect to.  The connection is made to
 * the port specified by LISTENING_PORT.  The program reads one
 * line of text from the connection and then closes the
 * connection.  It displays the text that it read on
 * standard output.  This program is meant to be used with
 * the server program, DateServer, which sends the current
 * date and time on the computer where the server is running.
 */

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

//    public void startConnection(String ip, int port) throws IOException {
//        clientSocket = new Socket(ip, port);
//        out = new PrintWriter(clientSocket.getOutputStream(), true);
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//    }

//    public CustomerResponse sendRequest() throws Exception {
//        out.println(CustomerRequest.toJSON(new CustomerRequest(1)));
//        return CustomerResponse.fromJSON(in.readLine());
//    }

//    public void stopConnection() throws IOException {
//        in.close();
//        out.close();
//        clientSocket.close();
//    }

    public void setPreferredTotal(Beverage[] drinks) {
        double total = 0;
        String preferredUnit = "";
//        preferredUnit = box.text;
        for (Beverage drink : drinks) {
            total += drink.convertToPreferred(preferredUnit);
        }
//        totalTextBox.text = String.format("%,.2f", total)
    }


    private Canvas gameCanvas;
    private Scene gameScene;
    private Group gameGroup;

    Stage window;
    Button addButton;
    Button searchButton;
    int totalCalories;
    int totalRuns;
    ListView<String> listView;
    ArrayList<Beverage> beverages;
    int beverageNum;

    public static void main(String[] args)
    {

        Application.launch();
    }

    //start stage and show window of the logs
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Daily Intake Calculator");
        addButton = new Button();
        addButton.setText("Add");
        searchButton = new Button();
        searchButton.setText("Search Date");
        beverages = new ArrayList<Beverage>();
        beverageNum = 0;

        listView = new ListView<>();

        Label runs = new Label();

        HBox totalLayout = new HBox(5);
        Label totalLabel = new Label("Total: ");
        Label totalNumber = new Label();

        ChoiceBox<String> totalUnitsDropdown = new ChoiceBox<>();
        totalUnitsDropdown.getItems().addAll("OZ", "ML");
        totalUnitsDropdown.setValue("OZ");
        totalUnitsDropdown.setOnAction(e -> {
            String units = totalUnitsDropdown.getValue();
            String total = setPreferredTotal(beverages, units);
            totalNumber.setText(total);
        });

        Button saveFileButton = new Button("Save File");

        totalLayout.getChildren().addAll(totalLabel, totalUnitsDropdown, totalNumber);

        HBox bottomLayout = new HBox(50);
        bottomLayout.getChildren().addAll(totalLayout, saveFileButton);
        bottomLayout.setPadding(new Insets(20, 0, 10, 0));
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);

        Alert fileError = new Alert(AlertType.WARNING);
        fileError.setContentText("Error saving entries file.");

        Alert fileSuccess = new Alert(AlertType.INFORMATION);
        fileSuccess.setContentText("Entries File saved in DailyEntries.txt.");

        boolean error = true;
        saveFileButton.setOnAction(e -> {
            if (error) {
                fileError.show();
            } else {
                fileSuccess.show();
            }
        });

        addButton.setOnAction(e -> {
            Beverage beverage = AddNewEntry.display();
            if (beverage != null) {
                beverages.add(beverageNum,beverage);
                String beverageString = beverage.toString();

                listView.getItems().add(beverageString);
                beverageNum += 1;

                String units = totalUnitsDropdown.getValue();
                String total = setPreferredTotal(beverages, units);
                totalNumber.setText(total);

                if (beverage instanceof Soda) {
                    totalCalories += ((Soda) beverage).getCalories();
                    totalRuns += ((Soda) beverage).caloriesToRuns();
                    runs.setText("You need to run " + totalRuns + " times to burn " + totalCalories + " calories.");
                }
            }
        });

        Alert warning = new Alert(AlertType.WARNING);
        warning.setContentText("Please add entries to search.");

        searchButton.setOnAction(e -> {
            if (beverages.isEmpty()) {
                warning.show();
            } else {
                SearchNewEntry.display(beverages);
            }
        });

        HBox buttonsLayout = new HBox(50);
        buttonsLayout.getChildren().addAll(searchButton, addButton);
        buttonsLayout.setPadding(new Insets(20, 0, 10, 0));
        buttonsLayout.setAlignment(Pos.BOTTOM_CENTER);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(listView, buttonsLayout, bottomLayout, runs);

        Scene scene = new Scene(layout, 300, 300);
        window.setScene(scene);
        window.show();

    }

    //Convert all liquid amounts to one preferred unit to total all liquids consumed
    public String setPreferredTotal(ArrayList<Beverage> drinks, String unit) {
        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        ArrayList<Thread> threads = new ArrayList<Thread>();
        System.out.println(unit);
        for (Beverage drink : drinks) {
            if (drink != null) {
                threads.add(new Thread(() -> {
                    total.updateAndGet(v -> new Double((double) (v + drink.convertToPreferred(unit))));
                }));
                threads.get(threads.size()-1).start();

            }
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch(InterruptedException e) {
                System.out.println(e);
            };});


        return String.valueOf(total.get()) + " " + unit;
    }


//    public static void main(String[] args) {
//        Client client = new Client();
//        try {
//            client.startConnection("127.0.0.1", 4444);
////            System.out.println(client.sendRequest().toString());
//
//            client.stopConnection();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
} //end class Client

