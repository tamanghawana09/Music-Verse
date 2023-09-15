package dev.musicVerse;



import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Local {
    Container container = new Container();
    ImageIcon playIcon, stopIcon, nextIcon, prevIcon;
    Image playImage, stopImage, nextImage, prevImage;
    private JTable songsTbl;
    private DefaultTableModel model;
    private JFrame frame;

    FilenameFilter filter;
    int playstatus=0, filepathresponse,trackNo=0;
    File[] selectedFile;

   private JSlider localSlider;
    private AdvancedPlayer player;
    private Thread playerThread;
    private File selectedDirectory;
    private JPanel playBtn, pauseBtn, nextBtn, prevBtn,shuffleBtn,loopBtn;
    private int currentSongIndex = -1;




    public Local(){
        Color panelColor = Color.decode("#1b2223");
        Color greenColor = Color.decode("#0EF6CC");
        Color whiteColor = Color.decode("#F4FEFD");


       JDialog local = new JDialog();
       local.setBounds(390,360,1005,445);
       local.setBackground(Color.BLACK);
       local.setUndecorated(true);
       local.setLayout(null);


        // Local player components
        localSlider = new JSlider(JSlider.HORIZONTAL,0,100,50);
        localSlider.setMinimum(0);
        localSlider.setMaximum(100);
        localSlider.setValue(0);
        localSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source =(JSlider) e.getSource();

            }
        });
        localSlider.setBackground(panelColor);
        localSlider.setBorder(null);
        localSlider.setVisible(true);
        localSlider.setBounds(200,415,600,15);
        container.add(localSlider);

        JLabel start = new JLabel();
        start.setText("0:00");
        start.setForeground(whiteColor);
        start.setVisible(true);
        start.setBounds(170,415,30,10);
        container.add(start);

        JLabel stop = new JLabel();
        stop.setForeground(whiteColor);
        stop.setText("0:00");
        stop.setVisible(true);
        stop.setBounds(810,415,30,10);
        container.add(stop);
        JPanel exitBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/exit.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }

        };
        container.setLayout(null);
        exitBtn.setBounds(985,5,15,15);
        container.add(exitBtn);
        exitBtn.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                local.dispose();
            }
        });

        //Shuffle Button
        shuffleBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/shuff.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        container.setLayout(null);
        shuffleBtn.setBounds(640,382,18,18);
        container.add(shuffleBtn);

        //Previous Button
        prevBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/prev.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };

        prevBtn.setBounds(400,382,15,15);
        container.add(prevBtn);
        prevBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                stopPlayer();
//                if(currentSongIndex >0){
//                    currentSongIndex--;
//                    File selectedSong = new File(selectedDirectory,(String) model.getValueAt(currentSongIndex,0));
//                    playSong(selectedSong);
//                }
            }
        });

        //Loop Button
        loopBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/lo.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        container.setLayout(null);
        loopBtn.setBounds(330,382,20,20);
        container.add(loopBtn);


        //Next Button
        nextBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/nex.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };

        nextBtn.setBounds(580,382,15,15);
        nextBtn.setVisible(true);
        container.add(nextBtn);
        nextBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                stopPlayer();
