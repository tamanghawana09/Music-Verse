package dev.musicVerse;

import Client.MusicHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.FlowView;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Design extends JFrame {
    MusicHandler musicHandler;

    private static  final Container container = new Container();
    private static final JPanel panel = new JPanel();
    private static final RoundedPanel playListPanel = new RoundedPanel(10);

    private ImageIcon image1;
    private JLabel imgLbl;


    //Panel to store table and songs data
    RoundedPanel displaySongsPanel = new RoundedPanel(10);

//    Data Table to display data from the database
        public DefaultTableModel tableModel = new DefaultTableModel(
            new String[][]{}, // Initial data (empty)
            new String[]{"S.N", "Title", "Artist", "Duration", "Genre"}
        );

    public JTable displayData = new JTable(tableModel){
        @Override
        public boolean isCellEditable(int row, int column) {
//            return super.isCellEditable(row, column);
            return false;
        }
    };

    public JScrollPane scrollPane = new JScrollPane(displayData);



    private boolean isDisplayPanelVisible = false;


//    Playlist

    JLabel demoPlaylistOne = new JLabel("Domiee");
    JLabel demoPlaylistTwo = new JLabel("Domiee");
    JLabel demoPlaylistThree = new JLabel("Domiee");
    JLabel demoPlaylistFour = new JLabel("Domiee");
    JLabel demoPlaylistFive = new JLabel("Domiee");

    // Create playlist Panel
    RoundedPanel addPlaylistPanel = new RoundedPanel(20);
    JTextField playlistName = new JTextField("Name of Playlist");
    RoundedButton addPlaylist = new RoundedButton("Add",20);


    private boolean isCreatePlaylistPanelVisible = false;





    private final boolean isPanelVisible = false;

   private Point mousePressLocation;
//    JPanel imgpnl = new JPanel(){
//        protected void paintComponent(Graphics g){
//            super.paintComponent(g);
//            Image image = new ImageIcon(this.getClass().getResource("Images/edge.jpg")).getImage();
//            g.drawImage(image,0,500,this.getWidth(),this.getHeight(),this);
//        }
//    };
    public Design(){
        musicHandler = new MusicHandler(this);
//        musicHandler.connectServer();
        //Colors and Fonts properties
        Color backgroundColor = Color.decode("#00000");
        Color panelColor = Color.decode("#1b2223");
        Color greenColor = Color.decode("#0EF6CC");
        Color whiteColor = Color.decode("#F4FEFD");
        Color YellowColor = Color.decode("#f9e509");
        Color userPnlColor = Color.decode("#3A4F50");
        Font pnlfont = new Font("Tahoma",Font.PLAIN,25);
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


        //Search panel
        RoundedPanel searchPnl = new RoundedPanel(35);
        searchPnl.setBounds(500,20,400,35);
        searchPnl.setBackground(panelColor);
        container.add(searchPnl);

        JTextField searchTf = new JTextField("Search");
        searchTf.setFont(new Font("Tahoma",Font.PLAIN,15));
        searchTf.setForeground(whiteColor);
        searchTf.setBackground(panelColor);
        searchTf.setBorder(null);
        searchTf.setBounds(510,28,380,20);
        container.add(searchTf);
        searchTf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(searchTf.getText().equals("Search")){
                    searchTf.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(searchTf.getText().isEmpty()){
                    searchTf.setText("Search");
                }
            }
        });
        searchTf.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if(searchTf.getText().equals("Search")) {
                   searchTf.setText("");
               }
            }
        });

        //This is for songs to be search in server or database
