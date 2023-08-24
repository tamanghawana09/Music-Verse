package Client;

import dev.musicVerse.Design;

import javax.sound.sampled.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class MusicHandler {
    Socket socket;
    public Clip clip;
    public  long clipPosition = 0;
    public boolean isPlaying = false;
    public byte[] audioData;
    public ByteArrayOutputStream byteArrayOutputStream;
    public InputStream inputStream;
    public int currentSongIndex = -1; // Initialize to an invalid index



    //Design Class


    public void connectServer(){
        try {
            socket = new Socket("localhost",12345);
            System.out.println("Connection Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void playMusic() throws LineUnavailableException, IOException, InterruptedException, UnsupportedAudioFileException {
        if (audioData != null) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioData);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            Thread audioThread = new Thread(() -> {
                isPlaying = true;
                clip.setMicrosecondPosition(clipPosition);
                clip.start();
                try {
                    Thread.sleep(clip.getMicrosecondLength() / 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clipPosition = clip.getMicrosecondPosition();
                if (clipPosition >= clip.getMicrosecondLength()) {
                    isPlaying = false;
                }
                clip.close();
            });

            audioThread.start();
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
            playMusic();
        } catch (LineUnavailableException | IOException | InterruptedException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] fetchDataFromServer() {
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




}


