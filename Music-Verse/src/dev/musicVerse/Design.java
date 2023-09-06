package dev.musicVerse;

import Client.MusicHandler;


import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import java.io.IOException;

public class Design extends JFrame{
    MusicHandler musicHandler;

    private static  final Container container = new Container();
    private static final JPanel panel = new JPanel();
    private static final RoundedPanel playListPanel = new RoundedPanel(10);

    private ImageIcon image1;
    private JLabel imgLbl;
    private ImageIcon playerImg;
    private JLabel playerLbl;

    //Colors and Fonts properties
    Color backgroundColor = Color.decode("#00000");
    Color panelColor = Color.decode("#1b2223");
    Color greenColor = Color.decode("#0EF6CC");
    Color whiteColor = Color.decode("#F4FEFD");
    //Color YellowColor = Color.decode("#f9e509");
    Color userPnlColor = Color.decode("#3A4F50");
    Font pnlfont = new Font("Tahoma",Font.PLAIN,25);
    Font font = new Font("Tahoma",Font.PLAIN,20);



    //Panel to store table and songs data
    RoundedPanel displaySongsPanel = new RoundedPanel(10);
    RoundedPanel localPnl = new RoundedPanel(10);

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


    public final String musicTitle = "DefaultMusic";

    public JScrollPane scrollPane = new JScrollPane(displayData);



    private boolean isDisplayPanelVisible = false;
    private boolean local = false;



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
    private boolean isMusicPlaying = false;

