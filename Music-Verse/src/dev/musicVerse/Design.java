package dev.musicVerse;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Design extends JFrame {
    private static  final Container container = new Container();
    private static final JPanel panel = new JPanel();

   private Point mousePressLocation;
//    JPanel imgpnl = new JPanel(){
//        protected void paintComponent(Graphics g){
//            super.paintComponent(g);
//            Image image = new ImageIcon(this.getClass().getResource("Images/edge.jpg")).getImage();
//            g.drawImage(image,0,500,this.getWidth(),this.getHeight(),this);
//        }
//    };
    public Design(){
        //Colors and Fonts properties
        Color backgroundColor = Color.decode("#00000");
        Color panelColor = Color.decode("#1b2223");
        Color greenColor = Color.decode("#0EF6CC");
        Color whiteColor = Color.decode("#F4FEFD");
        Color userPnlColor = Color.decode("#3A4F50");
        //Font fontLabel = new Font("Tahoma",Font.PLAIN,20);
        Font font = new Font("Tahoma",Font.PLAIN,20);

        //frame properties
        setUndecorated(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),20,20));
            }
        });

        setSize(1280,800);
        setBackground(backgroundColor);
        setLocation(120,10);
        //left panel
        panel.setBounds(0,0,250,800);
        panel.setBackground(panelColor);
        container.add(panel);

        // Top artist panel in dashboard
        RoundedPanel artistPnl = new RoundedPanel(10);
        artistPnl.setBounds(265,370,625,130);
        artistPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        artistPnl.setBackground(panelColor);

        JLabel artistlbl = new JLabel();
        artistlbl.setText("Top Artists");
        artistlbl.setFont(font);
        artistlbl.setForeground(whiteColor);
        artistPnl.add(artistlbl);
        container.add(artistPnl);

        //Player panel in dashboard
        RoundedPanel playerPnl = new RoundedPanel(10);
        playerPnl.setBounds(905,370,360,420);
        playerPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerPnl.setBackground(panelColor);

        JLabel playerlbl = new JLabel();
        playerlbl.setText("Player");
        playerlbl.setFont(font);
        playerlbl.setForeground(whiteColor);
        playerPnl.add(playerlbl);
        container.add(playerPnl);

        //Genres panel in dashboard
        RoundedPanel genrePnl = new RoundedPanel(10);
        genrePnl.setBounds(265,510,280,280);
        genrePnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        genrePnl.setBackground(panelColor);

        JLabel genrelbl = new JLabel();
        genrelbl.setText("Genres");
        genrelbl.setFont(font);
        genrelbl.setForeground(whiteColor);
        genrePnl.add(genrelbl);
        container.add(genrePnl);

        //Top Charts panel in dashboard
        RoundedPanel chartPnl = new RoundedPanel(10);
        chartPnl.setBounds(560,510,330,280);
        chartPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        chartPnl.setBackground(panelColor);

        JLabel chartlbl = new JLabel();
        chartlbl.setText("Top Charts");
        chartlbl.setFont(font);
        chartlbl.setForeground(whiteColor);
        chartPnl.add(chartlbl);
        container.add(chartPnl);

        // Top labels (Music, Playlist)
        JLabel musiclbl = new JLabel();
        musiclbl.setText("Music");
        musiclbl.setForeground(whiteColor);
        musiclbl.setFont(font);
        musiclbl.setBounds(270,20,80,40);
        musiclbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                musiclbl.setForeground(greenColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                musiclbl.setForeground(whiteColor);
            }
        });
        container.add(musiclbl);

        JLabel playlistlbl = new JLabel();
        playlistlbl.setText("Playlist");
        playlistlbl.setForeground(whiteColor);
        playlistlbl.setFont(font);
        playlistlbl.setBounds(340,20,100,40);
        playlistlbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playlistlbl.setForeground(greenColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                playlistlbl.setForeground(whiteColor);
            }
        });
        container.add(playlistlbl);

        //User Name Panel
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(1000,20,200,45);
        RoundedPanel userPnl = new RoundedPanel(10);
        userPnl.setBackground(userPnlColor);
        userPnl.setBounds(0,0,180,45);
        //User Image Panel
        RoundedPanel userimgPnl = new RoundedPanel(10);
        userimgPnl.setBackground(whiteColor);
        userimgPnl.setBounds(0,0,55,45);
        layeredPane.add(userPnl,Integer.valueOf(1));
        layeredPane.add(userimgPnl,Integer.valueOf(2));
        container.add(layeredPane);

        JButton button = new JButton(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/exit.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }

        };
        container.setLayout(null);
        button.setBorderPainted(false);
        button.setBounds(1255,5,20,20);
        container.add(button);
        button.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                if(event.getSource() == button){
                    System.exit(0);
                }
            }
        });





//        ImageIcon imageIcon = new ImageIcon("/Images/edge.jpg");
//        JLabel imageLabel = new JLabel(imageIcon);
//        imageLabel.setBounds(0,700,this.getWidth(),this.getHeight());
//        container.add(imageLabel);

//        imgpnl.setBackground(Color.WHITE);
//        imgpnl.setBorder(new EmptyBorder(5,5,5,5));
//        imgpnl.setLayout(null);
//        container.add(imgpnl);


        //Add mouse listener to handle dragging (moving) the frame
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                mousePressLocation = e.getPoint();
            }
            @Override
            public void mouseReleased(MouseEvent e){
                mousePressLocation = null;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                Point p = e.getPoint();
                Point location = getLocation();
                setLocation(location.x + (p.x - mousePressLocation.x),location.y +(p.y - mousePressLocation.y));
            }
        });




        //Image
//        imgpnl.setBackground(Color.WHITE);
//        imgpnl.setBorder(new EmptyBorder(5,5,5,5));
//        imgpnl.setLayout(null);
//        container.add(imgpnl);
        setLocationRelativeTo(null);
        setLayout(null);
        setContentPane(container);
    }

}
