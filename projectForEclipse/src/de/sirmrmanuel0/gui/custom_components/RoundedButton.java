package de.sirmrmanuel0.gui.custom_components;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
    }

    // Überschreiben der paintComponent-Methode, um einen abgerundeten Button zu zeichnen.
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Erstellen eines abgerundeten Rechtecks für den Button.
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, height, height);

        // Füllen des Hintergrunds mit der Hintergrundfarbe des Buttons.
        g2d.setColor(getBackground());
        g2d.fill(roundedRectangle);

        // Zeichnen des Rahmens mit der Vordergrundfarbe des Buttons.
        g2d.setColor(getForeground());
        g2d.draw(roundedRectangle);

        g2d.dispose();

        super.paintComponent(g);
    }
}