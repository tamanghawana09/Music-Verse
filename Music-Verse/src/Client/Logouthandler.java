package Client;

import dev.musicVerse.Logout;

import java.io.*;
import java.net.Socket;

public class Logouthandler {


    //Logout
    Logout logout;
    private Socket socket;
    public BufferedReader socketDataReader;
    public BufferedInputStream bufferedInputStream;
    OutputStream outputStream;
    public PrintWriter printWriter;
    public Logouthandler(Logout logout) {
        this.logout = logout;
    }

    public void connectServer() {
        try {
            socket = new Socket("localhost", 12345);
            System.out.println("Connection Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeSocket() {
        System.out.println("Closing Socket");
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Socket closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logoutAccountAsunc(){
        connectServer();
        Thread networkThread = new Thread(() -> {
            try {
                logoutAccount();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
//        closeSocket();
        networkThread.start();
    }

    private void logoutAccount() throws IOException {
        socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream);

        printWriter.println("REQ_TO_LOGOUT");
        printWriter.flush();

//        printWriter.println();

    }


}