//        searchTf.addKeyListener(new KeyAdapter() {
//        });
//

        //Top label components

        //Trending New Hits label
        JLabel lbl =  new JLabel("Trending New Hits");
        lbl.setForeground(whiteColor);
        lbl.setBounds(270,100,150,30);
        lbl.setFont(new Font("Tahoma",Font.PLAIN,15));
        container.add(lbl);

        JLabel lbl2 = new JLabel("Nachahe ko Hoina");
        lbl2.setForeground(whiteColor);
        lbl2.setBounds(270,170,400,40);
        lbl2.setFont(new Font("Tahoma",Font.BOLD,40));
        container.add(lbl2);

        JLabel lbl3 = new JLabel("The Edge Band Nepal");
        lbl3.setForeground(userPnlColor);
        lbl3.setBounds(270,220,300,30);
        lbl3.setFont(new Font("Tahoma",Font.BOLD,20));
        container.add(lbl3);

        JLabel lbl4 = new JLabel("1 Million Plays");
        lbl4.setForeground(whiteColor);
        lbl4.setBounds(500,220,150,30);
        lbl4.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(lbl4);

        //Rounded Button
        RoundedButton rb = new RoundedButton("Listen Now",50);
        rb.setFont(new Font("Tahoma",Font.BOLD,20));
        rb.setBackground(panelColor);
        rb.setForeground(greenColor);
        rb.setBounds(270,280,180,50);
        rb.setBorderPainted(false);
        container.add(rb);


        //left panel components
        JLabel nameLbl = new JLabel("Music-Verse");
        nameLbl.setBounds(90,15,150,30);
        nameLbl.setFont(new Font("Tahoma",Font.PLAIN,25));
        nameLbl.setForeground(greenColor);
        container.add(nameLbl);

        JPanel designPnl = new JPanel();
        designPnl.setBounds(0,60,250,2);
        designPnl.setBackground(greenColor);
        container.add(designPnl);

        //Menu label
        JLabel menuLbl = new JLabel("MENU");
        menuLbl.setForeground(userPnlColor);
        menuLbl.setBounds(50,80,150,30);
        menuLbl.setFont(new Font("Tahoma",Font.PLAIN,25));
        container.add(menuLbl);

        JLabel explore = new JLabel("Explore");
        explore.setForeground(whiteColor);
        explore.setBounds(100,125,150,30);
        explore.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(explore);
        explore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                explore.setText("<html><u><font color='#0EF6CC'>Explore</font></u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
               explore.setText("<html><font color='#F4FEFD'>Explore</font></html>");
            }
        });

        JLabel genres = new JLabel("Genres");
        genres.setForeground(whiteColor);
        genres.setBounds(100,165,150,30);
        genres.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(genres);
        genres.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                genres.setText("<html><u><font color='#0EF6CC'>Genres</font></u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                genres.setText("<html><font color='#F4FEFD'>Genres</font></html>");
            }
        });

        JLabel albums = new JLabel("Albums");
        albums.setForeground(whiteColor);
        albums.setBounds(100,200,150,30);
        albums.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(albums);
        albums.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                albums.setText("<html><u><font color='#0EF6CC'>Albums</font></u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                albums.setText("<html><font color='#F4FEFD'>Albums</font></html>");
            }
        });

        JLabel artists = new JLabel("Artists");
        artists.setForeground(whiteColor);
        artists.setBounds(100,235,150,30);
        artists.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(artists);
        artists.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                artists.setText("<html><u><font color='#0EF6CC'>Artists</font></u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
               artists.setText("<html><font color='#F4FEFD'>Artists</font></html>");
            }
        });

        //Library
        JLabel library = new JLabel("LIBRARY");
        library.setForeground(userPnlColor);
        library.setBounds(50,280,150,30);
        library.setFont(new Font("Tahoma",Font.PLAIN,25));
        container.add(library);

        JLabel recent = new JLabel("Recent");
        recent.setForeground(whiteColor);
        recent.setBounds(100,325,150,30);
        recent.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(recent);
        recent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                recent.setText("<html><u><font color='#0EF6CC'>Recent</font></u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                recent.setText("<html><font color='#F4FEFD'>Recent</font></html>");
            }
        });

        JLabel fav = new JLabel("Favorites");
        fav.setForeground(whiteColor);
        fav.setBounds(100,360,150,30);
        fav.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(fav);
        fav.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fav.setText("<html><u><font color='#0EF6CC'>Favorites</font></u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fav.setText("<html><font color='#F4FEFD'>Favorites</font></html>");
            }
        });

        JLabel local = new JLabel("Local");
        local.setForeground(whiteColor);
        local.setBounds(100,395,150,30);
        local.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(local);
        local.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
               local.setText("<html><u><font color='#0EF6CC'>Local</font></u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                local.setText("<html><font color='#F4FEFD'>Local</font></html>");
            }
        });



        //RoundedPanel to create table which display Songs.
        //Table should consist Name, Artist, duration Genre.
        displaySongsPanel.setBackground(panelColor);
        displaySongsPanel.setBounds(265,370,625,420);
        container.add(displaySongsPanel);
        displaySongsPanel.setVisible(false);

