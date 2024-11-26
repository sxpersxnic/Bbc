package bbcspaceinvaders.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Score {

    private int score;

    public Score() {
        this.score = 0;
    }

    public void increaseScoreForAlienShip() {
        score += 5;
    }

    public void increaseScoreForBomb() {
        score += 1;
    }

    public int getScore() {
        return score;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 20));
        gc.fillText("Score: " + getScore(), 10, 30);
    }
}
