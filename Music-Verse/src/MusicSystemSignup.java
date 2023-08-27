import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusicSystemSignup extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField reconfirmPasswordField;
    private JTextField emailField;
    private JTextField fullNameField;

    public MusicSystemSignup() {
        setTitle("Music System Signup");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel reconfirmPasswordLabel = new JLabel("Reconfirm Password:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel fullNameLabel = new JLabel("Full Name:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        reconfirmPasswordField = new JPasswordField();
        emailField = new JTextField();
        fullNameField = new JTextField();

        JButton signupButton = new JButton("Signup");
        signupButton.setPreferredSize(new Dimension(80, 30));
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                char[] reconfirmPasswordChars = reconfirmPasswordField.getPassword();
                String reconfirmPassword = new String(reconfirmPasswordChars);
                String email = emailField.getText();
                String fullName = fullNameField.getText();

                if (isValidSignup(username, password, reconfirmPassword, email, fullName)) {
                    JOptionPane.showMessageDialog(null, "Signup successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Signup failed. Please check your information.");
                }
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(fullNameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(fullNameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(usernameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(emailLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(emailField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(reconfirmPasswordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(reconfirmPasswordField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        panel.add(signupButton, constraints);

        add(panel);
        setVisible(true);
    }

    private boolean isValidSignup(String username, String password, String reconfirmPassword, String email, String fullName) {
        // Replace this with your actual signup validation logic
        return !username.isEmpty() && !password.isEmpty() && !reconfirmPassword.isEmpty() && password.equals(reconfirmPassword) && !email.isEmpty() && !fullName.isEmpty();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MusicSystemSignup().setVisible(true);
        });
    }
}
