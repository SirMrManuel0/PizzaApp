package de.sirmrmanuel0.gui.custom_components;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, height, height);

        g2d.setColor(getBackground());
        g2d.fill(roundedRectangle);

        g2d.setColor(getForeground());
        g2d.draw(roundedRectangle);

        g2d.dispose();

        super.paintComponent(g);
    }
}