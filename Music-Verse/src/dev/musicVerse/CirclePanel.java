package dev.musicVerse;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CirclePanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int panelWidth = getWidth();
        int panelHeight = getHeight();


        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;
        int radius = Math.min(panelWidth, panelHeight) / 2 - 10;

        // Create an Ellipse2D object representing the circle
        Ellipse2D.Double circle = new Ellipse2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius);


        g2d.setColor(Color.WHITE);


        g2d.fill(circle);
    }

}
