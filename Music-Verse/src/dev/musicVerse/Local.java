package dev.musicVerse;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ImageObserver;

public class Local {
    Container container = new Container();
    ImageIcon playIcon, stopIcon, nextIcon, prevIcon;
    Image playImage, stopImage, nextImage, prevImage;
    public Local(){
        Color panelColor = Color.decode("#1b2223");
        Color greenColor = Color.decode("#0EF6CC");
        Color whiteColor = Color.decode("#F4FEFD");
        Color userPnlColor = Color.decode("#3A4F50");

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

        JPanel playBtn = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Image image = new ImageIcon(this.getClass().getResource("/Images/playButton.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        playBtn.setBounds(495,382,15,15);
        container.add(playBtn);

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
                local.dispose();
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
        String[] columnNames = {"S.N.","Title","Duration"};
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        JTable songsTbl = new JTable(model);
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

        local.setVisible(true);
        local.setContentPane(container);
    }


}
