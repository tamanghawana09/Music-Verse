package Client;

import dev.musicVerse.Register;

import java.io.*;
import java.net.Socket;

public class RegisterHandler {
    Register registerClass;
    private Socket socket;
    private final String reqRegister = "REQUEST_FOR_REGISTRATION";

    public BufferedReader socketDataReader;
    public BufferedInputStream bufferedInputStream;
    OutputStream outputStream;
    public PrintWriter printWriter;
    String fName, uName, email, password;

    public RegisterHandler(Register register) {
        this.registerClass = register;
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
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Socket closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void registerAccountAsync(String fName, String uName, String email, String password) throws IOException {
        connectServer();
        this.fName = fName;
        this.uName = uName;
        this.email = email;
        this.password = password;
        Thread networkThread = new Thread(() -> {
            try {
                registerAccount();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        networkThread.start();
        try {
            socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String socketResponse = socketDataReader.readLine();
            System.out.println(socketResponse);
            if (socketResponse.equals("Server : Registration Successful")) {
                registerClass.isRegistered = true;
            } else {
                registerClass.isRegistered = false;
            }
        } catch (IOException e) {
            // Handle the exception as needed
            e.printStackTrace();
        }

        closeSocket();
    }

    public void registerAccount() throws IOException {
//        connectServer();
        socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream);

        printWriter.println("REQUEST_FOR_REGISTRATION");
        printWriter.flush();

        printWriter.println(fName);
        printWriter.flush();

        printWriter.println(uName);
        printWriter.flush();

        printWriter.println(email);
        printWriter.flush();

        printWriter.println(password);
        printWriter.flush();

//        socketDataReader.close();
//        printWriter.close();

    }

}
