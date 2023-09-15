package Client;

import dev.musicVerse.Login;

import java.io.*;
import java.net.Socket;

public class LoginHandler {

    private final Login login;

    private Socket socket;

    private final String reqLogin = "REQUEST_FOR_LOGIN";

    private String uName,uPassword;

    public BufferedReader socketDataReader;
    public BufferedInputStream bufferedInputStream;
    OutputStream outputStream;
    public PrintWriter printWriter;
    public LoginHandler(Login login) {
        this.login = login;
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


    public void loginAccountAsync(String uName, String uPassword){
        connectServer();
        this.uName = uName;
        this.uPassword = uPassword;
        System.out.println(uName + " " + uPassword);

        Thread networkThread = new Thread(() -> {
            try {
                loginAccount();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        networkThread.start();

        try {
            socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String socketResponse = socketDataReader.readLine();
            System.out.println(socketResponse);

            if(socketResponse.equals("Server : Login Successful")){
                login.isUserValid = true;
//                login.valid_uName = socketDataReader.readLine();
//                System.out.println(login.valid_uName);
            }else{
                login.isUserValid = false;
            }

//            login.valid_uName = socketDataReader.readLine();
//            System.out.println(login.valid_uName);



        } catch (IOException e) {
            e.printStackTrace();
        }

        closeSocket();


    }

    private void loginAccount() throws IOException {
//        connectServer();

        socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream);



        printWriter.println("REQ_TO_LOGIN");
        printWriter.flush();

        printWriter.println(uName);
        printWriter.flush();

        printWriter.println(uPassword);
        printWriter.flush();

    }


}
