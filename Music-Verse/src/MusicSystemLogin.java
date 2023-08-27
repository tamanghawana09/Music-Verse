
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MusicSystemLogin extends JFrame implements ActionListener, ItemListener {
    private JTextField usernameField;
    MusicSystemSignup signup = new MusicSystemSignup();
    private JPasswordField passwordField;
    JPanel panel = new JPanel();
    private JCheckBox showPasswordCheckbox; // Checkbox to show/hide password

    public MusicSystemLogin() {
        setSize(600, 400);
        setTitle("Music System Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.addItemListener(this);

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Signup");
        signupButton.addActionListener(this);
        loginButton.setPreferredSize(new Dimension(80, 30));
        signupButton.setPreferredSize(new Dimension(80, 30));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (isValidLogin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
                }
                if(e.getSource()==signupButton){
                    switchToSignupPanel();

                }
            }
        });

        signupButton.addActionListener(this);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(usernameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(passwordField, constraints);


        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(showPasswordCheckbox, constraints);


        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(loginButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(signupButton, constraints);



        add(panel);
        setVisible(true);
    }

    private boolean isValidLogin(String username, String password) {
        // Replace this with your actual login validation logic
        return username.equals("user") && password.equals("password");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MusicSystemSignup musicSystemSignup = new MusicSystemSignup();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == showPasswordCheckbox) {
            int state = e.getStateChange();
            if (state == ItemEvent.SELECTED) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('\u25CF');
            }
        }
    }
    private void switchToSignupPanel() {
        setVisible(false); // Hide the current login panel
        MusicSystemSignup musicSystemSignup = new MusicSystemSignup();
        musicSystemSignup.setVisible(true); // Show the signup panel
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MusicSystemLogin();
        });
    }
}