package dev.musicVerse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Local extends JFrame {
    Container container = new Container();
    public Local(){
        Color panelColor = Color.decode("#1b2223");
        Color greenColor = Color.decode("#0EF6CC");
        Color whiteColor = Color.decode("#F4FEFD");
        //Color YellowColor = Color.decode("#f9e509");
        Color userPnlColor = Color.decode("#3A4F50");

        setBounds(390,375,1005,425);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        setUndecorated(true);

        // Local player components
        JLabel localsongslbl = new JLabel("Local songs");
        localsongslbl.setFont(new Font("Tahoma",Font.BOLD,20));
        localsongslbl.setBounds(10,10,150,40);
        localsongslbl.setForeground(Color.WHITE);
        localsongslbl.setVisible(true);
        container.add(localsongslbl);

        RoundedButton addbtn = new RoundedButton("Add",30);
        addbtn.setBounds(880,10,100,30);
        addbtn.setFont(new Font("Tahoma",Font.PLAIN,15));
        addbtn.setBackground(greenColor);
        addbtn.setBorderPainted(false);
        addbtn.setForeground(Color.BLACK);
        container.add(addbtn);

        RoundedPanel lowerplaypnl = new RoundedPanel(10);
        lowerplaypnl.setBackground(panelColor);
        lowerplaypnl.setVisible(true);
        lowerplaypnl.setBounds(5,370,1000,50);
        container.add(lowerplaypnl,BorderLayout.CENTER);


        JPanel musicPnl = new JPanel();
        musicPnl.setBounds(30,60,940,300);
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
        scrollPane.getViewport().setBackground(Color.BLUE);
        scrollPane.setBorder(null);
        musicPnl.add(scrollPane,BorderLayout.CENTER);

        setVisible(true);
        setContentPane(container);
    }


}
