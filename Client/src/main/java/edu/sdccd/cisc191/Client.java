package edu.sdccd.cisc191;

import edu.sdccd.cisc191.ciphers.Atbash;
import edu.sdccd.cisc191.ciphers.Affine;
import edu.sdccd.cisc191.ciphers.Caesar;
import edu.sdccd.cisc191.ciphers.Hill;
import edu.sdccd.cisc191.ciphers.Vigenere;
import edu.sdccd.cisc191.hashes.MD4;
import edu.sdccd.cisc191.hashes.MD4Engine;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;


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

public class Client extends Application{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    ComboBox<String> combobox;
    private TextArea textArea;
    private static Stage window;
    private static Scene scene;
    private static String outputText;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public CustomerResponse sendRequest() throws Exception {
        out.println(CustomerRequest.toJSON(new CustomerRequest(1)));
        return CustomerResponse.fromJSON(in.readLine());
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.startConnection("127.0.0.1", 4444);
            System.out.println(client.sendRequest().toString());
            System.out.println(client.sendRequest().getId());
            client.stopConnection();
        } catch(Exception e) {
            e.printStackTrace();
        }
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Decode");

        //list of Ciphers
        combobox = new ComboBox<>();
        combobox.getItems().addAll(
                "Hill Cipher",
                "Caesar Cipher",
                "Vigenere Cipher",
                "Atbash Cipher",
                "Affine Cipher",
                "MD4 Hash",
                "Engima"
        );
        //listen for selection changes


        //Get Help Button
        Button help = new Button("Help");
        help.setOnAction(e -> {
            switch (combobox.getValue()) {
                case "Hill Cipher":
                    AlertBox.display("Hill Cipher", "The key must be a word or series of letters");
                    break;
                case "Caesar Cipher":
                    AlertBox.display("Caesar Cipher", "The key must be a number");
                    break;
                case "Vigenere Cipher":
                    AlertBox.display("Vigenere Cipher", "The key must be a word or series of letters");
                    break;
                case "Atbash Cipher":
                    AlertBox.display("Atbash Cipher", "There is no key :D");
                    break;
                case "Affine Cipher":
                    AlertBox.display("Affine Cipher","They key must be formatted as #,#");
                    break;
            }
        }
    );

        //labels
        Label label = new Label("Enter key:");

        //text input
        TextField input = new TextField();

        //Text Area
        textArea = new TextArea();
        textArea.setWrapText(true);

        //Decode/encode button
        Button encode = new Button("Encode");
        encode.setOnAction(e -> Client.encode(textArea.getText(), input.getText(), combobox.getValue()));
        Button decode = new Button("Decode");
        decode.setOnAction(e -> Client.decode(textArea.getText(), input.getText(), combobox.getValue()));

