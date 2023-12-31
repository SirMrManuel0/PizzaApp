package de.sirmrmanuel0.gui.custom_components;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedCornerPanel extends JPanel {

    private int cornerRadius;

    public RoundedCornerPanel(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false);
    }

    // Überschreiben der paintComponent-Methode, um ein JPanel mit abgerundeten Ecken zu zeichnen.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics2D = (Graphics2D) g.create();

        // Einstellen von Rendering-Hints für Anti-Aliasing.
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Füllen des Hintergrunds mit der Hintergrundfarbe des Panels.
        graphics2D.setColor(getBackground());
        graphics2D.fill(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, cornerRadius, cornerRadius));

        // Zeichnen des Rahmens mit der Vordergrundfarbe des Panels.
        graphics2D.setColor(getForeground());
        graphics2D.draw(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, cornerRadius, cornerRadius));

        graphics2D.dispose();
    }

}