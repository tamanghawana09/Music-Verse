package Client;

import dev.musicVerse.Design;

import javax.sound.sampled.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MusicHandler {
//    private Logout lo;
    Socket socket;
    public Clip clip;
    public long clipPosition = 0;
    public boolean isPlaying = false;
    public byte[] audioData,prevData;
    public ByteArrayOutputStream byteArrayOutputStream;
    public InputStream inputStream;
    public int currentSongIndex = -1; // Initialize to an invalid index
    String clientReq, songName, tableName;
    public BufferedReader socketDataReader;
    public BufferedInputStream bufferedInputStream;
    OutputStream outputStream;
    public PrintWriter printWriter;
    public Design design;
    //    private String serverRequest;
    public String serverResponse;
    public String musicTitle;
//    public String music = "PLAY_DEFAULT_MUSIC";
    public String fetchData = "FETCH_AUDIO_DATA";
    public String anotherMusicTitle = "PLAY_ANOTHER";
    public String reqForPlayMusic;

    public int prevReq = -1;
    public String songTitle,singerTitle;
    public String songTotalDuration;
    public String reqSearch = "SEARCH_MUSIC";

    //Playlist
    String newPlaylist;

    String addMusicToPlaylist;
    String selectedPLaylist;
    String getPlaylistName;
    String music;
    String songData = "";



    public MusicHandler(Design design) {
        this.design = design;
//        this.lo = lo;
    }


    //Design Class


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

    public void getSongDataAsync(String table) {
        Thread networkThread = new Thread(() -> {
            try {
                getSongData(table);
            } catch (IOException e) {
                // Handle exceptions
                e.printStackTrace();
            }
        });
        networkThread.start();
//        closeSocket();

    }

    public void playSongAsync(){
        Thread networkThread = new Thread(() ->{
            try {
                playMusic();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        });
        networkThread.start();
//        closeSocket();

    }

    public void playMusic() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        connectServer();

        socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream);


        this.reqForPlayMusic = design.reqForPlayMusic;
        System.out.println(this.reqForPlayMusic);
        this.musicTitle = design.musicTitle;
        System.out.println(this.musicTitle);

        //sending request for playing musics
        printWriter.println(reqForPlayMusic); //PLAY_DEFAULT_MUSIC
        printWriter.flush();


//        prevReq = socketDataReader.read();
//        System.out.println(prevReq);

        if (musicTitle.equals("DefaultMusic")){
            //sending which music to play
            printWriter.println(musicTitle);  //DefaultMusic
            printWriter.flush();

            songTitle = socketDataReader.readLine();
            design.songName.setText(songTitle);


            singerTitle = socketDataReader.readLine();
            design.singerName.setText(singerTitle);


            songTotalDuration = socketDataReader.readLine();
//            System.out.println(songTotalDuration);
            design.stopTime.setText(songTotalDuration);

            try {
                System.out.println("Fetching audio data from the server");
                inputStream = socket.getInputStream();
                bufferedInputStream = new BufferedInputStream(inputStream); // Initialize the buffered input stream
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
                audioData = byteArrayOutputStream.toByteArray();
//                audioData = fetchAudioData();

                if(prevData == null){
                    prevData = audioData;
                }
//              prevData = audioData;
                System.out.println("audio data received");
//              System.out.println(Arrays.toString(audioData));

                runmusic();

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (socketDataReader != null){
                    socketDataReader.close();
                }if(printWriter != null){
                    printWriter.close();
                }
                closeSocket();
            }
        }else{
            System.out.println( "Client : " +musicTitle);
            if(musicTitle.equals("Default_List")){

            }else{
                playPlaylistMusicAsync(songTitle);
            }

        }
//        else{
//            printWriter.println(anotherMusicTitle);
//            printWriter.flush();
//
//
//            printWriter.println(musicTitle);
//            printWriter.flush();
//
//            audioData = fetchAudioData();
//
//            if(prevData == null){
//                prevData = audioData;
//            }
//            System.out.println("Hii");
//
//            runmusic();
//
//        }
    }

    public byte[] fetchAudioData(){

        try {
            inputStream = socket.getInputStream();
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void runmusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (audioData != null) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);

            // Create a Clip and open the audio stream
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Set up the slider and clip position
            design.slider.setMaximum(100);
            clipPosition = 0;

            // Create a thread to update the slider
            Thread sliderUpdateThread = new Thread(() -> {
                isPlaying = true;
                while (isPlaying) {
                    long microsecondPosition = clip.getMicrosecondPosition();
                    int sliderValue = (int) ((double) microsecondPosition / clip.getMicrosecondLength() * 100);
                    design.slider.setValue(sliderValue);

                    try {
                        Thread.sleep(100); // Adjust the sleep time as needed
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restore interrupted status
                        e.printStackTrace();
                    }
                }
            });


            // Start the slider update thread
            sliderUpdateThread.start();

            // Start playing the music
            clip.setMicrosecondPosition(clipPosition);
            clip.start();

            // The main thread should not wait; it will exit the method
        }

    }
    private void playNextSong() {
        playSongAsync();
    }



    public void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            clipPosition = clip.getMicrosecondPosition();

            // Create a new thread to stop the clip
            Thread stopThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    clip.stop();
                    isPlaying = false;
                }
            });

            stopThread.start();
        }
    }


    public void resumePauseMusic() {
        if (clip != null && clipPosition >= 0 && !clip.isRunning()) {
            // Create a new thread to resume the music
            Thread resumeThread = new Thread(() -> {
                // Set the Clip's position to where it was paused
                clip.setMicrosecondPosition(clipPosition);

                // Start playing the music
                clip.start();

                // Update the isPlaying flag
                isPlaying = true;

                // Clear the clipPosition since music is resumed
                clipPosition = -1;
            });

            resumeThread.start();
        }
    }


