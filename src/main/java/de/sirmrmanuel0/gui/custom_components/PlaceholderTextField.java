package de.sirmrmanuel0.gui.custom_components;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderTextField extends JTextField implements FocusListener, DocumentListener {
    private String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        addFocusListener(this);
        getDocument().addDocumentListener(this);
    }

    // Überschreiben der paintComponent-Methode, um den Platzhaltertext zu zeichnen.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Nur den Platzhaltertext zeichnen, wenn das Textfeld leer ist und den Fokus nicht hat.
        if (getText().isEmpty() && !hasFocus()) {
            FontMetrics metrics = g.getFontMetrics(getFont());
            int textWidth = metrics.stringWidth(placeholder);
            int textHeight = metrics.getHeight();
            int x = (getWidth() - textWidth) / 2;
            int y = ((getHeight() - textHeight) / 2) + metrics.getAscent();
            g.setColor(Color.LIGHT_GRAY);
            g.drawString(placeholder, x, y);
        }
    }

    // Implementierung der FocusListener-Schnittstelle für Fokusereignisse.
    @Override
    public void focusGained(FocusEvent e) {
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        repaint();
    }

    // Implementierung der DocumentListener-Schnittstelle für Dokumentänderungen.
    @Override
    public void insertUpdate(DocumentEvent e) {
        repaint();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        repaint();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        repaint();
    }
}