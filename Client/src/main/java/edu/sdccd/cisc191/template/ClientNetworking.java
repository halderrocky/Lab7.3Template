package edu.sdccd.cisc191.template;
import java.io.*;
import java.net.Socket;

public class ClientNetworking {

    private String serverAddress;
    private int serverPort;

    public ClientNetworking(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public Response sendRequest(Request request) throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            // Establish a connection to the server
            System.out.println("Attempting to connect to the server...");
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to the server.");

            // Create output stream to send request to the server
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(request);
            out.flush();
            System.out.println("Request sent: " + request);

            // Create input stream to receive response from the server
            in = new ObjectInputStream(socket.getInputStream());
            Response response = (Response) in.readObject();
            return response;

        } catch (ClassNotFoundException e) {
            throw new IOException("Failed to deserialize the server's response.", e);
        } finally {
            // Close all resources
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        }
    }
}