//    public byte[] fetchDataFromServer() {
//
//        return null;
//    }

//


    public void getSongData(String table) throws IOException {
        connectServer();
        if (design.tableModel.getRowCount() == 0) {
            try {
                clientReq = "REQ_TO_RETRIEVE_DATA";
                this.tableName = table;
                System.out.println(tableName);
                socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outputStream = socket.getOutputStream();
                printWriter = new PrintWriter(outputStream);

                System.out.println("Sending Request");
                printWriter.println(clientReq);
                printWriter.flush();

//                serverRequest = socketDataReader.readLine();
//                System.out.println("Server : " + serverRequest);
//                if (serverRequest.equals("SEND_TABLE_DETAIL")) {

//                }
                printWriter.println(tableName);
                printWriter.flush();

                System.out.println("Displaying Data in the ScrollPane");
                while ((serverResponse = socketDataReader.readLine()) != null) {
                    System.out.println(serverResponse);
                    String[] rowData = serverResponse.split(" - "); // Split server response
                    design.tableModel.addRow(rowData);
                }

//                serverResponse = " ";
                System.out.println("Data received and Displayed on the Table");
            } catch (Exception e) {
                System.out.println("Could not connect to the server");
            }
            finally {
                // Close the resources other than the socket itself
                if (socketDataReader != null) {
                    socketDataReader.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            }
        }
    }

    public void searchDataAsync(String reqSearchKey){
        Thread networkThread = new Thread(() ->{
            searchData(reqSearchKey);
        });
        networkThread.start();
//        closeSocket();

    }




    public void searchData(String searchedKey){
        connectServer();
        if(design.displaySearchData.getRowCount() == 0){
            try {
                socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outputStream = socket.getOutputStream();
                printWriter = new PrintWriter(outputStream);
                printWriter.println("SEARCH_MUSIC");
                printWriter.flush();

                printWriter.println(searchedKey);
                printWriter.flush();

                boolean dataFound = false; // Flag to track if data is found

                while ((serverResponse = socketDataReader.readLine()) != null) {
                    System.out.println(serverResponse);
                    String[] rowData = serverResponse.split(" - "); // Split server response
                    design.searchTableModel.addRow(rowData);
                    dataFound = true; // Data is found
                }

                if (!dataFound) {
                    // If no data is found, add a "Data not found" message to the table
                    String[] noDataMessage = {"Data not found"};
                    design.searchTableModel.addRow(noDataMessage);
                }
                System.out.println("Data received and Displayed on the Table");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }


    public void check_Logged_In_User() throws IOException {
        connectServer();
        socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream);

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        printWriter.println("CHECK_FOR_USER_LOGIN");
        printWriter.flush();
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        String userName = socketDataReader.readLine();
        System.out.println(userName);
        if(!userName.equals("Server : No Users Logged In")){
//            System.out.println("User looged in");
            design.userlabel.setText(userName);
        }else{
            System.out.println("User not logged in");
        }
    }

    public void check_Logged_In_UserAsync() {
        Thread checkLogIn = new Thread(() ->{
            try {
                check_Logged_In_User();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        checkLogIn.start();
//        closeSocket();
    }



    public void createPlaylistAsync(String newPlaylist) {
        this.newPlaylist = newPlaylist;
        Thread addPlaylist = new Thread(() -> {
            try {
                createPlaylist();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        addPlaylist.start();
//        closeSocket();
    }

    private void createPlaylist() throws IOException {
        connectServer();
        socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream);

        // Send a command to create a new playlist on the server
        printWriter.println("CREATE_NEW_PLAYLIST");
        printWriter.flush();

        // Send the name of the new playlist to be created
        printWriter.println(newPlaylist);
        printWriter.flush();
////        receiveUpdatedPlaylistsFromServer();
//        List<String> reciveData =  receivePlaylistNamesFromServer();
//
////        design.playlistListModel.clear();
////        design.playlistList.repaint();
//
//        for (String playlistName : reciveData){
//            System.out.println("Client : " + playlistName);
//            design.playlistListModel.addElement(playlistName);
//        }
//        design.playlistList.repaint();

    }

    // Receive the updated list of playlist names from the server
//    private void receiveUpdatedPlaylistsFromServer() throws IOException {
//        List<String> updatedPlaylists = new ArrayList<>();
//        String line;
//
//        // Read lines until "END_OF_PLAYLIST" is received
//        while ((line = socketDataReader.readLine()) != null) {
//            System.out.println(line);
////            if (line.equals("END_OF_PLAYLIST")) {
////                break;
////            }
//            updatedPlaylists.add(line);
//        }
//
//        design.playlistListModel.clear(); // Clear existing data
//        for (String playlistName : updatedPlaylists) {
//            design.playlistListModel.addElement(playlistName);
//            System.out.println(playlistName);
//        }
//
//        // Refresh your UI component (JList) to display the updated data
//        design.playlistList.repaint();
//    }


    public void check_playlist_In_UserAsync() {
        Thread checkPlaylist = new Thread(()->{
            try {
                check_playlist_In_User();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        checkPlaylist.start();
//        closeSocket();

    }

    private void check_playlist_In_User() throws IOException {
        connectServer();

        socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream);

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        printWriter.println("CHECK_FOR_PLAYLIST");
        printWriter.flush();
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        // Receive the list of playlist names from the server
        List<String> playlistNames = receivePlaylistNamesFromServer();

        // Process the received playlist names (e.g., display in your UI)
        design.playlistListModel.clear();
        design.playlistListModel1.clear();
        for (String playlistName : playlistNames) {
//            System.out.println("Playlist Name: " + playlistName);
            // Add your logic here to display the playlist names in your UI
            design.playlistListModel.addElement(playlistName);
            design.playlistListModel1.addElement(playlistName);
        }
    }
    private List<String> receivePlaylistNamesFromServer() throws IOException {
        List<String> playlistNames = new ArrayList<>();
        String line;

        // Create a BufferedReader to receive data from the server
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Read lines until "END_OF_PLAYLIST" is received
        while ((line = clientReader.readLine()) != null) {
            if (line.equals("END_OF_PLAYLIST")) {
                break;
            }
            System.out.println(line);
            playlistNames.add(line);
        }

        return playlistNames;
    }

//    Adding songs to playlist
    public void addToPlaylistAsync(String musicTitle, String selectedData) {
        this.selectedPLaylist = selectedData;
        this.addMusicToPlaylist = musicTitle;
        Thread addPlaylist = new Thread(()->{
            try {
                addPlaylist();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        addPlaylist.start();
//        closeSocket();

    }

    private void addPlaylist() throws IOException {
        connectServer();
        socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream);

        printWriter.println("REQ_TO_ADD_SONG_TO_PLAYLIST");
        printWriter.flush();

        printWriter.println(selectedPLaylist);
        printWriter.flush();

        printWriter.println(addMusicToPlaylist);
        printWriter.flush();

    }
//Getting Playlist Data from the database
    public void getPlaylistDataAsync(String getPlaylistName) {
        this.getPlaylistName = getPlaylistName;
        Thread getPlaylist = new Thread(() ->{
           try {
               getPlaylistData();
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
        });
        getPlaylist.start();
//        closeSocket();

    }

    private void getPlaylistData() throws IOException {
        connectServer();
        socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream);

        printWriter.println("REQ_TO_GET_PLAYLIST_DATA");
        printWriter.flush();


        printWriter.println(getPlaylistName);
        printWriter.flush();

        boolean dataFound = false; // Flag to track if data is found

        while ((serverResponse = socketDataReader.readLine()) != null) {
            System.out.println(serverResponse);
            String[] rowData = serverResponse.split(" - "); // Split server response
            design.playlistTableModel.addRow(rowData);
            dataFound = true; // Data is found
        }

        if (!dataFound) {
            // If no data is found, add a "Data not found" message to the table
            String[] noDataMessage = {"Data not found"};
            design.playlistTableModel.addRow(noDataMessage);
        }
        System.out.println("Data received and Displayed on the Table");

        System.out.println("Playlist Data received and Displayed on the Table");


    }

    public void playPlaylistMusicAsync(String songTitle) {
        Thread playSongFromPlaylist = new Thread(() ->{
            try {
                playPlaylistMusic(songTitle);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        playSongFromPlaylist.start();
    }

    private void playPlaylistMusic(String songTitle) throws IOException {
        connectServer();
        socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream);


        printWriter.println("PLAY_PLAYLIST_MUSIC");
        printWriter.flush();


        if(songTitle.isEmpty()){
            int rowCount = design.displayPlayListSongs.getRowCount();
            if(rowCount > 0){
                Random random = new Random();
                int randomRowIndex = random.nextInt(rowCount);
                System.out.println(randomRowIndex);
                music = (String) design.displayPlayListSongs.getValueAt(randomRowIndex, 1);
                System.out.println("Client : "+music);
            }

            printWriter.println(music);
            printWriter.flush();
        }else{
            printWriter.println(songTitle);
            printWriter.flush();
        }


        try {
            System.out.println("Fetching audio data from the server");
            inputStream = socket.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream); // Initialize the buffered input stream
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            audioData = byteArrayOutputStream.toByteArray();
//                audioData = fetchAudioData();

            if(prevData == null){
                prevData = audioData;
            }
//              prevData = audioData;
            System.out.println("audio data received");
//              System.out.println(Arrays.toString(audioData));

            runmusic();

        }catch (UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        } finally {
            if (socketDataReader != null){
                socketDataReader.close();
            }if(printWriter != null){
                printWriter.close();
            }
            closeSocket();
        }


    }

    public void playFromDefaultMusicListAsync(String songtitle) {
        Thread playFromDefault = new Thread(() ->{
           try {
            playFromefaultMusic(songtitle);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
        });
        playFromDefault.start();
    }

    private void playFromefaultMusic(String songtitle) throws IOException {
        connectServer();
        socketDataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream);

        printWriter.println("PLAY_DEFAULT_LISTED_MUSIC");
        printWriter.flush();


//        System.out.println(songtitle);

        if(songtitle.isEmpty()){
            int rowCount = design.tableModel.getRowCount();
            System.out.println("Client  : " + rowCount);
            if(rowCount > 0){
                Random random = new Random();
                int randomRowIndex = random.nextInt(rowCount);
                System.out.println(randomRowIndex);
                music = (String) design.tableModel.getValueAt(randomRowIndex, 1);
                System.out.println("Client : " + music);
            }

            System.out.println(music);

            printWriter.println(music);
            printWriter.flush();
        }else{
            System.out.println(songtitle);

            printWriter.println(songtitle);
            printWriter.flush();
        }
        songTitle = socketDataReader.readLine();
        design.songName.setText(songTitle);


        singerTitle = socketDataReader.readLine();
        design.singerName.setText(singerTitle);


        songTotalDuration = socketDataReader.readLine();
//            System.out.println(songTotalDuration);
        design.stopTime.setText(songTotalDuration);

        try {
            System.out.println("Fetching audio data from the server");
            inputStream = socket.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream); // Initialize the buffered input stream
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            audioData = byteArrayOutputStream.toByteArray();
//                audioData = fetchAudioData();

            if(prevData == null){
                prevData = audioData;
            }
//              prevData = audioData;
            System.out.println("audio data received");
//              System.out.println(Arrays.toString(audioData));

            runmusic();

        }catch (UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        } finally {
            if (socketDataReader != null){
                socketDataReader.close();
            }if(printWriter != null){
                printWriter.close();
            }
            closeSocket();
        }



    }
}

