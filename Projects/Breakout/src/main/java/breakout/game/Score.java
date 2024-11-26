package breakout.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Score {

    private int score;

    public Score() {
        this.score = 0;
    }

    public void increaseScore() {
        score += 5;
    }

    public int getScore() {
        return score;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", 40));
        gc.fillText("SCORE: " + getScore(), 10, 30);
    }
}