//      Table to retrieve songs data form database and store it

//        displaySongsPanel.add(displayData);
        scrollPane.setBounds(270,375,615,410);
        container.add(scrollPane);
        container.setComponentZOrder(scrollPane,0);
        scrollPane.setVisible(false);




        //Playlist
        JLabel playlist = new JLabel("PLAYLIST");
        playlist.setForeground(userPnlColor);
        playlist.setBounds(50,440,150,30);
        playlist.setFont(new Font("Tahoma",Font.PLAIN,25));
        container.add(playlist);


        demoPlaylistOne.setForeground(whiteColor);
        demoPlaylistOne.setBounds(100,475,150,30);
        demoPlaylistOne.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(demoPlaylistOne);
        demoPlaylistOne.setVisible(false);

        demoPlaylistTwo.setForeground(whiteColor);
        demoPlaylistTwo.setBounds(100,505,150,30);
        demoPlaylistTwo.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(demoPlaylistTwo);
        demoPlaylistTwo.setVisible(false);

        demoPlaylistThree.setForeground(whiteColor);
        demoPlaylistThree.setBounds(100,535,150,30);
        demoPlaylistThree.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(demoPlaylistThree);
        demoPlaylistThree.setVisible(false);

        demoPlaylistFour.setForeground(whiteColor);
        demoPlaylistFour.setBounds(100,565,150,30);
        demoPlaylistFour.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(demoPlaylistFour);
        demoPlaylistFour.setVisible(false);

        demoPlaylistFive.setForeground(whiteColor);
        demoPlaylistFive.setBounds(100,695,150,30);
        demoPlaylistFive.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(demoPlaylistFive);
        demoPlaylistFive.setVisible(false);



//        RoundedPanel addPLaylistBtnPanel = new RoundedPanel(20);
//        addPLaylistBtnPanel.setBounds(30,625,200,60);
//        addPLaylistBtnPanel.setBackground(YellowColor);
//        container.add(addPLaylistBtnPanel);

        RoundedButton createPlaylistBtn = new RoundedButton("Create Playlist",20);
        createPlaylistBtn.setText("Create");
        createPlaylistBtn.setBounds(32,627,190,50);
        createPlaylistBtn.setForeground(whiteColor);
        createPlaylistBtn.setBackground(greenColor);
        createPlaylistBtn.setFont(new Font("Tahoma",Font.BOLD,24));
