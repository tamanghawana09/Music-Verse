package dev.musicVerse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Login extends JFrame {
    //Colors and Fonts properties
    Color backgroundColor = Color.decode("#00000");
    Color panelColor = Color.decode("#1b2223");
    Color greenColor = Color.decode("#0EF6CC");
    Color whiteColor = Color.decode("#F4FEFD");
    //Color YellowColor = Color.decode("#f9e509");

    Container container = new Container();
    public Login(){
        setBounds(300,150,900,600);
        setBackground(backgroundColor);
        setUndecorated(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),20,20));
            }
        });
        JPanel imgPnl = new JPanel(){
            protected void paintComponent(Graphics g){
                Image img = new ImageIcon(this.getClass().getResource("/Images/pnlImg.png")).getImage();
                g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
            }
        };
        imgPnl.setBounds(0,0,475,600);
        imgPnl.setVisible(true);
        container.add(imgPnl);

        JPanel exitPnl = new JPanel(){
          protected void paintComponent(Graphics g){
              Image img = new ImageIcon(this.getClass().getResource("/Images/exit.png")).getImage();
              g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
          }
        };
        exitPnl.setBounds(875,5,20,20);
        container.add(exitPnl);
        exitPnl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        JLabel loginlbl = new JLabel("Login");
        loginlbl.setFont(new Font("Tahoma", Font.BOLD,70));
        loginlbl.setBounds(570,80,200,100);
        loginlbl.setForeground(whiteColor);
        container.add(loginlbl);

        JLabel username = new JLabel("User name:");
        username.setFont(new Font("Tahoma",Font.PLAIN,18));
        username.setBounds(510,250,200,40);
        username.setForeground(whiteColor);
        container.add(username);

        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Tahoma",Font.PLAIN,18));
        password.setBounds(510,300,200,40);
        password.setForeground(whiteColor);
        container.add(password);

        JTextField usernameTF = new JTextField();
        usernameTF.setBackground(whiteColor);
        usernameTF.setBorder(null);
        usernameTF.setBounds(610,260,200,20);
        container.add(usernameTF);


        JPasswordField passwordTF = new JPasswordField();
        passwordTF.setBackground(whiteColor);
        passwordTF.setBorder(null);
        passwordTF.setBounds(610,310,200,20);
        passwordTF.setEchoChar('*');
        container.add(passwordTF);


        JCheckBox showPass = new JCheckBox();
        showPass.setBounds(820,305,30,30);
        showPass.setBackground(panelColor);
        container.add(showPass);
        showPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                passwordTF.setEchoChar(cb.isSelected() ? '\0':'*');
            }
        });

        RoundedButton loginBtn = new RoundedButton("Login",40);
        loginBtn.setFont(new Font("Tahoma",Font.PLAIN,20));
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setBackground(greenColor);
        loginBtn.setBounds(545,380,250,40);
        loginBtn.setBorderPainted(false);
        container.add(loginBtn);

        RoundedButton registrationBtn = new RoundedButton("Register",40);
        registrationBtn.setFont(new Font("Tahoma",Font.PLAIN,20));
        registrationBtn.setForeground(Color.BLACK);
        registrationBtn.setBackground(whiteColor);
        registrationBtn.setBounds(545,440,250,40);
        registrationBtn.setBorderPainted(false);
        container.add(registrationBtn);

        RoundedPanel roundedPanel = new RoundedPanel(10);
        roundedPanel.setBounds(475,30,390,540);
        roundedPanel.setBackground(panelColor);
        container.add(roundedPanel);

        setLayout(null);
        setContentPane(container);
        setVisible(true);
    }

}
