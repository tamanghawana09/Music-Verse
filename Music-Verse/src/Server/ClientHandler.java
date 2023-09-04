package Server;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private List<Socket> clients;
    private BufferedReader clientDataReader;
    private PrintWriter printWriter;

    // Database connection details
    private static final String DATABASE_URL = "jdbc:mysql://localhost/musicplayerdb";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "@qwe@123";



    private String dataRequest;

    public ClientHandler(Socket clientSocket, List<Socket> clients) throws IOException {
        this.clientSocket = clientSocket;
        this.clients = clients;
        this.clientDataReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.printWriter = new PrintWriter(clientSocket.getOutputStream(), true); // Auto-flush enabled
    }

    @Override
    public void run() {
        try {
            handleClient();
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
        finally {
            try {
                System.out.println("The server is closed!!!");
                clientSocket.close();
                clients.remove(clientSocket);
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private void handleClient() throws IOException {
        String clientRequest;
        while ((clientRequest = clientDataReader.readLine()) != null) {
            System.out.println("Server : Request received : " + clientRequest);
//            System.out.println("Server: Request Received");

            if (clientRequest.equals("REQ_TO_RETRIEVE_DATA")) {
                System.out.println("Req received to send Data");
                handleDataRequest();
            } else if (clientRequest.equals("PLAY_MUSIC")) {
                System.out.println("Request received to play music");
                handleMusicRequest();
            }
        }
    }

    private void handleDataRequest() {
        try {
//            System.out.println("Requestion data from the client!!!");
//            printWriter.flush();
//            printWriter.println("SEND_TABLE_DETAIL");
//            printWriter.flush();

            dataRequest = clientDataReader.readLine();
            System.out.println("Server : Request received to " + dataRequest);
            if (dataRequest.equals("Music")) {
                List<String> databaseData = retrieveDataFromDatabase(DATABASE_URL);
                for (String data : databaseData) {
                    printWriter.println(data);
                    printWriter.flush();
                }
                // Clear the list after sending data
                databaseData.clear();

            }
        } catch (IOException e) {
            System.err.println("Error handling data request: " + e.getMessage());
        }
    }

    private void handleMusicRequest() {
        try {
            String musicName = clientDataReader.readLine();
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
                String query = "SELECT filepath FROM songs WHERE Title = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, musicName);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String filepath = resultSet.getString("filepath");
                    File audioFile = new File(filepath);
                    try (InputStream inputStream = new FileInputStream(audioFile);
                         OutputStream outputStream = clientSocket.getOutputStream()) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.err.println("Error handling music request: " + e.getMessage());
        }
    }

    private List<String> retrieveDataFromDatabase(String databaseUrl) {
        List<String> data = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(databaseUrl, "root", "@qwe@123");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT Title, Artist, Duration, Name FROM Songs " +
                    "INNER JOIN Genres ON Songs.GenreId = Genres.GenreID");

            int sn = 1;
            while (resultSet.next()) {
                String title = resultSet.getString("Title");
                String artist = resultSet.getString("Artist");
                int durationInSeconds = resultSet.getInt("Duration");
                int minutes = durationInSeconds / 60;
                int seconds = durationInSeconds % 60;
                String genre = resultSet.getString("Name");
                String songData = sn + " - " + title + " - " + artist + " - " + minutes + " : " + seconds + " min - " + genre;
                data.add(songData);
                sn++;
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.err.println("Error retrieving data from database: " + e.getMessage());
        }
        return data;
    }
}