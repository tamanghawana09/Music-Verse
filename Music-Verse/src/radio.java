import java.net.URL;
import javazoom.jl.player.Player;
public class radio {
    public static void main(String[] args) {
        Thread musicThread = new Thread(()->{
           try{
               playRadio();
           }catch(Exception e){
               System.out.println("There was an error:" + e.getMessage());
           }
        });
        musicThread.start();
    }
    public static void playRadio() throws Exception{
        String radioStreamUrl = "https://drive.uber.radio/uber/bollywoodlove/icecast.audio";
        Player player = new Player(new java.io.BufferedInputStream(new java.net.URL(radioStreamUrl).openStream()));
        System.out.println("Playing radio ...");
        player.play();
    }
}
