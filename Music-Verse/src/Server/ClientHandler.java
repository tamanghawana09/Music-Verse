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

    private Connection connection;

    private String newPlaylist;



    public ClientHandler(Socket clientSocket, List<Socket> clients) throws IOException, SQLException {
        this.clientSocket = clientSocket;
        this.clients = clients;

        connection = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
//        Statement statement = connection.createStatement();
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

            switch (clientRequest){
                case "REQ_TO_RETRIEVE_DATA":
                    System.out.println("Req received to send Data");
                    clientRequest = "";
                    handleDataRequest();
                    break;
                case "PLAY_DEFAULT_MUSIC":
                    System.out.println("Request received to play music");
                    clientRequest = "";
                    handleMusicRequest();
                    break;

                case "SEARCH_MUSIC":
                    System.out.println("Request recevied for searching Music data");
                    clientRequest = "";
                    handleSearch();
                    break;

                case "REQUEST_FOR_REGISTRATION":
                    System.out.println("Request received for Registering User");
                    handleRegisteration();
                    break;
                case "REQ_TO_LOGIN":
                    System.out.println("Request received for Login User");
                    handleLogin();
                    break;
                case "CHECK_FOR_USER_LOGIN":
                    System.out.println("Request received for Checking User Login");
                    checkUserLogedin();
                    break;
                case "REQ_TO_LOGOUT":
                    System.out.println("Request received for  User Logout");
                    handleLogout();
                    break;
                case "CREATE_NEW_PLAYLIST":
                    System.out.println("Request received for creating a new Playlist");
                    handelPlaylist();
                    break;
                case "CHECK_FOR_PLAYLIST":
                    System.out.println("Request received for checking Playlist");
                    checkForPlaylist();
                    break;
                case "REQ_TO_ADD_SONG_TO_PLAYLIST":
                    System.out.println("Request received for storing Data in playlist");
                    addSongToPLaylist();
                    break;
                case "REQ_TO_GET_PLAYLIST_DATA":
                    System.out.println("Request received to get Playlist data");
                    sendClientPlaylistData();
                    break;
                case "PLAY_PLAYLIST_MUSIC":
                    handelPlaylistMusicPlay();
                    break;
                case "PLAY_DEFAULT_LISTED_MUSIC":
                    handleDefaultListedMusic();
                    break;
                default:
                    break;
            }

        }
    }




    private void sendClientPlaylistData() throws IOException {
        String playlistName = clientDataReader.readLine();

        // Step 1: Find Playlist ID
        int playlistId = findPlaylistId(playlistName);

        // Step 3: Retrieve Song IDs
        List<Integer> songIds = retrieveSongIds(playlistId);

        // Step 4: Retrieve Song Details
        List<String> songs = retrieveSongDetails(songIds);

        // Step 4: Send Song Details to the Client
        sendSongDetailsToClient(songs);

    }

    private void sendSongDetailsToClient(List<String> songs) {
        try {
            // Send the song details to the client
            for (String songDetail : songs) {
                printWriter.println(songDetail);
                printWriter.flush();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        if (dataRequest.equals("Music")) {
//            List<String> databaseData = retrieveDataFromDatabase(DATABASE_URL);
//            for (String data : databaseData) {
//                printWriter.println(data);
//                printWriter.flush();
//            }
//            // Clear the list after sending data
//            databaseData.clear();
//
//        }
    }

    private List<String> retrieveSongDetails(List<Integer> songIds) {
        List<String> songDetails = new ArrayList<>();

        // Define your database query
//        String query = "SELECT Title, Genre, Duration, Artist FROM songs WHERE song_id = ?";
        String query = "SELECT s.Title, g.Name, s.Duration, s.Artist " +
                "FROM songs s " +
                "JOIN genres g ON s.GenreId = g.GenreID " +
                "WHERE s.SongID = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int songId : songIds) {
                // Set the songId as a parameter in the prepared statement
                preparedStatement.setInt(1, songId);

                // Execute the query and get the result set
                ResultSet resultSet = preparedStatement.executeQuery();

                // Check if a row exists for the given songId
                int sn = 1;
                if (resultSet.next()) {
                    // Retrieve song details as strings
                    String title = resultSet.getString("Title");
                    String genre = resultSet.getString("Name");
                    int durationInSeconds = resultSet.getInt("Duration");
                    int minutes = durationInSeconds / 60;
                    int seconds = durationInSeconds % 60;                    String artist = resultSet.getString("Artist");

                    // Concatenate song details into a single string
//                    String songDetail =sn +"Title: " + title + ", Genre: " + genre + ", Duration: " + duration + ", Artist: " + artist;
                    String songDetail1 = sn + " - " + title + " - " + artist + " - " + minutes + " : " + seconds + " min - " + genre;
                    sn++;
                    // Add the song detail string to the list
                    songDetails.add(songDetail1);
                }
            }
        } catch (SQLException e) {
            // Handle any database-related exceptions here
            e.printStackTrace();
        }

        return songDetails;
    }

    private List<Integer> retrieveSongIds(int playlistId) {
        List<Integer> songIds = new ArrayList<>();

        // Define your database query
        String query = "SELECT song_id FROM playlist_song WHERE playlist_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set the playlistId as a parameter in the prepared statement
            preparedStatement.setInt(1, playlistId);

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate through the result set and add song_id values to the list
            while (resultSet.next()) {
                int songId = resultSet.getInt("song_id");
                songIds.add(songId);
            }
        } catch (SQLException e) {
            // Handle any database-related exceptions here
            e.printStackTrace();
        }

        return songIds;
    }

    private int findPlaylistId(String playlistName) {
        int playlistId = -1; // Initialize with an invalid value

        // Define your database query
        String query = "SELECT playlist_id FROM playlist WHERE playlist_Name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set the playlistName as a parameter in the prepared statement
            preparedStatement.setString(1, playlistName);

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a result was found
            if (resultSet.next()) {
                // Retrieve the playlist ID from the result set
                playlistId = resultSet.getInt("playlist_id");
            }
        } catch (SQLException e) {
            // Handle any database-related exceptions here
            e.printStackTrace();
        }

        return playlistId;
    }

    private void addSongToPLaylist() throws IOException {
        String playlistName = clientDataReader.readLine();
        String musicToAdd = clientDataReader.readLine();

        String getPlaylistIdQuery = "SELECT playlist_id FROM playlist WHERE playlist_Name = ?";
        String playlistId = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(getPlaylistIdQuery)) {
            preparedStatement.setString(1, playlistName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                playlistId = resultSet.getString("playlist_id");
            } else {
                throw new SQLException("Playlist not found: " + playlistName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Step 2: Retrieve the song_id based on musicToAdd
        String getSongIdQuery = "SELECT SongID FROM songs WHERE Title = ?";
        String songId = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(getSongIdQuery)) {
            preparedStatement.setString(1, musicToAdd);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                songId = resultSet.getString("SongID");
            } else {
                throw new SQLException("Song not found: " + musicToAdd);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Step 3: Check if the record already exists in playlist_song
        String checkExistingQuery = "SELECT COUNT(*) FROM playlist_song WHERE playlist_id = ? AND song_id = ?";
        int existingRecordCount = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(checkExistingQuery)) {
            preparedStatement.setString(1, playlistId);
            preparedStatement.setString(2, songId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                existingRecordCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (existingRecordCount == 0) {
            // Step 4: Insert a new record into playlist_song
            String insertQuery = "INSERT INTO playlist_song (playlist_id, song_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, playlistId);
                preparedStatement.setString(2, songId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Record already exists in playlist_song.");
        }
    }


    private void checkForPlaylist() {
        String userID = String.valueOf(getUserId());
        List<String> playlistNames = new ArrayList<>();

        System.out.println(userID);

        String query = "SELECT playlist_id FROM user_playlist WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String playlistID = resultSet.getString("playlist_id");
                String playlistName = getPlaylistName(playlistID); // Call a method to get the playlist name
                playlistNames.add(playlistName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Send the list of playlist names to the client
        sendPlaylistNamesToClient(playlistNames);
    }

    private String getPlaylistName(String playlistID) {
        String playlistName = "";

        String getPlaylistNameQuery = "SELECT playlist_Name FROM playlist WHERE playlist_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getPlaylistNameQuery)) {
            preparedStatement.setString(1, playlistID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                playlistName = resultSet.getString("playlist_Name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return playlistName;
    }

    // Send the list of playlist names to the client
    private void sendPlaylistNamesToClient(List<String> playlistNames) {
        try {
            // Send each playlist name to the client
            for (String playlistName : playlistNames) {
                printWriter.println(playlistName);
                printWriter.flush();
            }

            // Send an "END_OF_PLAYLIST" marker to indicate the end of the list
            printWriter.println("END_OF_PLAYLIST");
            printWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserId(){
        String query = "SELECT userID FROM User WHERE is_logged_in = true";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String userID = resultSet.getString("userID");
                System.out.println(userID);
                return userID;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return query;
    }


    private void handelPlaylist() throws IOException {
        String newPlaylist = clientDataReader.readLine();
        String playlistID = "";
        String userID = "";
        String insertQuery = "INSERT INTO playlist (playlist_Name) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, newPlaylist);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String getPlaylistId = "SELECT playlist_id FROM playlist where playlist_Name = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(getPlaylistId)){
            preparedStatement.setString(1,newPlaylist);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                playlistID = resultSet.getString("playlist_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String getUserID = "SELECT userID FROM User WHERE is_logged_in = true";
        try(PreparedStatement preparedStatement = connection.prepareStatement(getUserID)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                userID = resultSet.getString("userID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Playlist ID : " + playlistID);
        System.out.println("User ID : " + userID);

        String insertIntoUserPlaylist = "INSERT INTO User_Playlist (user_id, playlist_id) VALUES (?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(insertIntoUserPlaylist)){
            preparedStatement.setString(1,userID);
            preparedStatement.setString(2,playlistID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        sendPlaylistNamesOfLoggedInUsers();
    }

    private void handleLogout() {
        String updateQuery = "UPDATE User SET is_logged_in = false";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkUserLogedin() {
        // Query your database to get the usernames of users with is_logged_in = true.
        // Example SQL query:
        String query = "SELECT Username FROM User WHERE is_logged_in = true";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean hasLoggedInUsers = false;

            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                System.out.println(username);
                // Send each username back to the client
                printWriter.println(username);
                printWriter.flush();
                hasLoggedInUsers = true;
            }

            if (!hasLoggedInUsers) {
                // No users are currently logged in
                printWriter.println("Server : No Users Logged In");
                printWriter.flush();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleLogin() throws IOException {
        String uName,uPassword;

        uName = clientDataReader.readLine();
        uPassword = clientDataReader.readLine();

        System.out.println(uName + " " + uPassword);

        if (isUserValid(uName, uPassword)) {
            // User exists and password matches
            printWriter.println("Server : Login Successful");
            printWriter.flush();

            printWriter.println(uName);
            printWriter.flush();
        } else {
            // No user found or password doesn't match
            printWriter.println("Server : No User Found");
            printWriter.flush();
        }

    }

    private boolean isUserValid(String uName, String uPassword) {
        // Query your database to check if the user exists with the provided username
        // and if the password matches the stored password for that user.

        // Example SQL query with parameterized query:
        String query = "SELECT COUNT(*) FROM User WHERE Username = ? AND Password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, uName);
            preparedStatement.setString(2, uPassword);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                // User exists and password matches, update is_logged_in to true
                String updateQuery = "UPDATE User SET is_logged_in = true WHERE Username = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setString(1, uName);
                    updateStatement.executeUpdate();
                }
            }

            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private void handleRegisteration() throws IOException {
        String fName, uName, email, password;

        fName = clientDataReader.readLine();
        uName = clientDataReader.readLine();
        email = clientDataReader.readLine();
        password = clientDataReader.readLine();

        System.out.println(fName + " " + uName + " " + email + " " + password + " ");


//        boolean userAdreadyExist = isUserExists(uName,email);
        try {
            if(isUserExists(uName,email)){
                System.out.println("Filed");
                printWriter.println("Server : User Already Exists");
                printWriter.flush();
            }else {
                registerUser(fName,uName,email,password);
                System.out.println("Registration Successful");
                printWriter.println("Server : Registration Successful");
                printWriter.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    private boolean isUserExists(String uName, String email) {
        String query = "SELECT COUNT(*) FROM User WHERE Username = ? OR Email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, uName);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerUser(String fName, String uName, String email, String password) {
        String query = "INSERT INTO User (FullName, Username, Email, Password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, fName);
            preparedStatement.setString(2, uName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    private void handleSearch() throws IOException {
        String searchedKey = clientDataReader.readLine();
        System.out.println("Server : Req to find : " + searchedKey);

        List<String> databaseData = retriveSearchedData(DATABASE_URL, searchedKey);
        for (String data : databaseData){
            printWriter.println(data);
            printWriter.flush();
        }

        databaseData.clear();
    }

    private List<String> retriveSearchedData(String databaseUrl, String searchedKey) {
        List<String> data = new ArrayList<>();
        try {
//            Connection connection = DriverManager.getConnection(databaseUrl, DATABASE_USER, DATABASE_PASSWORD);
            Statement statement = connection.createStatement();

            String sql = "SELECT Title, Artist, Duration, Name " +
                    "FROM Songs " +
                    "INNER JOIN Genres ON Songs.GenreId = Genres.GenreID " +
                    "WHERE Title = ?";
//
//            ResultSet resultSet = statement.executeQuery("SELECT Title, Artist, Duration, Name FROM Songs " +
//                    "INNER JOIN Genres ON Songs.GenreId = Genres.GenreID");


            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,searchedKey);


            ResultSet resultSet = preparedStatement.executeQuery();

            int sn = 1;
            String more = " . ";
            while (resultSet.next()) {
                String title = resultSet.getString("Title");
                String artist = resultSet.getString("Artist");
                int durationInSeconds = resultSet.getInt("Duration");
                int minutes = durationInSeconds / 60;
                int seconds = durationInSeconds % 60;
                String genre = resultSet.getString("Name");
                String songData = sn + " - " + title + " - " + artist + " - " + minutes + " : " + seconds + " min - " + genre + " - " + more;
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
//                        Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                        String query1 = "SELECT COUNT(*) AS row_count FROM songs";

                        try (PreparedStatement preparedStatement = connection.prepareStatement(query1)) {
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
                            PreparedStatement preparedStatement = connection.prepareStatement(querryForName);
                            preparedStatement.setInt(1,randNum);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            if(resultSet.next()){
                                String songName = resultSet.getString("Title");

                                printWriter.println(songName);
                                printWriter.flush();
                            }

                            String querryForSingerName = "SELECT Artist FROM songs WHERE SongID = ?";
                            PreparedStatement preparedStatement1 = connection.prepareStatement(querryForSingerName);
                            preparedStatement1.setInt(1,randNum);
                            ResultSet resultSet1 = preparedStatement1.executeQuery();
                            if(resultSet1.next()){
                                String singerName = resultSet1.getString("Artist");

                                printWriter.println(singerName);
                                printWriter.flush();
                            }


                            String queryForDuration = "SELECT Duration FROM songs WHERE SongID = ?";
                            PreparedStatement preparedStatement2 = connection.prepareStatement(queryForDuration);
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
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
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
//                        conn.close();
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
                case "Default_List":
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

    private void handelPlaylistMusicPlay() throws IOException {
        String music = clientDataReader.readLine();
        int songId = 0;

        String query = "SELECT SongID FROM songs WHERE Title = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, music);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                songId = resultSet.getInt("SongID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(songId);

        String getFilePath = "SELECT FilePath FROM songs WHERE SongID = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(getFilePath);) {
            preparedStatement.setInt(1, songId);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void handleDefaultListedMusic() throws IOException {
        String songName = clientDataReader.readLine();
        int songId = 0;



        String query = "SELECT SongID FROM songs WHERE Title = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, songName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                songId = resultSet.getInt("SongID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println( "Server : " + songId);
        try {
            String querryForName = "SELECT Title FROM songs WHERE SongID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(querryForName);
            preparedStatement.setInt(1,songId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String nameSong = resultSet.getString("Title");

                printWriter.println(nameSong);
                printWriter.flush();
            }

            String querryForSingerName = "SELECT Artist FROM songs WHERE SongID = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(querryForSingerName);
            preparedStatement1.setInt(1,songId);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            if(resultSet1.next()){
                String singerName = resultSet1.getString("Artist");

                printWriter.println(singerName);
                printWriter.flush();
            }


            String queryForDuration = "SELECT Duration FROM songs WHERE SongID = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(queryForDuration);
            preparedStatement2.setInt(1, songId);
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




        String getFilePath = "SELECT FilePath FROM songs WHERE SongID = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(getFilePath);) {
            preparedStatement.setInt(1, songId);
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
                    System.out.println("SERVER: DATA SENT SUCCESSFUlY!!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
                String songData = sn + " - " + title + " - " + artist + " - " + minutes + " : " + seconds + " min - " + genre + " - " + " . ";
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





