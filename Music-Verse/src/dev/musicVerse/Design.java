package dev.musicVerse;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Design extends JFrame{
    private static  final Container container = new Container();
    private static final JPanel panel = new JPanel();
    private Point mousePressLocation;
    public Design(){
        //Colors properties
        Color backgroundColor = Color.decode("#00000");
        Color panelColor = Color.decode("#1b2223");

        //frame properties
        setUndecorated(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),50,50));
            }
        });

        setSize(1280,780);
        setBackground(backgroundColor);
        setLocation(120,20);

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



        panel.setBounds(0,0,280,800);
        panel.setBackground(panelColor);
        container.add(panel);

        setLayout(null);
        setContentPane(container);
    }

}