//        container.setComponentZOrder(createPlaylistBtn, 0);
        container.add(createPlaylistBtn);


        createPlaylistBtn.addMouseListener(new MouseAdapter() {
//    addPlaylistPanel, playlistName, addPlaylist

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!isCreatePlaylistPanelVisible){
                    addPlaylistPanel.setVisible(true);
                    playlistName.setVisible(true);
                    addPlaylist.setVisible(true);
                    isCreatePlaylistPanelVisible = true;

                }else{
                    addPlaylistPanel.setVisible(false);
                    playlistName.setVisible(false);
                    addPlaylist.setVisible(false);
                    isCreatePlaylistPanelVisible = false;
                }
            }
        });

        addPlaylistPanel.setBounds(450, 300,500,200);
        addPlaylistPanel.setBackground(panelColor);
        container.setComponentZOrder(addPlaylistPanel,0);
        container.add(addPlaylistPanel);
        addPlaylistPanel.setVisible(false);

        playlistName.setBounds(515,375,250,50);
        playlistName.setBackground(panelColor);
        playlistName.setForeground(greenColor);
        playlistName.setFont(new Font("Tahoma",Font.PLAIN,24));
        container.add(playlistName);
        container.setComponentZOrder(playlistName,0);
        playlistName.setVisible(false);

        playlistName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(playlistName.getText().equals("Name of Playlist")){
                    playlistName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(playlistName.getText().isEmpty()){
                    playlistName.setText("Name of Playlist");
                }
            }
        });

        addPlaylist.setBounds(785,375,100,50);
        addPlaylist.setBackground(greenColor);
        addPlaylist.setForeground(whiteColor);
        addPlaylist.setFont(new Font("Tahoma",Font.BOLD,24));
        container.add(addPlaylist);
        container.setComponentZOrder(addPlaylist,0);

        addPlaylist.setVisible(false);


//        addPlaylist.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                if(playlistName.getText().isEmpty() || playlistName.getText().equals("Name of Playlist")){
//
//                }else{
//                    String playlistName = playlist.getName();
//                    if(Objects.equals(demoPlaylistOne.getText(), "Domiee") || demoPlaylistOne.getText().isEmpty()){
//                        demoPlaylistOne.setText(playlistName);
//                        demoPlaylistOne.setVisible(true);
//                    } else if (Objects.equals(demoPlaylistTwo.getText(), "Domiee") || demoPlaylistTwo.getText().isEmpty()) {
//                        demoPlaylistTwo.setText(playlistName);
//                        demoPlaylistTwo.setVisible(true);
//                    }
//                }
//            }
//        });








        playListPanel.setBounds(265,370,625,420);
        playListPanel.setBackground(panelColor);
        container.add(playListPanel);
        playListPanel.setVisible(false);



        JLabel backlbl = new JLabel("playing on device");
        backlbl.setForeground(whiteColor);
        backlbl.setFont(new Font("Tahoma",Font.PLAIN,15));
        backlbl.setBounds(90,737,150,30);
        container.add(backlbl);

        JLabel frontlbl = new JLabel();
        frontlbl.setText("<html>Enjoy <br> Music</html>");
        frontlbl.setForeground(whiteColor);
        frontlbl.setFont(new Font("Tahoma",Font.PLAIN,20));
        frontlbl.setBounds(140,670,150,50);
        container.add(frontlbl);

        RoundedPanel imgpnl = new RoundedPanel(20);
        imgpnl.setBounds(30,660,80,70);
        imgpnl.setBackground(whiteColor);
        imgpnl.setBorder(null);
        container.add(imgpnl);

        RoundedPanel frontpnl = new RoundedPanel(20);
        frontpnl.setBounds(20,650,210,90);
        frontpnl.setBackground(userPnlColor);
        frontpnl.setBorder(null);
        container.add(frontpnl);


        RoundedPanel backpnl = new RoundedPanel(20);
        backpnl.setBounds(25,665,200,105);
        backpnl.setBackground(Color.BLACK);
        backpnl.setBorder(null);
        container.add(backpnl);

        panel.setBounds(0,0,250,800);
        panel.setBackground(panelColor);
        container.add(panel);

        // Top playlists panel in dashboard
        RoundedPanel playlistPnl = new RoundedPanel(10);
        playlistPnl.setBounds(265,370,625,130);
        playlistPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        playlistPnl.setBackground(panelColor);

        JLabel artistLbl = new JLabel();
        artistLbl.setText("Top Playlists");
        artistLbl.setFont(pnlfont);
        artistLbl.setForeground(whiteColor);
        playlistPnl.add(artistLbl);
        container.add(playlistPnl);

        //Player panel  components in dashboard

        //play Button
        JButton playBtn = new JButton(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/playButton.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }

        };
        container.setLayout(null);
        playBtn.setBorderPainted(false);
        playBtn.setBounds(1070,727,35,35);
        container.add(playBtn);
