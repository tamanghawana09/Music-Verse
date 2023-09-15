package Client;

import dev.musicVerse.Design;
import dev.musicVerse.Logout;

import javax.sound.sampled.*;
import java.io.*;
import java.net.Socket;


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

//            while (socketDataReader.ready()) {
//                socketDataReader.readLine();
//            }

            singerTitle = socketDataReader.readLine();
            design.singerName.setText(singerTitle);


            songTotalDuration = socketDataReader.readLine();
//            System.out.println(songTotalDuration);
            design.stopTime.setText(songTotalDuration);


            try {
                System.out.println("Fetching audio data from the server");
//                inputStream = socket.getInputStream();
//                bufferedInputStream = new BufferedInputStream(inputStream); // Initialize the buffered input stream
//                byteArrayOutputStream = new ByteArrayOutputStream();
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//
//                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
//                    byteArrayOutputStream.write(buffer, 0, bytesRead);
//                }
//                audioData = byteArrayOutputStream.toByteArray();
                audioData = fetchAudioData();

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
            }
        }else{
            printWriter.println(anotherMusicTitle);
            printWriter.flush();


            printWriter.println(musicTitle);
            printWriter.flush();

            audioData = fetchAudioData();

            if(prevData == null){
                prevData = audioData;
            }
            System.out.println("Hii");

            runmusic();

        }
    }

    public byte[] fetchAudioData(){
//        try {
//            System.out.println("Fetching audio data from the server");
//            inputStream = socket.getInputStream();
//            bufferedInputStream = new BufferedInputStream(inputStream); // Initialize the buffered input stream
//            byteArrayOutputStream = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//
//            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
//                byteArrayOutputStream.write(buffer, 0, bytesRead);
//            }
//            return byteArrayOutputStream.toByteArray();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

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
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

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
                    }   catch (InterruptedException e) {
                        e.printStackTrace();
                        // Continue the loop without stopping
                    }
                }
            });

            // Start the slider update thread
            sliderUpdateThread.start();

            // Start playing the music
            clip.setMicrosecondPosition(clipPosition);
            clip.start();
            try {
                Thread.sleep(clip.getMicrosecondLength() / 1000);
            }catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            clipPosition = clip.getMicrosecondPosition();
            if (clipPosition >= clip.getMicrosecondLength()) {
                isPlaying = false;
            }
            clip.close();
            audioData = null;

            playSongAsync();
        }
    }



    public void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            clipPosition = clip.getMicrosecondPosition();
            clip.stop();
            isPlaying = false;
        }
    }

    public void resumePauseMusic() {
        pauseMusic();
        // Resume logic is the same as play, so call play
        try {
            runmusic();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
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
            System.out.println("User looged in");
            design.userlabel.setText(userName);
        }else{
            System.out.println("User not logged in");
        }



    }

    public void check_Logged_In_UserAsync() {
        connectServer();
        Thread checkLogIn = new Thread(() ->{
            try {
                check_Logged_In_User();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        checkLogIn.start();
    }
}


//public void playMusicAsync(String music) {
//        Thread audioThread = new Thread(() -> {
//            try {
//                playMusic(music);
//            } catch (LineUnavailableException | IOException | InterruptedException | UnsupportedAudioFileException e) {
//                // Handle exceptions
//                e.printStackTrace();
//            }
//        });
//        audioThread.start();
//    }


//    public void playMusic(String music) throws LineUnavailableException, IOException, InterruptedException, UnsupportedAudioFileException {
//
////        connectServer();
//        clientReq = "PLAY_MUSIC";
//        this.songName = music;
//
//
//        if (audioData != null) {
//                printWriter.println(songName);
//
//                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
//
//                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
//                clip = AudioSystem.getClip();
//                clip.open(audioInputStream);
//
//        Thread audioThread = new Thread(() -> {
//        isPlaying = true;
//        clip.setMicrosecondPosition(clipPosition);
//        clip.start();
//        try {
//        Thread.sleep(clip.getMicrosecondLength() / 1000);
//        } catch (InterruptedException e) {
//        e.printStackTrace();
//        }
//        clipPosition = clip.getMicrosecondPosition();
//        if (clipPosition >= clip.getMicrosecondLength()) {
//        isPlaying = false;
//        }
//        clip.close();
//        });
//
//        audioThread.start();
//        }
//
//    }
//
//    public void pauseMusic() {
//        if (clip != null && clip.isRunning()) {
//            clipPosition = clip.getMicrosecondPosition();
//            clip.stop();
//            isPlaying = false;
//        }
//    }
//
//    public void resumePauseMusic() {
//        pauseMusic();
//        // Resume logic is the same as play, so call play
//        try {
//            playMusic(this.songName);
//        } catch (LineUnavailableException | IOException | InterruptedException | UnsupportedAudioFileException e) {
//            throw new RuntimeException(e);
//        }
//    }
//


