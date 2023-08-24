package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
    private static ArrayList<Socket> clients = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("=======SERVER=======");
        try {
                ServerSocket ss = new ServerSocket(12345);
                while (true) {
                    Socket socket = ss.accept();
                    clients.add(socket);
                    System.out.println("Client connected: " + socket);

                    ClientHandler clientHandler = new ClientHandler(socket, clients);
                    Thread clientThread = new Thread(clientHandler);
                    clientThread.start();
                }

        } catch (Exception e) {
            System.out.println("Client Disconnected");
        }
    }
}
