import dev.musicVerse.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Label Click Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1005, 700);

        // Create a JPanel to hold your content
        JPanel contentPane = new JPanel();
        frame.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout()); // Use BorderLayout

        // Create a label
        JLabel label = new JLabel("Click me!");
        contentPane.add(label, BorderLayout.NORTH); // Add label to the top (North) of the contentPane

        // Create the panel you want to show/hide
        RoundedPanel localPnl = new RoundedPanel(10);
        localPnl.setVisible(false);
        localPnl.setBackground(Color.BLACK);
        localPnl.setBounds(100,100,100,100);
        contentPane.add(localPnl, BorderLayout.CENTER);

        // Add a click listener to the label
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // Toggle the visibility of the panel on label click
                localPnl.setVisible(!localPnl.isVisible());


            }
        });

        frame.setVisible(true);
    }
}
