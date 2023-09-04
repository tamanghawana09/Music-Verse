import javax.swing.*;
import javax.swing.event.*;

public class test {
    public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("Java Seek Bar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 100);

        // Create a JProgressBar (Seek Bar)
        JProgressBar seekBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        seekBar.setValue(50); // Initial value
        seekBar.setStringPainted(true); // Display the value
        seekBar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JProgressBar source = (JProgressBar) e.getSource();
                int value = source.getValue();
                System.out.println("Seek bar value: " + value);
            }
        });

        // Add the seek bar to the JFrame
        frame.add(seekBar);

        // Set JFrame properties and make it visible
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }
}
