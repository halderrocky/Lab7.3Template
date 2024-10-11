package edu.sdccd.cisc191.template;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ArrayOperations arrayOps;
    private Array2DOperations array2DOps;
    private static final int PORT = 8080;
    private ExecutorService executor;

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }

    public Server() {
        arrayOps = new ArrayOperations(10);
        array2DOps = new Array2DOperations(5, 5);
        executor = Executors.newFixedThreadPool(10);
        loadData();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT + "...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                executor.execute(new ClientHandler(clientSocket, arrayOps));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        DataPersistence dataPersistence = new DataPersistence();
        int[] loadedArray = dataPersistence.loadArray();
        if (loadedArray != null) {
            arrayOps.setArray(loadedArray);
            System.out.println("Loaded array data.");
        }
        int[][] loadedArray2D = dataPersistence.loadArray2D();
        if (loadedArray2D != null) {
            array2DOps.setArray2D(loadedArray2D);
            System.out.println("Loaded 2D array data.");
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private ArrayOperations arrayOps;

        public ClientHandler(Socket socket, ArrayOperations arrayOps) {
            this.socket = socket;
            this.arrayOps = arrayOps;
        }

        @Override
        public void run() {
            try (
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
            ) {
                Request request;
                while ((request = (Request) in.readObject()) != null) {
                    System.out.println("Received request: " + request.getOperation());
                    Response response = processRequest(request);
                    out.writeObject(response);
                    out.flush();
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Client disconnected.");
            }
        }

        private Response processRequest(Request request) {
            String operation = request.getOperation();
            int index = request.getIndex();
            int value = request.getValue();

            switch (operation.toUpperCase()) {
                case "GET":
                    try {
                        int result = arrayOps.getAtIndex(index);
                        return new Response(String.valueOf(result));
                    } catch (IndexOutOfBoundsException e) {
                        return new Response("Error: Invalid index.");
                    }
                case "SET":
                    try {
                        arrayOps.setAtIndex(index, value);
                        return new Response("Value set successfully.");
                    } catch (IndexOutOfBoundsException e) {
                        return new Response("Error: Invalid index.");
                    }
                default:
                    return new Response("Error: Unknown operation.");
            }
        }
    }
}
