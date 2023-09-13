package Server;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.*;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final List<Socket> clients;
    private final BufferedReader clientDataReader;
    private BufferedReader clientFetchAudioReader;
    private final PrintWriter printWriter;

    // Database connection details
    private static final String DATABASE_URL = "jdbc:mysql://localhost/musicplayerdb";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "@qwe@123";



    private String dataRequest;
    private int rowCount;
    private final Random randomNum = new Random();


    public ClientHandler(Socket clientSocket, List<Socket> clients) throws IOException {
        this.clientSocket = clientSocket;
        this.clients = clients;
        this.clientDataReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.clientFetchAudioReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.printWriter = new PrintWriter(clientSocket.getOutputStream(), true); // Auto-flush enabled
    }

    @Override
    public void run() {
        try {
            handleClient();
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }finally {
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
                clientRequest = "";
                handleDataRequest();
            } else if (clientRequest.equals("PLAY_DEFAULT_MUSIC")) {
                System.out.println("Request received to play music");
                clientRequest = "";
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
        System.out.println("Play Music request received");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String musicName = clientDataReader.readLine();
            System.out.println(musicName);

            switch (musicName){
                case "DefaultMusic":

                    try {
                        Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                        String query1 = "SELECT COUNT(*) AS row_count FROM songs";

                        try (PreparedStatement preparedStatement = conn.prepareStatement(query1)) {
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                if (resultSet.next()) {
                                    rowCount = resultSet.getInt("row_count");
                                    System.out.println("Row Count (Excluding Header): " + rowCount);
                                } else {
                                    System.out.println("Table not found.");
                                }
                            }
                        }
                        int randNum = getRandNum(rowCount);
                        System.out.println(randNum);


//                        printWriter.println(randNum);
//                        printWriter.flush();

                        try {
                            String querryForName = "SELECT Title FROM songs WHERE SongID = ?";
                            PreparedStatement preparedStatement = conn.prepareStatement(querryForName);
                            preparedStatement.setInt(1,randNum);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            if(resultSet.next()){
                                String songName = resultSet.getString("Title");

                                printWriter.println(songName);
                                printWriter.flush();
                            }

                            String querryForSingerName = "SELECT Artist FROM songs WHERE SongID = ?";
                            PreparedStatement preparedStatement1 = conn.prepareStatement(querryForSingerName);
                            preparedStatement1.setInt(1,randNum);
                            ResultSet resultSet1 = preparedStatement1.executeQuery();
                            if(resultSet1.next()){
                                String singerName = resultSet1.getString("Artist");

                                printWriter.println(singerName);
                                printWriter.flush();
                            }


                            String queryForDuration = "SELECT Duration FROM songs WHERE SongID = ?";
                            PreparedStatement preparedStatement2 = conn.prepareStatement(queryForDuration);
                            preparedStatement2.setInt(1, randNum);
                            ResultSet resultSet2 = preparedStatement2.executeQuery();
                            if (resultSet2.next()) {
                                int durationInSeconds = resultSet2.getInt("Duration"); // Use "Duration" here
                                int minutes = durationInSeconds / 60;
                                int seconds = durationInSeconds % 60;

                                printWriter.println(minutes + " : " + seconds);
                                printWriter.flush();
                            }

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }


                        String query = "SELECT FilePath FROM songs WHERE SongID = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(query);
                        preparedStatement.setInt(1, randNum);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            String filepath = resultSet.getString("FilePath");
                            System.out.println(filepath);
                            File audioFile = new File(filepath);

                            try (InputStream inputStream = new FileInputStream(audioFile);
                                 OutputStream outputStream = clientSocket.getOutputStream()) {

                                byte[] buffer = new byte[1024];
                                int bytesRead;

                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                                outputStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        conn.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }finally {
                        if (clientDataReader != null){
                            clientDataReader.close();
                        }
                        if(printWriter != null){
                            printWriter.close();
                        }
                    }
                    break;
                case "PLAY_ANOTHER":
                    System.out.println("Hello ");
                    String selectedMusic = clientDataReader.readLine();
                    try {
                        Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                        String query = "SELECT filepath FROM songs WHERE Title = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(query);
                        preparedStatement.setString(1,selectedMusic);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            String filepath = resultSet.getString("filepath");
                            System.out.println(filepath);
                            File audioFile = new File(filepath);

                            try (FileInputStream fileInputStream = new FileInputStream(audioFile);
                                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream())) {
                                byte[] buffer = new byte[1024];
                                int bytesRead;
                                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                                    bufferedOutputStream.write(buffer, 0, bytesRead);
                                }
                                bufferedOutputStream.flush(); // Ensure all data is sent
                            }
                        }


                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    break;
            }
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Error handling music request: " + e.getMessage());
        }
    }

    private int getRandNum(int rowCount) {
        int previousNumber = -1; // Initialize with a value that is out of the valid range
        int randomNumber;

        do {
            randomNumber = randomNum.nextInt(1,rowCount);
        } while (randomNumber == previousNumber);

        previousNumber = randomNumber;
        return randomNumber;
    }

    private List<String> retrieveDataFromDatabase(String databaseUrl) {
        List<String> data = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(databaseUrl, DATABASE_USER, DATABASE_PASSWORD);
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






//                        System.out.println("Sending Audio data to Client");
//                        String query = "SELECT filepath FROM songs WHERE SongID = ?";
//                        PreparedStatement preparedStatement = conn.prepareStatement(query);
//                        preparedStatement.setInt(1, randNum);
//                        ResultSet resultSet = preparedStatement.executeQuery();
//                        if (resultSet.next()) {
//                            String filepath = resultSet.getString("filepath");
//                            System.out.println(filepath);
//                            File audioFile = new File(filepath);
////                               try (InputStream inputStream = new FileInputStream(audioFile);
////                                    OutputStream outputStream = clientSocket.getOutputStream()) {
////                                    byte[] buffer = new byte[1024];
////                                    int bytesRead;
////                                    while ((bytesRead = inputStream.read(buffer)) != -1) {
////                                        outputStream.write(buffer, 0, bytesRead);
////                                    }
////                                }
//                            try (FileInputStream fileInputStream = new FileInputStream(audioFile);
//                                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream())) {
//                                 byte[] buffer = new byte[1024];
//                                 int bytesRead;
//                                 while ((bytesRead = fileInputStream.read(buffer)) != -1) {
//                                     bufferedOutputStream.write(buffer, 0, bytesRead);
//                                 }
//                                 bufferedOutputStream.flush(); // Ensure all data is sent
//                            }
//                        }





