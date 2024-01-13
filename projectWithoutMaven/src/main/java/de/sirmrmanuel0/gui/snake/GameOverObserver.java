package de.sirmrmanuel0.gui.snake;

public interface GameOverObserver {
    void onGameOver(int score);
    void toClose();
}
