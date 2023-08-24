package Server;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class ClientHandler implements Runnable{
    private ArrayList<Socket> clients;
    private Socket clientSocket;


    //Rohan Database Connection

    String databaseUrl = "jdbc:mysql://localhost/musicplayerdb";
    String filepath;



    public ClientHandler(Socket clientSocket, ArrayList<Socket> clients) throws ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.clients = clients;
    }



    @Override
    public void run() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try(Connection conn = DriverManager.getConnection(databaseUrl,"root","@qwe@123")) {

                String query= "SELECT filepath FROM songs WHERE songID = ?";


                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1,1);

                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    filepath = resultSet.getString("filepath");
                    File audioFile = new File(filepath);

                    try (InputStream inputStream = new FileInputStream(audioFile);
                         OutputStream outputStream = clientSocket.getOutputStream()) {

                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Client Disconnected");
        }

    }

}
