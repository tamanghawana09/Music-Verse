import dev.musicVerse.RoundedPanel;

import java.awt.*;

public class radiotest {
    Container container = new Container();
    public radiotest(){
        RoundedPanel playlistPnl = new RoundedPanel(10);
        playlistPnl.setBounds(265,370,625,130);
        playlistPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        playlistPnl.setBackground(Color.BLACK);
        playlistPnl.setLayout(null);
        playlistPnl.setVisible(true);
        container.add(playlistPnl);
    }

    public static void main(String[] args) {
        radiotest ra = new radiotest();

    }
}
