import java.net.URL;
import javazoom.jl.player.Player;

public class radio {
    private static volatile boolean stopPlayer = false;

    public static void main(String[] args) {
        Thread musicThread = new Thread(() -> {
            try {
                playRadio();
            } catch (Exception e) {
                System.out.println("There was an error: " + e.getMessage());
            }
        });

        // Start the music thread
        musicThread.start();

        // Simulate stopping the player after a certain time (e.g., 30 seconds)
        try {
            Thread.sleep(30000); // Sleep for 30 seconds
            stopPlayer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void playRadio() throws Exception {
        String radioStreamUrl = "https://drive.uber.radio/uber/bollywoodlove/icecast.audio";
        Player player = new Player(new java.io.BufferedInputStream(new java.net.URL(radioStreamUrl).openStream()));
        System.out.println("Playing radio ...");
        player.play();

        // Check the stopPlayer flag periodically and stop the player if needed
        while (!stopPlayer) {
            Thread.sleep(1000); // Sleep for 1 second before checking again
        }

        // When the loop exits, close the player
        player.close();
        System.out.println("Player stopped.");
    }

    public static void stopPlayer() {
        stopPlayer = true;
    }
}
