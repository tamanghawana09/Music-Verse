package dev.musicVerse;

import Client.LoginHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Login extends JFrame {
    //LoginHandler Class
    private LoginHandler loginHandler;

    private Design design;

    //Register Class
    private Register register;

    //Colors and Fonts properties
    Color backgroundColor = Color.decode("#00000");
    Color panelColor = Color.decode("#1b2223");
    Color greenColor = Color.decode("#0EF6CC");
    Color whiteColor = Color.decode("#F4FEFD");
    //Color YellowColor = Color.decode("#f9e509");

    Container container = new Container();



    public String uName;
    public String uPassword;

    public boolean isUserValid = false;

    public String valid_uName;

//    private boolean loginInProgress = false;



    public Login(){
        super("Login");

        loginHandler = new LoginHandler(this);


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

        // Create a variable to track whether a login attempt is in progress

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uName = usernameTF.getText();
                uPassword = passwordTF.getText();

//                if (!(uName.isEmpty() || uPassword.isEmpty())) {
//                    // Disable the login button to prevent multiple login attempts
//                    loginBtn.setEnabled(false);
//
//                    SwingWorker<Void, Void> loginWorker = new SwingWorker<>() {
//                        @Override
//                        protected Void doInBackground() {
//                            try {
//                                loginHandler.loginAccountAsync(uName, uPassword);
//
//                                boolean isValid = isUserValid;
//
//                                if (isValid) {
//                                    // This is the time-consuming part - initialize the Design frame
//                                    SwingUtilities.invokeLater(() -> {
//                                        openDesignFrame();
//                                        dispose();
//                                    });
//                                } else {
//                                    JOptionPane.showMessageDialog(null, "Login Failed!! Please try Again!!", "Failure", JOptionPane.ERROR_MESSAGE);
//                                }
//                            } catch (Exception ex) {
//                                throw new RuntimeException(ex);
//                            }
//                            return null;
//                        }
//
//                        @Override
//                        protected void done() {
//                            // Re-enable the login button after the login task is done
//                            loginBtn.setEnabled(true);
//                        }
//                    };
//
//                    loginWorker.execute();
//                } else {
//                    JOptionPane.showMessageDialog(null, "Please Fill all the Fields");
//                }


                uName = usernameTF.getText();
                uPassword = passwordTF.getText();

                if (!(uName.isEmpty() || uPassword.isEmpty())) {
                    // Disable the login button to prevent multiple login attempts
                    loginBtn.setEnabled(false);

                    try {
                        // Perform the login operation directly
                        loginHandler.loginAccountAsync(uName, uPassword);
                        boolean isValid = isUserValid;

                        if (isValid) {
                            // Open the design frame if login is successful
                            openDesignFrame();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Login Failed!! Please try Again!!", "Failure", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        // Re-enable the login button
                        loginBtn.setEnabled(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please Fill all the Fields");
                }

            }
        });




        RoundedButton registrationBtn = new RoundedButton("Register",40);
        registrationBtn.setFont(new Font("Tahoma",Font.PLAIN,20));
        registrationBtn.setForeground(Color.BLACK);
        registrationBtn.setBackground(whiteColor);
        registrationBtn.setBounds(545,440,250,40);
        registrationBtn.setBorderPainted(false);
        container.add(registrationBtn);

        registrationBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                register = new Register();
                register.setVisible(true);

                dispose();
            }
        });

        RoundedPanel roundedPanel = new RoundedPanel(10);
        roundedPanel.setBounds(475,30,390,540);
        roundedPanel.setBackground(panelColor);
        container.add(roundedPanel);

        setLayout(null);
        setContentPane(container);
        setVisible(true);
    }



    public void openDesignFrame() {
        design = new Design();
        design.setVisible(true);
//        dispose(); // Close the login frame
    }


}
