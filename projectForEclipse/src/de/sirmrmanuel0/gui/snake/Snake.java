package de.sirmrmanuel0.gui.snake;

import javax.swing.*;
import java.awt.*;

public class Snake extends JFrame {
    public Snake(JPanel panel){
        setTitle("Rabatt Spiel: Snake");
        setSize(new Dimension(600,600));
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

}