        //Import File Button
        Button files = new Button("Select File");
        files.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT files", "*.TXT"),
                    new FileChooser.ExtensionFilter("txt files", ".txt"));
            File selectedFile = fc.showOpenDialog(null);
            if (selectedFile != null) {
                try{
                    Scanner scanner = new Scanner(selectedFile);
                    while(scanner.hasNextLine()){
                        textArea.appendText(scanner.nextLine());
                    }
                }catch (FileNotFoundException f){
                    f.getMessage();
                }
            } else {
                System.out.println("File is not valid");
            }
        });


        //layout
        HBox layout2 = new HBox(10);
        layout2.getChildren().addAll(input, combobox, help);
        layout2.setAlignment(Pos.CENTER);
        HBox layout3 = new HBox(10);
        layout3.setAlignment(Pos.CENTER);
        layout3.getChildren().addAll(encode, decode);
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, layout2, layout3, textArea, files);


        combobox.setOnAction(e -> {
            switch(combobox.getValue()){
                case "Engima":
                    engimaWindow();
                    break;
            }
        });

        scene = new Scene(layout, 800,600);
        window.setScene(scene);
        window.show();
    }

    public static void encode(String inputText, String key, String cipherType) {
        switch(cipherType) {
            case "Hill Cipher":
                outputText = Hill.encode(inputText, key);
                createSecondWindow();
                break;
            case "Caesar Cipher":
                try {
                    outputText = Caesar.encode(inputText, key);
                    createSecondWindow();
                } catch (NumberFormatException e) {
                    AlertBox.display("Error", "ERROR!\nThe key must be a number");
                }
                break;
            case "Vigenere Cipher":
                outputText = Vigenere.encode(inputText, key);
                createSecondWindow();
                break;
            case "Atbash Cipher":
                outputText = Atbash.encrypt(inputText);
                createSecondWindow();
                break;
            case "Affine Cipher":
                try{
                    outputText = Affine.encode(inputText, key);
                    createSecondWindow();
                    break;
                } catch (Exception e) {
                    AlertBox.display("Error", "ERROR!\nInput must be #,#\nThe first number must not be even or a multiple of 13");
                }
                break;
            case "MD4 Hash":
                MD4 md4 = new MD4();
                if(key.toUpperCase().equals("LIST")) {
                    String[] list = inputText.split("\n");
                    StringBuilder output = new StringBuilder();
                    for(String str : list) {
                        output.append(md4.hashAsString(str) + "\n");
                    }

                    outputText = output.toString();
                } else {
                    outputText = md4.hashAsString(inputText);
                }
                createSecondWindow();
                break;
        }
    }

    public static void decode(String inputText, String key, String cipherType){
        switch (cipherType) {
            case "Hill Cipher":
                outputText = Hill.decode(inputText, key);
                createSecondWindow();
                break;
            case "Caesar Cipher":
                try {
                    outputText = Caesar.decode(inputText, key);
                    createSecondWindow();
                } catch (NumberFormatException e) {
                    AlertBox.display("Error", "ERROR!\nThe key must be a number");
                }
                break;
            case "Vigenere Cipher":
                outputText = Vigenere.decode(inputText, key);
                createSecondWindow();
                break;
            case "Atbash Cipher":
                outputText = Atbash.decrypt(inputText);
                createSecondWindow();
                break;
            case "Affine Cipher":
                try{
                    outputText = Affine.decode(inputText, key);
                    createSecondWindow();
                    break;
                } catch(Exception e){
                    AlertBox.display("Error", "ERROR!\nInput must be #,#\nThe first number must not be even or a multiple of 13");
                }
                break;
            case "MD4 Hash":
                String[] list = inputText.split("\n");
                int numThreads = 8; //TODO Get Number of Threads
                HashMap<Character, char[]> formatMap = new HashMap<>(); //TODO: Get this as user input
                formatMap.put('a', new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'});
                formatMap.put('A', new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'});
                formatMap.put('0', new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});

                MD4Engine md4Engine = new MD4Engine(list, formatMap, key, numThreads);
                md4Engine.runMD4Crack();

                HashMap<String, String> crackedPasswords = md4Engine.getCrackedPasswords();
                StringBuilder output = new StringBuilder();
                for(String str : list) {
                    output.append(str).append(" --> ").append(crackedPasswords.get(str)).append("\n");
                }
                outputText = output.toString();
                createSecondWindow();
                break;
        }
    }

    public static void createSecondWindow() {
        Label answer = new Label("Result:");
        VBox layout4 = new VBox(10);
        TextArea output = new TextArea(outputText);
        output.setWrapText(true);
        output.setPrefSize(300,200);

        Button back = new Button("Back");
        back.setOnAction(e -> window.setScene(scene));

        Button file = new Button("Get File");
        file.setOnAction(e -> {
            try {
                getOutputFile();
                AlertBox.display("File Saved", "Your output file has been saved");
            } catch (FileNotFoundException ex) {
                AlertBox.display("Error", "Error!\n No File Found");
            }
        });

        HBox view = new HBox(10);
        view.setAlignment(Pos.CENTER);
        view.getChildren().addAll(back, file);

        layout4.setPadding(new Insets(50,50,50,50));
        layout4.setAlignment(Pos.CENTER);
        layout4.getChildren().addAll(answer, output, view);
        Scene scene2 = new Scene(layout4, 800, 600);
        window.setScene(scene2);
    }

    public static void getOutputFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            PrintWriter pw = new PrintWriter(file);
            pw.println(outputText);
            pw.close();
        }
    }
    public static void engimaWindow(){
        HBox layout2 = new HBox(20);
        layout2.setAlignment(Pos.CENTER);
        Label reflector = new Label("Reflector:");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
                "UKW B",
                "UKW C"
        );

        Button help = new Button("Help");
        help.setOnAction(e -> {
            AlertBox.display("Help", "Position and Ring must be a number from 1-26" +
                    "\nPlugboard must be pairs of unique letters (can't repeat the same letter)");
        });
        layout2.getChildren().addAll(reflector, comboBox, help);

        Label rotor = new Label("Rotor 1:");
        Label position = new Label("Position:");
        Label ring = new Label("Ring:");

        ComboBox<String> rotor1 = new ComboBox<>();
        rotor1.getItems().addAll(
                "I",
                "II",
                "III",
                "IV",
                "V",
                "VI",
                "VII",
                "VIII"
        );

        TextField positionInput = new TextField();
        TextField ringInput = new TextField();

        HBox layout4 = new HBox(50);
        layout4.setAlignment(Pos.CENTER);
        layout4.getChildren().addAll(rotor, rotor1, position, positionInput, ring, ringInput);

        Label rotor22 = new Label("Rotor 2:");
        Label position2 = new Label("Position:");
        Label ring2 = new Label("Ring:");

        ComboBox<String> rotor2 = new ComboBox<>();
        rotor2.getItems().addAll(
                "I",
                "II",
                "III",
                "IV",
                "V",
                "VI",
                "VII",
                "VIII"
        );

        TextField positionInput2 = new TextField();
        TextField ringInput2 = new TextField();


        HBox layout5 = new HBox(50);
        layout5.setAlignment(Pos.CENTER);
        layout5.getChildren().addAll(rotor22, rotor2, position2, positionInput2, ring2, ringInput2);

        Label rotor33 = new Label("Rotor 3:");
        Label position3 = new Label("Position:");
        Label ring3 = new Label("Ring:");
        ComboBox<String> rotor3 = new ComboBox<>();
        rotor3.getItems().addAll(
                "I",
                "II",
                "III",
                "IV",
                "V",
                "VI",
                "VII",
                "VIII"
        );

        TextField positionInput3 = new TextField();
        TextField ringInput3 = new TextField();

        HBox layout6 = new HBox(50);
        layout6.setAlignment(Pos.CENTER);
        layout6.getChildren().addAll(rotor33, rotor3, position3, positionInput3, ring3, ringInput3);

        Label plugboard = new Label("Plugboard");
        TextField plugboardInput = new TextField();

        HBox layout8 = new HBox(20);
        layout8.setAlignment(Pos.CENTER);
        layout8.getChildren().addAll(plugboard, plugboardInput);

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);

        Button back = new Button("Back");
        back.setOnAction(e -> window.setScene(scene));
        Button encode = new Button("Encode");
        encode.setOnAction(e -> System.out.println("Encoding"));
        Button decode = new Button("Cryptanalyze");
        decode.setOnAction(e -> System.out.println("Decoding"));
        HBox layout7 = new HBox(10);
        layout7.setAlignment(Pos.CENTER);
        layout7.getChildren().addAll(encode, decode, back);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(layout2, layout4, layout5, layout6, layout8, textArea, layout7 );
        Scene scene2 = new Scene(layout, 800, 600);
        window.setScene(scene2);
    }
} //end class Client