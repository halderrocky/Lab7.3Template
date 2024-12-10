package edu.sdccd.cisc191.template;

import java.net.*;
import java.io.*;
import java.util.Scanner;
/** * This class represents a client that connects
 * to a server, performs array operations, * handles customer operations,
 * and manages a music library. */
public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

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

    private static MusicLibrary musicLibrary = new MusicLibrary();

    private static void handleMusicLibrary() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Music Library Menu:");
            System.out.println("1. Add Song");
            System.out.println("2. Remove Song");
            System.out.println("3. Display Songs");
            System.out.println("4. Save Songs to File");
            System.out.println("5. Load Songs from File");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Song Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Artist: ");
                    String artist = scanner.nextLine();
                    System.out.print("Enter Album: ");
                    String album = scanner.nextLine();
                    System.out.print("Enter Release Date: ");
                    String releaseDate = scanner.nextLine();
                    Song song = new Song();
                    song.setTitle(title);
                    song.setArtist(artist);
                    song.setAlbum(album);
                    song.setReleaseDate(releaseDate);
                    musicLibrary.addSong(song);
                    break;
                case 2:
                    System.out.print("Enter Song Title to Remove: ");
                    title = scanner.nextLine();
                    Song songToRemove = musicLibrary.getSongs().stream()
                            .filter(s -> s.getTitle().equals(title))
                            .findFirst().orElse(null);
                    if (songToRemove != null) {
                        musicLibrary.removeSong(songToRemove);
                    } else {
                        System.out.println("Song not found.");
                    }
                    break;
                case 3:
                    System.out.println("Songs in Library:");
                    for (Song s : musicLibrary.getSongs()) {
                        System.out.println(s.getTitle() + " by " + s.getArtist());
                    }
                    break;
                case 4:
                    System.out.print("Enter filename to save songs: ");
                    String saveFile = scanner.nextLine();
                    musicLibrary.saveSongsToFile(saveFile);
                    break;
                case 5:
                    System.out.print("Enter filename to load songs: ");
                    String loadFile = scanner.nextLine();
                    musicLibrary.loadSongsFromFile(loadFile);
                    break;
                case 6:
                    return; // Back to main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Start Connection");
            System.out.println("2. Perform Array Operations");
            System.out.println("3. Handle Customer Operations");
            System.out.println("4. Manage Music Library");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try {
                        client.startConnection("127.0.0.1", 4444);
                        System.out.println(client.sendRequest().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    performArrayOperations();
                    break;
                case 3:
                    handleCustomerOperations();
                    break;
                case 4:
                    handleMusicLibrary();
                    break;
                case 5:
                    try {
                        client.stopConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void performArrayOperations() {
        Scanner scanner = new Scanner(System.in);
        int[] array = new int[10]; // Example array
        while (true) {
            System.out.println("Array Operations Menu:");
            System.out.println("1. Get at Index");
            System.out.println("2. Set at Index");
            System.out.println("3. Find Index of Value");
            System.out.println("4. Print All");
            System.out.println("5. Delete at Index");
            System.out.println("6. Expand");
            System.out.println("7. Shrink");
            System.out.println("8. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter index: ");
                    int index = scanner.nextInt();
                    if (index >= 0 && index < array.length) {
                        System.out.println("Value at index " + index + ": " + array[index]);
                    } else {
                        System.out.println("Invalid index. Please try again.");
                    }
                    break;
                case 2:
                    System.out.print("Enter index: ");
                    index = scanner.nextInt();
                    if (index >= 0 && index < array.length) {
                        System.out.print("Enter value: ");
                        int value = scanner.nextInt();
                        array[index] = value;
                    } else {
                        System.out.println("Invalid index. Please try again.");
                    }
                    break;
                case 3:
                    System.out.print("Enter value: ");
                    int value = scanner.nextInt();
                    boolean found = false;
                    for (int i = 0; i < array.length; i++) {
                        if (array[i] == value) {
                            System.out.println("Value found at index: " + i);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Value not found.");
                    }
                    break;
                case 4:
                    System.out.println("Array elements:");
                    for (int i : array) {
                        System.out.print(i + " ");
                    }
                    System.out.println();
                    break;
                case 5:
                    System.out.print("Enter index: ");
                    index = scanner.nextInt();
                    if (index >= 0 && index < array.length) {
                        array[index] = 0; // Example delete operation
                        System.out.println("Deleted value at index " + index);
                    } else {
                        System.out.println("Invalid index. Please try again.");
                    }
                    break;
                case 6:
                    array = java.util.Arrays.copyOf(array, array.length + 1);
                    System.out.println("Array expanded.");
                    break;
                case 7:
                    if (array.length > 1) {
                        array = java.util.Arrays.copyOf(array, array.length - 1);
                        System.out.println("Array shrunk.");
                    } else {
                        System.out.println("Array cannot be shrunk further.");
                    }
                    break;
                case 8:
                    return; // Back to main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void handleCustomerOperations() {
        Scanner scanner = new Scanner(System.in);
        Customer customer = new Customer();

        System.out.print("Enter Customer ID: ");
        customer.setId(scanner.nextInt());
        scanner.nextLine(); // Consume newline

        System.out.print("Enter First Name: ");
        customer.setFirstName(scanner.nextLine());

        System.out.print("Enter Last Name: ");
        customer.setLastName(scanner.nextLine());

        System.out.println("Customer Information: " + customer);
    }
}
