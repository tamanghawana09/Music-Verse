package dev.musicVerse;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Design extends JFrame{
    private static  final Container container = new Container();
    private static final JPanel panel = new JPanel();
    private Point mousePressLocation;
    JPanel imgpnl = new JPanel(){
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Image image = new ImageIcon(this.getClass().getResource("Images/edge.jpg")).getImage();
            g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
        }
    };
    public Design(){
        //Colors properties
        Color backgroundColor = Color.decode("#00000");
        Color panelColor = Color.decode("#1b2223");

        //frame properties
        setUndecorated(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),20,20));
            }
        });

        setSize(1280,780);
        setBackground(backgroundColor);
        setLocation(120,20);

        imgpnl.setBackground(Color.WHITE);
        imgpnl.setBorder(new EmptyBorder(5,5,5,5));
        imgpnl.setLayout(null);
        container.add(imgpnl);


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

        //left panel
        panel.setBounds(0,0,280,800);
        panel.setBackground(panelColor);
        container.add(panel);

        //Image
//        imgpnl.setBackground(Color.WHITE);
//        imgpnl.setBorder(new EmptyBorder(5,5,5,5));
//        imgpnl.setLayout(null);
//        container.add(imgpnl);

        setLayout(null);
        setContentPane(container);
    }

}