//                if(currentSongIndex>=0 && currentSongIndex < model.getRowCount() -1){
//                    currentSongIndex++;
//                    File selectedSong = new File(selectedDirectory,(String) model.getValueAt(currentSongIndex,0));
//                    playSong(selectedSong);
//                }
            }
        });

        pauseBtn = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Image image = new ImageIcon(this.getClass().getResource("/Images/pause.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }

        };
        pauseBtn.setBounds(495,382,15,15);
        pauseBtn.setVisible(false);
        container.add(pauseBtn);
        pauseBtn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                pauseBtn.setVisible(false);
                playBtn.setVisible(true);
                if(player != null) {
                    if (playerThread.isAlive()) {
                        player.close();
                    }
                }
            }
        });

       playBtn = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Image image = new ImageIcon(this.getClass().getResource("/Images/playButton.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        playBtn.setBounds(495,382,15,15);
        container.add(playBtn);
        playBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playBtn.setVisible(false);
                pauseBtn.setVisible(true);
            }
        });


        RoundedPanel playPnl = new RoundedPanel(10);
        playPnl.setBounds(475,370,50,40);
        playPnl.setBackground(Color.BLACK);
        playPnl.setLayout(null);
        container.add(playPnl);

        JLabel localsongslbl = new JLabel("Local songs");
        localsongslbl.setFont(new Font("Tahoma",Font.BOLD,20));
        localsongslbl.setBounds(10,5,150,40);
        localsongslbl.setForeground(Color.WHITE);
        localsongslbl.setVisible(true);
        container.add(localsongslbl);

        RoundedButton addbtn = new RoundedButton("Add",30);
        addbtn.setBounds(870,15,100,30);
        addbtn.setFont(new Font("Tahoma",Font.PLAIN,15));
        addbtn.setBackground(greenColor);
        addbtn.setBorderPainted(false);
        addbtn.setForeground(Color.BLACK);
        container.add(addbtn);
        addbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    playMusic();
            }
        });

        RoundedPanel lowerplaypnl = new RoundedPanel(10);
        lowerplaypnl.setBackground(panelColor);
        lowerplaypnl.setVisible(true);
        lowerplaypnl.setBounds(5,362,995,77);
        container.add(lowerplaypnl,BorderLayout.CENTER);


        JPanel musicPnl = new JPanel();
        musicPnl.setBounds(30,50,940,300);
        musicPnl.setLayout(new BorderLayout());
        musicPnl.setBackground(panelColor);
        container.add(musicPnl);

        int rows = 20;
        model = new DefaultTableModel(new Object[]{"Song Name"},0);
        songsTbl = new JTable(model);
        songsTbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        songsTbl.setBackground(panelColor);
        songsTbl.setForeground(whiteColor);
        songsTbl.setRowHeight(rows);
        JTableHeader header = songsTbl.getTableHeader();
        header.setBackground(panelColor);
        header.setForeground(whiteColor);

        JScrollPane scrollPane = new JScrollPane(songsTbl);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       // scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(panelColor);
        scrollPane.setBorder(null);
        musicPnl.add(scrollPane,BorderLayout.CENTER);


        local.setContentPane(container);
        local.setVisible(true);
    }
    public void playMusic(){

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            filepathresponse = fileChooser.showOpenDialog(null);
            if(filepathresponse == JFileChooser.APPROVE_OPTION){
                selectedDirectory = fileChooser.getSelectedFile();
                File[] songsFiles = selectedDirectory.listFiles(((dir, name) -> name.endsWith(".mp3")));
                if(songsFiles != null){
                    model.setRowCount(0);
                    for(File songFile: songsFiles){
                        model.addRow(new Object[]{songFile.getName()});
                    }
                }
//                uploadMusic(file);
            }
            songsTbl.getSelectionModel().addListSelectionListener(e->{
                if(!e.getValueIsAdjusting()){
                    int selectedRow = songsTbl.getSelectedRow();
                    if(selectedRow>=0){
                        File selectedSong = new File(selectedDirectory,(String) model.getValueAt(selectedRow,0));
                        playBtn.setVisible(false);
                        pauseBtn.setVisible(true);
                        playSong(selectedSong);
                    }
                }
            });



    }

//    private void uploadMusic(File file){
//        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/music","root","0911")){
//            String insertQuery = "INSERT INTO music_list VALUES (?)";
//            try(PreparedStatement preparedStatement = con.prepareStatement(insertQuery)){
//                FileInputStream fis = new FileInputStream(file);
//                preparedStatement.setBinaryStream(1, fis,(int) file.length());
//                preparedStatement.executeUpdate();
//                JOptionPane.showMessageDialog(frame,"File uploaded to database");
//            }catch (SQLException | IOException e){
//                e.printStackTrace();
//            }
//            }catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    private void playSong(File songFile){
        try {
            FileInputStream fileInputStream = new FileInputStream(songFile);
            Bitstream bitstream = new Bitstream(fileInputStream);

            player = new AdvancedPlayer(fileInputStream);

            // Start playing the song in a new thread
            playerThread = new Thread(() -> {
                try {
                    int totalFrames = bitstream.readFrame().max_number_of_frames(0);
                    player.play();
                    for(int i=0;i<totalFrames;i++){
                        int progress =(int)(((float)i/totalFrames)*100);
                        localSlider.setValue(progress);
                       bitstream.readFrame();
                    }
                    if(currentSongIndex < model.getRowCount() - 1){
                        currentSongIndex++;
                        File nextSong = new File(selectedDirectory,(String)model.getValueAt(currentSongIndex,0));
                        playSong(nextSong);
                    }
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            });
            playerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    public void stopPlayer(){
        if(playerThread.isAlive()){
            player.close();
        }
    }

    public static void main(String[] args) {
        Local lo = new Local();

    }


}
