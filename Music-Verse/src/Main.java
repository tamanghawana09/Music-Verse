import dev.musicVerse.Design;
import dev.musicVerse.Logout;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->{
                Design dg = new Design();
                dg.setVisible(true);
        });


    }
}