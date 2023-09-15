import dev.musicVerse.Design;
//import dev.musicVerse.Local;
import dev.musicVerse.Login;
import dev.musicVerse.Logout;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->{
//        Design dj = new Design();
//        dj.setVisible(true);
//        Local lo = new Local();
//
            Login lg = new Login();
            lg.setVisible(true);
        });


    }
}