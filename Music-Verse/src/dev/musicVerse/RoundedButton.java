package dev.musicVerse;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
    private int radius;


    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setFocusPainted(false); // Remove default focus rectangle



        setContentAreaFilled(false); // Make the background transparent
        setOpaque(false); // Make the button itself transparent

        // Set preferred size for the button
        setPreferredSize(new Dimension(100, 40));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a rounded rectangle shape for the button
        Shape shape = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        if (getModel().isArmed()) {
            g2.setColor(Color.lightGray);
        } else {
            g2.setColor(getBackground());
        }

        g2.fill(shape);

        drawWrappedText(g2);


    }
    private void drawWrappedText(Graphics2D g2) {
        String text = getText();
        Font font = getFont();
        FontMetrics fontMetrics = g2.getFontMetrics(font);
        int lineHeight = fontMetrics.getHeight();
        int y = (getHeight() - lineHeight * getLines(text,fontMetrics,getWidth() - 20).length) / 2 + fontMetrics.getAscent();

        for (String line : getLines(text, fontMetrics, getWidth() - 20)) {
            int textWidth = fontMetrics.stringWidth(line);
            int x = (getWidth() - textWidth) / 2;
            g2.setColor(getForeground());
            g2.drawString(line, x, y);
            y += lineHeight;
        }
    }
    private String[] getLines(String text, FontMetrics fontMetrics, int maxWidth) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        java.util.List<String> lines = new java.util.ArrayList<>();

        for (String word : words) {
            if (fontMetrics.stringWidth(line + " " + word) <= maxWidth) {
                line.append(" ").append(word);
            } else {
                lines.add(line.toString().trim());
                line.setLength(0);
                line.append(word);
            }
        }

        lines.add(line.toString().trim());
        return lines.toArray(new String[0]);
    }
}


