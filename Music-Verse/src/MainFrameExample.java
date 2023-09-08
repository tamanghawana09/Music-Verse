import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameExample extends JFrame {
    public MainFrameExample() {
        setTitle("Main Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JButton openDialogButton = new JButton("Open Dialog");
        openDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openChildDialog();
            }
        });

        add(openDialogButton, BorderLayout.CENTER);
    }

    private void openChildDialog() {
        JDialog childDialog = new JDialog(this, "Child Dialog", true);
        childDialog.setSize(200, 150);


        JButton closeButton = new JButton("Close Dialog");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                childDialog.dispose(); // Close the dialog
            }
        });

        childDialog.add(new JLabel("This is a child dialog"), BorderLayout.CENTER);
        childDialog.add(closeButton, BorderLayout.SOUTH);

        childDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrameExample mainFrame = new MainFrameExample();
                mainFrame.setVisible(true);
            }
        });
    }
}
