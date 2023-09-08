import dev.musicVerse.RoundedPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Label Click Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1005, 700);

        JSlider slider = new JSlider(JSlider.HORIZONTAL,0,100,50);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                int value = source.getValue();
//                System.out.println("Slider Value" + value);
            }
        });
/*        slider.setBackground(panelColor);
        slider.setBorder(null);
        //UIManager.put("Slider.thumb",new EllipseThumb());
        slider.setBounds(960,660,250,15);
        container.add(slider);*/

        frame.setVisible(true);
    }
}
