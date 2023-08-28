package Server;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientHandler implements Runnable{
    private ArrayList<Socket> clients;
    private Socket clientSocket;
    private String clientRequest;


    //Rohan Database Connection

    String databaseUrl = "jdbc:mysql://localhost/musicplayerdb";
    String filepath;



    public ClientHandler(Socket clientSocket, ArrayList<Socket> clients) throws ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.clients = clients;
    }



    @Override
    public void run() {

        try (BufferedReader clientDataReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream())) {

            System.out.println("Waiting For Request");
            clientRequest = clientDataReader.readLine();

            if (!clientRequest.isEmpty()) {
                System.out.println("Request Received");
                if (clientRequest.equals("REQ_TO_RETRIEVE_DATA")) {
                    System.out.println("Request Received to send Database data");
                    clientRequest = "";
                    System.out.println("Deleting Client Req.");

                    System.out.println("Sending database data to client...");

                    // Retrieve data from the database
                    List<String> databaseData = retrieveDataFromDatabase();

                    // Send the data to the client
                    for (String data : databaseData) {
                        printWriter.println(data);
                        printWriter.flush();
                    }

                    System.out.println("Data sent to client");
                }




            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private List<String> retrieveDataFromDatabase() {

        List<String> data = new ArrayList<>();
        try {
            // Replace with your database connection details
//            String jdbcUrl = "jdbc:mysql://localhost:3306/your_database";
//            String username = "your_username";
//            String password = "your_password";

            Connection connection = DriverManager.getConnection(databaseUrl, "root", "@qwe@123");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT Title, Artist, Duration, Name FROM Songs " +
                    "INNER JOIN Genres ON Songs.GenreId = Genres.GenreID");
            int sn = 1;
            while (resultSet.next()) {
                String title = resultSet.getString("Title");
                String artist = resultSet.getString("Artist");
                int durationInSeconds = resultSet.getInt("Duration");
                int minutes = durationInSeconds / 60; // Calculate minutes
                int seconds = durationInSeconds % 60; // Calculate seconds
                String genre = resultSet.getString("Name");
                String songData = sn + " - " + title + " - " + artist + " - " + minutes + " : " + seconds + " min - " + genre;
                data.add(songData);
                sn++;
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

}


//try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            try(Connection conn = DriverManager.getConnection(databaseUrl,"root","@qwe@123")) {
//
//                String query= "SELECT filepath FROM songs WHERE songID = ?";
//
//
//                PreparedStatement preparedStatement = conn.prepareStatement(query);
//                preparedStatement.setInt(1,1);
//
//                ResultSet resultSet = preparedStatement.executeQuery();
//                if(resultSet.next()){
//                    filepath = resultSet.getString("filepath");
//                    File audioFile = new File(filepath);
//
//                    try (InputStream inputStream = new FileInputStream(audioFile);
//                         OutputStream outputStream = clientSocket.getOutputStream()) {
//
//                        byte[] buffer = new byte[1024];
//                        int bytesRead;
//
//                        while ((bytesRead = inputStream.read(buffer)) != -1) {
//                            outputStream.write(buffer, 0, bytesRead);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//        } catch (ClassNotFoundException e) {
//            System.out.println("Client Disconnected");
//        }
