package dev.musicVerse;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Logout extends JFrame {
    Container container = new Container();
    public Logout(){
        Color backgroundColor = Color.decode("#00000");
        Color pnlColor = Color.decode("#2B2B2B");
        Color greenColor = Color.decode("#0EF6CC");
        Color borderColor = Color.decode("#0EF6CC");
        int borderWidth = 2;
        Border customBorder = new LineBorder(borderColor,borderWidth);

        setUndecorated(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),20,20));
            }
        });
        setSize(680,545);
        setBackground(backgroundColor);
        setLocation(450,100);

        RoundedButton logBtn=  new RoundedButton("Naah, Just Kidding !");
        logBtn.setBackground(pnlColor);
        logBtn.setBorder(customBorder);
        logBtn.setBounds(185,320,320,50);
        container.add(logBtn);

        RoundedButton logBtn2=  new RoundedButton("Yes, Log me out !");
        logBtn2.setBackground(greenColor);
        logBtn2.setBounds(185,390,320,50);
        container.add(logBtn2);

        //logout panel
        RoundedPanel pnl = new RoundedPanel(20);
        pnl.setBackground(pnlColor);
        pnl.setSize(520,420);
        pnl.setLocation(85,65);
        container.add(pnl);

        //logout image
//        JPanel imgpnl = new JPanel(){
//            protected void paintComponent(Graphics imgG){
//                Image image = new ImageIcon(this.getClass().getResource("/Images/logout.png")).getImage();
//                imgG.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
//            }
//
//        };
//        imgpnl.setBounds(0,0,400,300);
//        pnl.add(imgpnl);

        // exit button
        JButton button = new JButton(){
            protected void paintComponent(Graphics g){
                Image image = new ImageIcon(this.getClass().getResource("/Images/exit.png")).getImage();
                g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
            }

        };
        button.setBounds(660,5,15,15);
        button.setBorderPainted(false);
        container.add(button);
        button.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                if(event.getSource() == button){
                    System.exit(0);
                }
            }
        });



        setContentPane(container);
        setVisible(true);
        setLayout(null);
    }

}

