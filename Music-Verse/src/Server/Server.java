package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server extends Thread {
    private static List<Socket> clients = new CopyOnWriteArrayList<>(); // Use a thread-safe list
    private static boolean isServerRunning = true;

    public static void main(String[] args) {
        System.out.println("=======SERVER=======");
        try (ServerSocket ss = new ServerSocket(12345)){
            while (isServerRunning) { // Add a flag for gracefully stopping the server
                Socket socket = ss.accept();
                clients.add(socket);
                System.out.println("Client connected: " + socket);

                ClientHandler clientHandler = new ClientHandler(socket, clients);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }

        } catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
        }
    }

    public static void stopServer() {
        isServerRunning = false;
        for (Socket socket : clients) {
            try {
                socket.close();
            } catch (Exception e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
