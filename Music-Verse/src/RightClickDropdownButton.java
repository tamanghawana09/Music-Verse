import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RightClickDropdownButton {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Right-Click Dropdown Button Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create a panel
            JPanel panel = new JPanel();
            frame.add(panel);

            // Create a right-click dropdown button
            JButton rightClickButton = new JButton("Right-Click Me");
            panel.add(rightClickButton);

            // Create a popup menu
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem item1 = new JMenuItem("Option 1");
            JMenuItem item2 = new JMenuItem("Option 2");
            JMenuItem item3 = new JMenuItem("Option 3");

            // Add action listeners to menu items
            item1.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, "Option 1 selected");
            });

            item2.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, "Option 2 selected");
            });

            item3.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, "Option 3 selected");
            });

            // Add menu items to the popup menu
            popupMenu.add(item1);
            popupMenu.add(item2);
            popupMenu.add(item3);

            // Attach a mouse listener to the button to show the popup menu on right-click
            rightClickButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        popupMenu.show(rightClickButton, e.getX(), e.getY());
                    }
                }
            });

            frame.pack();
            frame.setVisible(true);
        });
    }
}
