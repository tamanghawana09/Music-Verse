package dev.musicVerse;

import javax.swing.*;
import javax.swing.text.FlowView;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Design extends JFrame {
    private static  final Container container = new Container();
    private static final JPanel panel = new JPanel();
    private static final RoundedPanel playListPanel = new RoundedPanel(10);


    private JPanel createPlaylistPanel;
    private JTextField playlistNameField;
    private JButton addPlaylistButton;
    private boolean isCreatePanelVisible = false;



    private boolean isPanelVisible = false;

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

        JLabel genres = new JLabel("Genres");
        genres.setForeground(whiteColor);
        genres.setBounds(100,165,150,30);
        genres.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(genres);

        JLabel albums = new JLabel("Albums");
        albums.setForeground(whiteColor);
        albums.setBounds(100,200,150,30);
        albums.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(albums);

        JLabel artists = new JLabel("Artists");
        artists.setForeground(whiteColor);
        artists.setBounds(100,235,150,30);
        artists.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(artists);

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

        JLabel fav = new JLabel("Favorites");
        fav.setForeground(whiteColor);
        fav.setBounds(100,360,150,30);
        fav.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(fav);

        JLabel local = new JLabel("Local");
        local.setForeground(whiteColor);
        local.setBounds(100,395,150,30);
        local.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(local);



        //Playlist
        JLabel playlist = new JLabel("PLAYLIST");
        playlist.setForeground(userPnlColor);
        playlist.setBounds(50,440,150,30);
        playlist.setFont(new Font("Tahoma",Font.PLAIN,25));
        container.add(playlist);

        JLabel newplaylist = new JLabel("New PlayList");
        newplaylist.setForeground(whiteColor);
        newplaylist.setBounds(100,475,150,30);
        newplaylist.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(newplaylist);


        newplaylist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(isPanelVisible){
                    playListPanel.setVisible(false);
                    isPanelVisible = false;
                }else{
                    playListPanel.setVisible(true);
                    isPanelVisible = true;
                }
            }
        });

        playListPanel.setBounds(265,370,625,420);
        playListPanel.setBackground(panelColor);
        container.add(playListPanel);
        playListPanel.setVisible(false);

//        playlistModel = new DefaultListModel<>();
//        CreatePlayList = new JList<>(playlistModel);
////        CreatePlayList.setForeground(whiteColor);
//        CreatePlayList.setBounds(100,510,150,30);
////        JScrollPane playlistScrollPane = new JScrollPane(playlist);
//
//        container.add(CreatePlayList);


        JLabel createplaylist = new JLabel("Create PlayList");
        createplaylist.setForeground(whiteColor);
        createplaylist.setBounds(100,510,150,30);
        createplaylist.setFont(new Font("Tahoma",Font.PLAIN,18));
        container.add(createplaylist);


        JLabel createNewplaylist = new JLabel("Create PlayList");
        createNewplaylist.setForeground(whiteColor);
        createNewplaylist.setBounds(100, 510, 150, 30);
        createNewplaylist.setFont(new Font("Tahoma", Font.PLAIN, 18));
        container.add(createNewplaylist);

        // Mouse listener for the Create PlayList label
        createplaylist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isCreatePanelVisible) {
                    createPlaylistPanel.setVisible(true);
                    container.setComponentZOrder(createPlaylistPanel, 0);
                    isCreatePanelVisible = true;
                }else{
                    createPlaylistPanel.setVisible(false);
                    isCreatePanelVisible = false;
                }
            }
        });


        // Create PlayList Panel
        createPlaylistPanel = new JPanel();
        createPlaylistPanel.setBounds(450, 350, 405, 150); // Adjust the height to make it bigger
        createPlaylistPanel.setBackground(Color.red);
        container.add(createPlaylistPanel);
        createPlaylistPanel.setVisible(false);


        // Playlist Name Field
        playlistNameField = new JTextField("Enter Playlist Name");
        playlistNameField.setBounds(10, 10, 400, 40);
        playlistNameField.setForeground(Color.GRAY); // Set initial text color
        createPlaylistPanel.add(playlistNameField);

        playlistNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (playlistNameField.getText().equals("Enter Playlist Name")) {
                    playlistNameField.setText("");
                    playlistNameField.setForeground(Color.BLACK); // Set text color when focused
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (playlistNameField.getText().isEmpty()) {
                    playlistNameField.setText("Enter Playlist Name");
                    playlistNameField.setForeground(Color.GRAY); // Restore placeholder text color
                }
            }
        });



        // Add Playlist Button
        addPlaylistButton = new JButton("Add");
        addPlaylistButton.setBounds(220, 10, 40, 30);
        createPlaylistPanel.add(addPlaylistButton);

        addPlaylistButton.addActionListener(e -> {
            String playlistName = playlistNameField.getText();
            if (!playlistName.isEmpty()) {
                // Add the playlist name to your model or perform the required action
                // For now, we'll just print it to the console

                System.out.println("New playlist: " + playlistName);
                playlistNameField.setText("");
            }
        });





        //music device panel
//        JPanel img = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//
//                ImageIcon imageIcon = new ImageIcon("/Images/music.png");
//                Image image = imageIcon.getImage();
//                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
//
//            }
//        };
//        img.setLayout(null);
//        img.setOpaque(false);
//        img.setBounds(35,670,55,60);
//        container.add(img);

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

        // Top artist panel in dashboard
        RoundedPanel artistPnl = new RoundedPanel(10);
        artistPnl.setBounds(265,370,625,130);
        artistPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
        artistPnl.setBackground(panelColor);

        JLabel artistLbl = new JLabel();
        artistLbl.setText("Top Artists");
        artistLbl.setFont(pnlfont);
        artistLbl.setForeground(whiteColor);
        artistPnl.add(artistLbl);
        container.add(artistPnl);

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

        JLabel chartlbl = new JLabel();
        chartlbl.setText("Top Charts");
        chartlbl.setFont(pnlfont);
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
                musiclbl.setText("<html><u><font color='#0EF6CC'>Music</font></u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                musiclbl.setText("<html><font color='#F4FEFD'>Music</font></html>");
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


        setLocationRelativeTo(null);
        setLayout(null);
        setContentPane(container);
    }

}
