import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class sample extends JFrame {
    private ImageIcon image1;
    private JLabel label1;
    private ImageIcon image2;
    private JLabel label2;

     sample() {
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       Object[][] data ={
               {"Alice", 25},
               {"Bob", 30},
               {"Charlie", 22}
       };
       String[] columnNames = {"S.N","Name","Duration"};
         DefaultTableModel model = new DefaultTableModel(data,columnNames);
         JTable table = new JTable(model);

         table.setBackground(Color.cyan);
         JTableHeader header = table.getTableHeader();
         header.setBackground(Color.cyan);
         JScrollPane scrollPane = new JScrollPane(table);
         add(scrollPane);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            sample obj = new sample();
            obj.setVisible(true);
            obj.pack();
        });
    }
}