   private Point mousePressLocation;
//    JPanel imgpnl = new JPanel(){
//        protected void paintComponent(Graphics g){
//            super.paintComponent(g);
//            Image image = new ImageIcon(this.getClass().getResource("Images/edge.jpg")).getImage();
//            g.drawImage(image,0,500,this.getWidth(),this.getHeight(),this);
//        }
//    };
    public Design(){
        //Server
//        musicHandler = new MusicHandler(this);
//        musicHandler.connectServer();


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



        //Local design & components

        localPnl.setBounds(265,370,1005,425);
        localPnl.setBackground(panelColor);
        localPnl.setLayout(null);
        container.add(localPnl);
        localPnl.setVisible(false);

        JLabel localSongsLbl = new JLabel("Local Songs");
        localSongsLbl.setFont(new Font("Tahoma",Font.BOLD,20));
        localSongsLbl.setBounds(10,10,150,40);
        localSongsLbl.setForeground(Color.WHITE);
        localSongsLbl.setVisible(true);
        localPnl.add(localSongsLbl);

        RoundedButton addBtn = new RoundedButton("Add",30);
        addBtn.setBounds(880,10,100,30);
        addBtn.setFont(new Font("Tahoma",Font.PLAIN,15));
        addBtn.setBackground(greenColor);
        addBtn.setBorderPainted(false);
        addBtn.setForeground(Color.BLACK);
        localPnl.add(addBtn);

        RoundedPanel lowerplaypnl = new RoundedPanel(10);
        lowerplaypnl.setBackground(Color.black);
        lowerplaypnl.setVisible(true);
        lowerplaypnl.setBounds(0,370,1005,55);
        localPnl.add(lowerplaypnl,BorderLayout.CENTER);

        JPanel musicPnl = new JPanel();
        musicPnl.setBounds(30,60,940,300);
        musicPnl.setLayout(new BorderLayout());
        musicPnl.setBackground(panelColor);
        localPnl.add(musicPnl);
        int rowsNew = 20;
        Object[][] dataNew = {
                {1,"Pal","4:55"},
                {2,"Upahar","4:50"},
                {3,"Chinta","4:20"}

        };
        String[] column = {"S.N.","Title","Duration"};
        DefaultTableModel models = new DefaultTableModel(dataNew,column);
        JTable songsTbl = new JTable(models);
        songsTbl.setBackground(panelColor);
        songsTbl.setForeground(whiteColor);
        songsTbl.setRowHeight(rowsNew);
        JTableHeader tableHeader = songsTbl.getTableHeader();
        tableHeader.setBackground(panelColor);
        tableHeader.setForeground(whiteColor);

        JScrollPane localScroll= new JScrollPane(songsTbl);
        localScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        localScroll.setVisible(true);
        // scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        localScroll.getViewport().setBackground(panelColor);

        musicPnl.add(localScroll,BorderLayout.CENTER);

        JPanel localimg = new JPanel(){
            protected void paintComponent(Graphics g){
                Image img = new ImageIcon(this.getClass().getResource("/Images/local.png")).getImage();
                g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        localimg.setBounds(65,400,18,18);
        container.add(localimg);

        JLabel local = new JLabel("Local");
        local.setForeground(whiteColor);
        local.setBounds(100,395,150,30);
        local.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(local);
        local.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               localDesign();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                local.setText("<html><u><font color='#0EF6CC'>Local</font></u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                local.setText("<html><font color='#F4FEFD'>Local</font></html>");
            }
        });
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
        menuLbl.setBounds(40,80,150,30);
        menuLbl.setFont(new Font("Tahoma",Font.PLAIN,25));
        container.add(menuLbl);

        JPanel exploreimg = new JPanel(){
          protected void paintComponent(Graphics g){
              Image img = new ImageIcon(this.getClass().getResource("/Images/explore.png")).getImage();
              g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
          }
        };
        exploreimg.setBounds(65,130,18,18);
        container.add(exploreimg);

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

        JPanel genreimg = new JPanel(){
            protected void paintComponent(Graphics g){
                Image img = new ImageIcon(this.getClass().getResource("/Images/genre.png")).getImage();
                g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        genreimg.setBounds(65,170,18,18);
        container.add(genreimg);

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

        JPanel albumsimg = new JPanel(){
            protected void paintComponent(Graphics g){
                Image img = new ImageIcon(this.getClass().getResource("/Images/albums.png")).getImage();
                g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        albumsimg.setBounds(65,205,18,18);
        container.add(albumsimg);

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

        JPanel artistsimg = new JPanel(){
            protected void paintComponent(Graphics g){
                Image img = new ImageIcon(this.getClass().getResource("/Images/artists.png")).getImage();
                g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        artistsimg.setBounds(65,240,18,18);
        container.add(artistsimg);

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
        library.setBounds(40,280,150,30);
        library.setFont(new Font("Tahoma",Font.PLAIN,25));
        container.add(library);

        JPanel recentimg = new JPanel(){
            protected void paintComponent(Graphics g){
                Image img = new ImageIcon(this.getClass().getResource("/Images/recent.png")).getImage();
                g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        recentimg.setBounds(65,330,18,18);
        container.add(recentimg);

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

        JPanel favimg = new JPanel(){
            protected void paintComponent(Graphics g){
                Image img = new ImageIcon(this.getClass().getResource("/Images/fav.png")).getImage();
                g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        favimg.setBounds(65,365,18,18);
        container.add(favimg);

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






        //RoundedPanel to create table which display Songs.
        //Table should consist Name, Artist, duration Genre.
        displaySongsPanel.setBackground(panelColor);
        displaySongsPanel.setBounds(265,370,625,420);
        container.add(displaySongsPanel);
        displaySongsPanel.setVisible(false);

//      Table to retrieve songs data form database and store it

//        displaySongsPanel.add(displayData);
        JTableHeader tblHeader = displayData.getTableHeader();
        tblHeader.setBackground(panelColor);
        tblHeader.setForeground(whiteColor);
        scrollPane.setBounds(270,375,615,410);
        scrollPane.getViewport().setBackground(panelColor);

        container.add(scrollPane);
        container.setComponentZOrder(scrollPane,0);
        scrollPane.setVisible(false);


        //Playlist
        JLabel playlist = new JLabel("PLAYLIST");
        playlist.setForeground(userPnlColor);
        playlist.setBounds(40,440,150,30);
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

        RoundedButton createPlaylistBtn = new RoundedButton("Create Playlist",10);
        createPlaylistBtn.setText("Create");
        createPlaylistBtn.setBounds(32,600,190,40);
        createPlaylistBtn.setBorderPainted(false);
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



        JPanel deviceimg = new JPanel(){
            protected void paintComponent(Graphics g){
                Image img = new ImageIcon(this.getClass().getResource("/Images/headphone.png")).getImage();
                g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        deviceimg.setBounds(50,742,25,20);
        container.add(deviceimg);

        JLabel backlbl = new JLabel("playing on device");
        backlbl.setForeground(whiteColor);
        backlbl.setFont(new Font("Tahoma",Font.PLAIN,15));
        backlbl.setBounds(90,737,150,30);
        container.add(backlbl);

        JPanel enjoymusicimg = new JPanel(){
            protected void paintComponent(Graphics g){
                Image img = new ImageIcon(this.getClass().getResource("/Images/AirPods-Pro.jpg")).getImage();
                g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        enjoymusicimg.setBounds(40,665,60,60);
        container.add(enjoymusicimg);

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

        RoundedPanel coverImgPanel = new RoundedPanel(10){
            protected void paintComponent(Graphics g){
                super.paintComponents(g);
                Image image = new ImageIcon(this.getClass().getResource("/Images/R.jpg")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }

        };
        coverImgPanel.setBounds(960,420,250,160);
        container.add(coverImgPanel);


        JLabel songName = new JLabel("Bhanai");
        songName.setFont(new Font("Tahoma",Font.BOLD,35));
        songName.setForeground(whiteColor);
        songName.setBounds(1020,590,300,40);
        container.add(songName);

        JLabel singerName = new JLabel("The Tribal Rain");
        singerName.setFont(new Font("Tahoma",Font.PLAIN,12));
        singerName.setForeground(whiteColor);
        singerName.setBounds(1040,630,300,12);
        container.add(singerName);

        JSlider slider = new JSlider(JSlider.HORIZONTAL,0,100,50);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                int value = source.getValue();
//                System.out.println("Slider Value" + value);
            }
        });
        slider.setBackground(panelColor);
        slider.setBorder(null);
        //UIManager.put("Slider.thumb",new EllipseThumb());
        slider.setBounds(960,660,250,15);
        container.add(slider);

        Font text = new Font("Tahoma",Font.PLAIN,12);
        JLabel startTime = new JLabel();
        startTime.setText("00:00");
        startTime.setFont(text);
        startTime.setForeground(whiteColor);
        startTime.setBounds(915,660,50,12);
        container.add(startTime);

        JLabel stopTime = new JLabel();
        stopTime.setText("00:00");
        stopTime.setFont(text);
        stopTime.setForeground(whiteColor);
        stopTime.setBounds( 1220,660,50,12);
        container.add(stopTime);



        //play Button
        JPanel playBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/playButton.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }

        };
        container.setLayout(null);
        playBtn.setBounds(1070,727,35,35);
        container.add(playBtn);
        playBtn.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
//                JPanel pauseBtn = new JPanel(){
//                    protected void paintComponent(Graphics g2){
//                        Image img = new ImageIcon(this.getClass().getResource("/Images/pause.png")).getImage();
//                        g2.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
//                    }
//                };
//                container.setLayout(null);
//                pauseBtn.setBounds(1070,727,35,35);
//                container.add(pauseBtn);

//                This is for testing of the play button
//                JOptionPane.showMessageDialog(container, "This is a message dialog!", "Message", JOptionPane.INFORMATION_MESSAGE);

            if(!isMusicPlaying){
                musicHandler.playSongAsync();
            }

            }
        });

        //Next Button
        JPanel nextBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/next.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };

        nextBtn.setBounds(1165,737,20,20);
        container.add(nextBtn);

        //Shuffle Button
        JPanel shuffleBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/shuffle.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        container.setLayout(null);
        shuffleBtn.setBounds(1220,737,20,20);
        container.add(shuffleBtn);

        //Previous Button
        JPanel prevBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/previous.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };

        prevBtn.setBounds(985,737,20,20);
        container.add(prevBtn);

        //Loop Button
        JPanel loopBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/loop.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        container.setLayout(null);
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
                if(isDisplayPanelVisible){
                    System.out.println("Calling getSongFunction");
                    musicHandler.getSongDataAsync(songName);
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

        JLabel userlabel = new JLabel();
        userlabel.setText("User-Name");
        userlabel.setFont(new Font("Tahoma",Font.PLAIN,20));
        userlabel.setForeground(whiteColor);
        userlabel.setBounds(1065,30,100,20);
        container.add(userlabel);
        //User Name Panel
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(1000,20,200,45);
        RoundedPanel userPnl = new RoundedPanel(10);
        userPnl.setBackground(userPnlColor);
        userPnl.setBounds(0,0,180,45);
        //User Image Panel
        RoundedPanel userimgPnl = new RoundedPanel(10){
            @Override
            protected void paintComponent(Graphics g) {
                Image img = new ImageIcon(this.getClass().getResource("/Images/user.png")).getImage();
                g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
            }

        };
        userimgPnl.setBackground(whiteColor);
        userimgPnl.setBounds(2,2,40,40);
        layeredPane.add(userPnl,Integer.valueOf(1));
        layeredPane.add(userimgPnl,Integer.valueOf(2));
        container.add(layeredPane);

        JPanel exitBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/exit.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }

        };
        container.setLayout(null);

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


        //        Handeling Table...
        displayData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = displayData.getSelectedRow();
                int titleColumnIndex = 1; // Assuming Title column is at index 1
                Object selectedTitle = tableModel.getValueAt(selectedRow, titleColumnIndex);

                if (selectedTitle != null) {

//                    playBtn
                    String title = selectedTitle.toString();
                    System.out.println( "Selected Title : " + title);

//                    if (!musicHandler.isPlaying) {
//                        if (musicHandler.clip == null || musicHandler.clipPosition >= musicHandler.clip.getMicrosecondLength()) {
//                            musicHandler.audioData = musicHandler.fetchDataFromServer();
//                            try {
//                                musicHandler.playMusic(title);
//                            } catch (LineUnavailableException | IOException | InterruptedException |
//                                     UnsupportedAudioFileException ex) {
//                                throw new RuntimeException(ex);
//                            }
//                            isMusicPlaying = true;
//                        }
//                    } else {
//                        isMusicPlaying = false;
//                        musicHandler.pauseMusic();
//                    }
//                    selectedTitleLabel.setText("Selected Title: " + title);
                }
            }
        });







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

    public void localDesign(){
        if(local){
            localPnl.setVisible(false);
            local = false;
        }else{
            localPnl.setVisible(true);
            local = true;
        }

    }







}
