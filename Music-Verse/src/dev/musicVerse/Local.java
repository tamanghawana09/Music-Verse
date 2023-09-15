package dev.musicVerse;



import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        JSlider localSlider = new JSlider(JSlider.HORIZONTAL,0,100,50);
        localSlider.setMajorTickSpacing(10);
        localSlider.setMinorTickSpacing(1);
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
        JPanel shuffleBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/shuff.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        container.setLayout(null);
        shuffleBtn.setBounds(640,382,18,18);
        container.add(shuffleBtn);

        //Previous Button
        JPanel prevBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/prev.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };

        prevBtn.setBounds(400,382,15,15);
        container.add(prevBtn);

        //Loop Button
        JPanel loopBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/lo.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        container.setLayout(null);
        loopBtn.setBounds(330,382,20,20);
        container.add(loopBtn);


        //Next Button
        JPanel nextBtn = new JPanel(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/nex.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };

        nextBtn.setBounds(580,382,15,15);
        nextBtn.setVisible(true);
        container.add(nextBtn);

        JPanel pauseBtn = new JPanel(){
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

            }
        });

        JPanel playBtn = new JPanel(){
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
        Object[][] data = {
                {1,"Pal","4:55"},
                {2,"Upahar","4:50"},
                {3,"Chinta","4:20"}

        };
        model = new DefaultTableModel(new Object[]{"Song Name"},0);
        songsTbl = new JTable(model);
        songsTbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        songsTbl.setBackground(panelColor);
        songsTbl.setForeground(whiteColor);
        songsTbl.setRowHeight(rows);
//        JTableHeader header = songsTbl.getTableHeader();
//        header.setBackground(panelColor);
//        header.setForeground(whiteColor);

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
        int returnValue = fileChooser.showOpenDialog(null);
        if(returnValue == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
//            File[] songFiles = file.listFiles((dir, name) -> name.endsWith(".mp3"));
                uploadMusic(file);
//            if(songFiles != null){
//                model.setRowCount(0);
//                for(File songFile : songFiles){
//                    model.addRow(new Object[]{songFile.getName()});
//                }
            }
        }

    private void uploadMusic(File file){
//        try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:database","root","root")){
//            String insertQuery = "INSERT INTO songs (file_data) VALUES (?)";
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
    }
    public static void main(String[] args) {
        Local lo = new Local();

    }


}
