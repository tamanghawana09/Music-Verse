import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class radio {
    private static AdvancedPlayer player;
    private static InputStream inputStream;

    public static void main(String[] args) {
        // Create the Swing GUI frame
        JFrame frame = new JFrame("Radio Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);

        // Create a "Stop" button
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopRadio();
            }
        });

        // Add the button to the frame
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(stopButton);

        // Show the frame
        frame.setVisible(true);

        String radioStreamUrl = "https://drive.uber.radio/uber/bollywoodlove/icecast.audio"; // Replace with the URL of the radio stream

        try {
            playRadio(radioStreamUrl);
        } catch (IOException | JavaLayerException ex) {
            ex.printStackTrace();
        }
    }

    public static void playRadio(String radioStreamUrl) throws IOException, JavaLayerException {
        // Open a connection to the radio stream URL
        inputStream = new URL(radioStreamUrl).openStream();

        // Create a Bitstream from the input stream
        Bitstream bitstream = new Bitstream(inputStream);

        // Create an AdvancedPlayer to play the audio
        player = new AdvancedPlayer(inputStream);

        // Start playing the radio stream in a separate thread
        Thread playerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        });
        playerThread.start();
    }

    public static void stopRadio() {
        // Stop the player and close the input stream
        if (player != null) {
            player.close();
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
