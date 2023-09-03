import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Server1{
    public  static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(12340);
            Socket socket = serverSocket.accept();
            BufferedReader socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            String sendingResponse = "Registered";
            String userName, password;

            userName = socketDataReader.readLine();
            password = socketDataReader.readLine();
            System.out.println(userName);
            System.out.println(password);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost/music";

                try (Connection conn = DriverManager.getConnection(url, "root", "root")) {
                    System.out.println("Database connected: ");

                    // Use PreparedStatement to safely insert data
                    String insertQuery = "INSERT INTO Login (Name, Password) VALUES (?, ?)";
                    try (PreparedStatement pstm = conn.prepareStatement(insertQuery)) {
                        pstm.setString(1, userName);
                        pstm.setString(2, password);

                        // Execute the INSERT statement
                        int rowsAffected = pstm.executeUpdate();

                        if (rowsAffected > 0) {
                            printWriter.println(sendingResponse);
                        } else {
                            printWriter.println("unsuccessful");
                        }
                    } catch (SQLException e) {
                        printWriter.println("unsuccessful");
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    printWriter.println("unsuccessful");
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
