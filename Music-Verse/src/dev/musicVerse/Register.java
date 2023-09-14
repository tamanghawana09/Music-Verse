package dev.musicVerse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Register extends JFrame {
    //Colors and Fonts properties
    Color backgroundColor = Color.decode("#00000");
    Color panelColor = Color.decode("#1b2223");
    Color greenColor = Color.decode("#0EF6CC");
    Color whiteColor = Color.decode("#F4FEFD");
    //Color YellowColor = Color.decode("#f9e509");

    Container container = new Container();
    public Register(){
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

        JLabel wrongPass = new JLabel();
        wrongPass.setText("<html><i>( The password is unmatched )</i></html>");
        wrongPass.setFont(new Font("Tahoma",Font.PLAIN,13));
        wrongPass.setForeground(Color.RED);
        wrongPass.setBounds(580,415,250,30);
        wrongPass.setVisible(false);
        container.add(wrongPass);

        JLabel registerlbl = new JLabel("Register");
        registerlbl.setFont(new Font("Tahoma", Font.BOLD,70));
        registerlbl.setBounds(520,60,300,100);
        registerlbl.setForeground(whiteColor);
        container.add(registerlbl);

        JLabel fullname = new JLabel("Full Name: ");
        fullname.setFont(new Font("Tahoma",Font.PLAIN,18));
        fullname.setBounds(510,180,200,40);
        fullname.setForeground(whiteColor);
        container.add(fullname);

        JLabel username= new JLabel("User Name: ");
        username.setFont(new Font("Tahoma",Font.PLAIN,18));
        username.setBounds(510,230,200,40);
        username.setForeground(whiteColor);
        container.add(username);

        JLabel email = new JLabel("Email:");
        email.setFont(new Font("Tahoma",Font.PLAIN,18));
        email.setBounds(510,280,200,40);
        email.setForeground(whiteColor);
        container.add(email);

        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Tahoma",Font.PLAIN,18));
        password.setBounds(510,330,200,40);
        password.setForeground(whiteColor);
        container.add(password);

        JLabel repass = new JLabel("Reconfirm Password:");
        repass.setFont(new Font("Tahoma",Font.PLAIN,18));
        repass.setBounds(510,380,200,40);
        repass.setForeground(whiteColor);
        container.add(repass);

        JTextField fullnameTF = new JTextField();
        fullnameTF.setBackground(whiteColor);
        fullnameTF.setBorder(null);
        fullnameTF.setBounds(610,190,200,20);
        container.add(fullnameTF);

        JTextField usernameTF = new JTextField();
        usernameTF.setBackground(whiteColor);
        usernameTF.setBorder(null);
        usernameTF.setBounds(610,240,200,20);
        container.add(usernameTF);

        JTextField emailTF = new JTextField();
        emailTF.setBackground(whiteColor);
        emailTF.setBorder(null);
        emailTF.setBounds(610,290,200,20);
        container.add(emailTF);

        JPasswordField passwordTF = new JPasswordField();
        passwordTF.setBackground(whiteColor);
        passwordTF.setBorder(null);
        passwordTF.setBounds(610,340,200,20);
        passwordTF.setEchoChar('*');
        container.add(passwordTF);

        JPasswordField repassTF = new JPasswordField();
        repassTF.setBackground(whiteColor);
        repassTF.setBorder(null);
        repassTF.setBounds(690,390,120,20);
        repassTF.setEchoChar('*');
        container.add(repassTF);

        JCheckBox showPass = new JCheckBox();
        showPass.setBounds(820,335,30,30);
        showPass.setBackground(panelColor);
        container.add(showPass);
        showPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                passwordTF.setEchoChar(cb.isSelected() ? '\0':'*');
            }
        });

        JCheckBox showrePass = new JCheckBox();
        showrePass.setBounds(820,385,30,30);
        showrePass.setBackground(panelColor);
        container.add(showrePass);
        showrePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                repassTF.setEchoChar(cb.isSelected() ? '\0':'*');
            }
        });

        RoundedButton registrationBtn = new RoundedButton("Register",40);
        registrationBtn.setFont(new Font("Tahoma",Font.PLAIN,20));
        registrationBtn.setForeground(Color.BLACK);
        registrationBtn.setBackground(greenColor);
        registrationBtn.setBounds(545,450,250,40);
        registrationBtn.setBorderPainted(false);
        container.add(registrationBtn);
        registrationBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                char[] passChar = passwordTF.getPassword();
                char[] repassChar = repassTF.getPassword();
                String password = new String(passChar);
                String repass = new String(repassChar);
                if(password.equals(repass)){
                    wrongPass.setVisible(false);
                } else if (password != repass) {
                    wrongPass.setVisible(true);
                }else{
                    // code to move to login form
                }

            }
        });

        RoundedButton loginBtn = new RoundedButton("Login",40);
        loginBtn.setFont(new Font("Tahoma",Font.PLAIN,20));
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setBackground(whiteColor);
        loginBtn.setBounds(545,510,250,40);
        loginBtn.setBorderPainted(false);
        container.add(loginBtn);

        RoundedPanel roundedPanel = new RoundedPanel(10);
        roundedPanel.setBounds(475,30,390,540);
        roundedPanel.setBackground(panelColor);
        container.add(roundedPanel);

        setLayout(null);
        setContentPane(container);
        setVisible(true);
    }

}
