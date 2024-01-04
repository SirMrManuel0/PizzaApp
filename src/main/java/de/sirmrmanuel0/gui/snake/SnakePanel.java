package de.sirmrmanuel0.gui.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakePanel extends JPanel implements ActionListener {

    // Konstanten für die Größe der Spielfläche, Einheitsgröße, Verzögerung und maximale Spielereinheiten.
    static final int UNIT_SIZE = 25;
    static final int DELAY = 75;
    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 600;
    static final int GAME_UNITS = (GAME_HEIGHT*GAME_WIDTH)/UNIT_SIZE;

    // Arrays zur Speicherung der Positionen von Schlangenkörper und Apfel.
    protected int[] x = new int[GAME_UNITS];
    protected int[] y = new int[GAME_UNITS];
    protected int bodyParts = 6;
    protected int score = 0;
    protected int appleX;
    protected int appleY;
    protected int timeTillClosing = 10;
    protected char direction = 'R';
    protected boolean running = false;
    protected Timer timer;
    protected Timer close;
    protected Random random;
    private ArrayList<GameOverObserver> observers;

    public SnakePanel(){
        observers = new ArrayList<>();
        random = new Random();
        close = new Timer(1000, new ClosingActionListener());
        appleY = 0;
        appleX = 0;
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new myKeyAdapter());
        startGame();
    }

    // Methode zum Hinzufügen eines Observers für Game Over-Ereignisse.
    public void addObserver(GameOverObserver observer){
        observers.add(observer);
    }

    // Benachrichtigung aller Observer über das Game Over-Ereignis.
    protected void notifyObservers() {
        for (GameOverObserver observer : observers) {
            observer.onGameOver(score);
        }
    }

    // Benachrichtigung aller Observer, dass das Spiel geschlossen wird.
    protected void notifyObserversClose(){
        for (GameOverObserver observer : observers) {
            observer.toClose();
        }
    }

    // Initialisierung des Spiels beim Start.
    protected void startGame(){
        genApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    // Überschreiben der paintComponent-Methode zum Zeichnen des Spiels.
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    // Methode zum Zeichnen des Spiels.
    protected void draw(Graphics g){
        if (running){
            // Zeichnen des Hintergrunds.
            int ev = 0;
            for (int i = 0; i<=GAME_HEIGHT/UNIT_SIZE; i++){
                if (i % 2 == 1){
                    ev = 0;
                } else {
                    ev = 1;
                }
                for (int ii = 0; ii<=GAME_WIDTH/UNIT_SIZE; ii++) {
                    if ((ii == 0) && (ev == 0)){
                        g.fillRect(0, i * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    } else if (ii == 0) {
                        continue;
                    }
                    if (ii % 2 == ev){
                        g.fillRect(ii * UNIT_SIZE, i * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    }

                }
            }

            // Zeichnen des Apfels.
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Zeichnen der Schlange.
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(2, 29, 207));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    g.setColor(new Color(6, 4, 125));
                    continue;
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            // Anzeigen der Punktzahl.
            g.setColor(new Color(112, 8, 8));
            g.setFont(new Font("SanSerif", Font.BOLD, 35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Punkte: " + score, (GAME_WIDTH - metrics.stringWidth("Punkte: " + score)) / 2, g.getFont().getSize());



        } else {
            // Anzeigen des Game Over-Bildschirms.
            gameOver(g);
        }
    }

    // Generieren eines neuen Apfels.
    protected void genApple(){
        while (newAppleInSnake()) {
            appleX = random.nextInt((int) (GAME_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            appleY = random.nextInt((int) (GAME_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        }

    }

    // Überprüfen, ob der neue Apfel in der Schlange liegt.
    protected boolean newAppleInSnake(){
        for (int i = 0; i < GAME_UNITS; i ++){
            if((x[i] == appleX) && (y[i] == appleY))
                return true;
        }
        return false;
    }

    // Bewegen der Schlange.
    protected void move(){
        for (int i = bodyParts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    // Umgekehrtes Bewegen der Schlange.
    protected void reverseMove(){
        for (int i = 0; i < bodyParts-1; i++){
            x[i] = x[i+1];
            y[i] = y[i+1];
        }

        switch (direction){
            case 'U':
                y[bodyParts-1] = y[bodyParts-1] + UNIT_SIZE;
                break;
            case 'D':
                y[bodyParts-1] = y[bodyParts-1] - UNIT_SIZE;
                break;
            case 'L':
                x[bodyParts-1] = x[bodyParts-1] + UNIT_SIZE;
                break;
            case 'R':
                x[bodyParts-1] = x[bodyParts-1] - UNIT_SIZE;
                break;
        }
    }

    // Überprüfen, ob der Apfel gefressen wurde.
    protected void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            score++;
            genApple();
        }


    }

    // Überprüfen auf Kollisionen.
    protected boolean checkCollisions(){
        // Testen, ob der Kopf mit dem Körper kollidiert.
        for (int i = bodyParts; i > 0; i--){
            if ((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
                break;
            }
        }

        // Testen, ob der Kopf mit den Spielfeldgrenzen kollidiert.
        //  linke Grenze | rechte Grenze | obere Grenze | untere Grenze
        if (x[0] < 0 || x[0] == GAME_WIDTH || y[0] < 0 || y[0] == GAME_HEIGHT){
            running = false;
        }

        // Stoppen des Timers und Rückgabe des Spielzustands.
        if (!running){
            timer.stop();
        }

        return !running;
    }

    // Anzeigen des Game Over-Bildschirms.
    protected void gameOver(Graphics g){
        close.start();

        // Konfiguriert die Schriftart und Farbe für den Game Over-Bildschirm.
        Font gameOverFont = new Font("SanSerif", Font.BOLD, 60);
        g.setColor(new Color(112, 8, 8));
        g.setFont(gameOverFont);

        // Ermittelt die Metriken der aktuellen Schriftart.
        FontMetrics metrics = getFontMetrics(g.getFont());

        // Zeichnet den Text "Game Over!" in der Mitte des Spielfelds.
        g.drawString("Game Over!", (GAME_WIDTH - metrics.stringWidth("Game Over!")) / 2, GAME_HEIGHT/2);

        // Konfiguriert die Schriftart und Farbe für die Punktzahl und den Schließungshinweis.
        g.setColor(new Color(112, 8, 8));
        g.setFont(new Font("SanSerif", Font.BOLD, 35));
        FontMetrics metrics2 = getFontMetrics(g.getFont());

        // Zeichnet die Punktzahl unter dem "Game Over!"-Text.
        g.drawString("Punkte: " + score, (GAME_WIDTH - metrics2.stringWidth("Punkte: " + score)) / 2, GAME_HEIGHT/2 + gameOverFont.getSize());

        // Zeichnet den Schließungshinweis und die verbleibende Zeit.
        g.drawString("Dieses Fenster schließt sich in " + timeTillClosing + "s.",
                (GAME_WIDTH - metrics2.stringWidth("Dieses Fenster schließt sich in " + timeTillClosing + "s.")) / 2,
                GAME_HEIGHT/2 + (gameOverFont.getSize() + g.getFont().getSize()));

        // Benachrichtigung der Observer über das Game Over-Ereignis.
        if ((!observers.isEmpty()) && (timeTillClosing == 10))
            notifyObservers();
        // Benachrichtigung der Observer über das Schließen des Spiels.
        if ((!observers.isEmpty()) && (timeTillClosing == 0))
            notifyObserversClose();
    }

    // KeyAdapter für Tastatureingaben.
    protected class myKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (direction != 'R')
                        direction = 'L';
                    break;


                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (direction != 'L')
                        direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (direction != 'D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (direction != 'U')
                        direction = 'D';
                    break;
                /*
                case KeyEvent.VK_T:
                    score += 100;
                    break;
                */
            }
        }
    }

    // ActionListener für das Schließen des Spiels.
    protected class ClosingActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            timeTillClosing --;
            repaint();
        }
    }

    // ActionListener für das Bewegen der Schlange.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            if (checkCollisions())
                reverseMove();
        }
        repaint();
    }
}
