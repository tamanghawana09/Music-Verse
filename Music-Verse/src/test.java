import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class test {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
           JFrame frame = new JFrame("SeekBar Example") ;
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           JSlider slider = new JSlider(JSlider.HORIZONTAL,0,100,50);
           slider.setMajorTickSpacing(10);
           slider.setMinorTickSpacing(1);
           slider.setPaintTicks(false);
           slider.setPaintLabels(false);

           UIDefaults uiDefaults = UIManager.getLookAndFeelDefaults();
           uiDefaults.put("Slider.trackForeground",Color.BLACK);
           slider.addChangeListener(new ChangeListener() {
               @Override
               public void stateChanged(ChangeEvent e) {
                   JSlider source =(JSlider) e.getSource();
                   int value = source.getValue();
                   System.out.println("Slider value:" + value);
               }
           });

           frame.add(slider);
           frame.pack();
           frame.setVisible(true);
        });
    }
}