//        playBtn.addMouseListener(new MouseAdapter(){
//            @Override
//            public void mouseClicked(MouseEvent event){
//
//            }
//        });

        //Shuffle Button
        JButton shuffleBtn = new JButton(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/shuffle.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        container.setLayout(null);
        shuffleBtn.setBorderPainted(false);
        shuffleBtn.setBounds(1210,737,20,20);
        container.add(shuffleBtn);

        //Loop Button
        JButton loopBtn = new JButton(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/loop.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        container.setLayout(null);
        loopBtn.setBorderPainted(false);
        loopBtn.setBounds(930,737,20,20);
        container.add(loopBtn);


        RoundedPanel play = new RoundedPanel(10);
        play.setBackground(panelColor);
        play.setBounds(1040,710,90,70);
        container.add(play);

        RoundedPanel playPnl = new RoundedPanel(10);
        playPnl.setBackground(whiteColor);
        playPnl.setBounds(905,700,360,90);
        container.add(playPnl);

        RoundedPanel playerPnl = new RoundedPanel(10);
        playerPnl.setBounds(905,370,360,420);
        playerPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerPnl.setBackground(panelColor);

        JLabel playerLbl = new JLabel();
        playerLbl.setText("Player");
        playerLbl.setFont(pnlfont);
        playerLbl.setForeground(whiteColor);
        playerPnl.add(playerLbl);
        container.add(playerPnl);

        //Genres panel components in dashboard

        //Dance Beats Button
        Font genreFont = new Font("Tahoma", Font.PLAIN,15);
        RoundedButton dbBtn = new RoundedButton("Dance Beats",20);
        dbBtn.setFont(genreFont);
        Color dbColor = Color.decode("#476A8A");
        dbBtn.setBackground(dbColor);
        dbBtn.setForeground(whiteColor);
        dbBtn.setBorderPainted(false);
        dbBtn.setBounds(280,555,100,70);
        container.add(dbBtn);

        //Electro Pop Button
        RoundedButton elecBtn = new RoundedButton("Electro Pop",20);
        elecBtn.setFont(genreFont);
        Color elecColor = Color.decode("#A69984");
        elecBtn.setForeground(whiteColor);
        elecBtn.setBackground(elecColor);
        elecBtn.setBorderPainted(false);
        elecBtn.setBounds(390,555,140,70);
        container.add(elecBtn);

        //Alternative Indie Button
        RoundedButton alterBtn = new RoundedButton("Alternative Indie",20);
        alterBtn.setFont(genreFont);
        Color alterColor = Color.decode("#A24C34");
        alterBtn.setForeground(whiteColor);
        alterBtn.setBackground(alterColor);
        alterBtn.setBorderPainted(false);
        alterBtn.setBounds(280,635,160,70);
        container.add(alterBtn);

        //Hip Hop Button
        RoundedButton hhBtn = new RoundedButton("Hip Hop",20);
        hhBtn.setFont(genreFont);
        Color hhColor = Color.decode("#0D4045");
        hhBtn.setForeground(whiteColor);
        hhBtn.setBackground(hhColor);
        hhBtn.setBorderPainted(false);
        hhBtn.setBounds(450,635,80,70);
        container.add(hhBtn);

        //Classical Period Button
        RoundedButton cpBtn = new RoundedButton("Classical Period",20);
        cpBtn.setFont(genreFont);
        Color cpColor = Color.decode("#A67894");
        cpBtn.setForeground(whiteColor);
        cpBtn.setBackground(cpColor);
        cpBtn.setBorderPainted(false);
        cpBtn.setBounds(280,715,120,65);
        container.add(cpBtn);

        //Hip Hop Rap Button
        RoundedButton hhrapBtn = new RoundedButton("Hip Hop Rap",20);
        hhrapBtn.setFont(genreFont);
        Color hhrapColor = Color.decode("#5547A5");
        hhrapBtn.setForeground(whiteColor);
        hhrapBtn.setBackground(hhrapColor);
        hhrapBtn.setBorderPainted(false);
        hhrapBtn.setBounds(410,715,120,65);
        container.add(hhrapBtn);
        
        RoundedPanel genrePnl = new RoundedPanel(10);
        genrePnl.setBounds(265,510,280,280);
        genrePnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        genrePnl.setBackground(panelColor);

        JLabel genrelbl = new JLabel();
        genrelbl.setText("Genres");
        genrelbl.setFont(pnlfont);
        genrelbl.setForeground(whiteColor);
        genrePnl.add(genrelbl);
        container.add(genrePnl);

        //Top Charts panel in dashboard

        RoundedPanel chartPnl = new RoundedPanel(10);
        chartPnl.setBounds(560,510,330,280);
        chartPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        chartPnl.setBackground(panelColor);

        JPanel chartPnl2 = new JPanel();
        chartPnl2.setBounds(565,550,320,230);
//        chartPnl2.setBackground(panelColor);
        chartPnl2.setLayout(new BorderLayout());
        container.add(chartPnl2);

        JLabel chartlbl = new JLabel();
        chartlbl.setText("Top Charts");
        chartlbl.setFont(pnlfont);
        chartlbl.setForeground(whiteColor);
        chartPnl.add(chartlbl);
        container.add(chartPnl);
        int rows=20;
        Object[][] data ={
                {01, "Bhanai","4:00"},
                {02,"Chinta","4:20"},
                {03, "Upahar","4:50"}
        };
        String[] columnNames = {"S.N","Name","Duration"};
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setBackground(panelColor);
        table.setForeground(whiteColor);
        table.setRowHeight(rows);
        JTableHeader header = table.getTableHeader();
        header.setBackground(panelColor);
        header.setForeground(whiteColor);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(panelColor);
        scrollPane.setBorder(null);
        chartPnl2.setBorder(null);
        chartPnl2.add(scrollPane,BorderLayout.CENTER);

        // Top labels (Music, Playlist)
        JLabel musiclbl = new JLabel();
        musiclbl.setText("Music");
        musiclbl.setForeground(whiteColor);
        musiclbl.setFont(font);
        musiclbl.setBounds(270,20,80,40);
        musiclbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                musiclbl.setText("<html><u><font color='#0EF6CC'>Music</font></u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                musiclbl.setText("<html><font color='#F4FEFD'>Music</font></html>");
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setPanelVisible();
                String songName = "Music";
                try {
                    if(isDisplayPanelVisible){
                        System.out.println("Calling getSongFunction");
                        musicHandler.getSongData(songName);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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

                playlistlbl.setText("<html><u><font color='#0EF6CC'>Playlist</font></u></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                playlistlbl.setText("<html><font color='#F4FEFD'>Playlist</font></html>");
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

        JButton exitBtn = new JButton(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/exit.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }

        };
        container.setLayout(null);
        exitBtn.setBorderPainted(false);
        exitBtn.setBounds(1255,5,20,20);
        container.add(exitBtn);
        exitBtn.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                    System.exit(0);
            }
        });




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

        //Background Edge band image
        image1 = new ImageIcon(getClass().getResource("/Images/edge.jpg"));
        imgLbl = new JLabel(image1);
        imgLbl.setBounds(700,30,600,400);
        container.add(imgLbl);

        setLocationRelativeTo(null);
        setLayout(null);
        setContentPane(container);
    }

    public void setPanelVisible(){
        if(isDisplayPanelVisible){
            displaySongsPanel.setVisible(false);
            scrollPane.setVisible(false);
            isDisplayPanelVisible = false;
        }else{
            displaySongsPanel.setVisible(true);
            isDisplayPanelVisible = true;
            scrollPane.setVisible(true);
            tableModel.setRowCount(0);
        }
    }

}
