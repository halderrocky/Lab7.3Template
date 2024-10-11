package edu.sdccd.cisc191.template;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            //noinspection InfiniteLoopStatement
            while (true) {
                // Wait for a client to connect
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Client connected");

                    // Handle client request
                    handleClientRequest(socket);
                    System.out.println("Client Request Handled");
                } catch (IOException e) {
                    System.err.println("Accept failed: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        }
    }

    private static void handleClientRequest(Socket socket) {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // Read the request from client
            Request request = (Request) in.readObject();
            System.out.println("Received request: " + request.getOperation());

            // Process the request and send a response back
            Response response = new Response("Expected Result");
            out.writeObject(response); // send the response back
            out.flush();
            System.out.println("Response sent");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error handling client request: " + e.getMessage());
        }
    }
}
