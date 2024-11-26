package breakout.game;

import javafx.scene.image.Image;

public class Images {
    public final static Image PADDLE = getImage("Paddle.png");
    public final static Image TEST_BLOCK = getImage("White_Block.png");
    public final static Image BALL = getImage("Ball.png");
    public final static Image BACKGROUND = getImage("Background_Image.png");

    private static Image getImage(String imagePath) {
        return new Image("/images/" + imagePath);
    }
}
