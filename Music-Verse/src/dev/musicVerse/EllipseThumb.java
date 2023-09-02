import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class EllipseThumb {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> EllipseThumb());
    }

    private static void EllipseThumb() {
        // Set a custom look and feel to customize the slider thumb appearance
        UIManager.put("Slider.thumb", new ellipseThumb());

        JFrame frame = new JFrame("Circular Seekbar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setLayout(new BorderLayout());

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        JLabel valueLabel = new JLabel("Value: " + slider.getValue());
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                valueLabel.setText("Value: " + source.getValue());

                // Additional actions based on the slider value can be added here
            }
        });

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BorderLayout());
        sliderPanel.add(slider, BorderLayout.CENTER);
        sliderPanel.add(valueLabel, BorderLayout.SOUTH);

        frame.add(sliderPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}

// Custom thumb class to render a circular thumb
class ellipseThumb implements Icon {
    private static final int THUMB_SIZE = 20;

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.GRAY);
        g2.fill(new Ellipse2D.Double(x, y, THUMB_SIZE, THUMB_SIZE));
    }

    @Override
    public int getIconWidth() {
        return THUMB_SIZE;
    }

    @Override
    public int getIconHeight() {
        return THUMB_SIZE;
    }
}
