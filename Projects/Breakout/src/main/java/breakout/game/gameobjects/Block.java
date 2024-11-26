package breakout.game.gameobjects;

import breakout.game.GameObjects;
import breakout.game.Images;

public class Block extends GameObject {

    public Block(double x, double y, GameObjects gameObjects) {
        super(x, y, Images.TEST_BLOCK);
    }

    @Override
    public void update(double deltaInSec) {

    }
}
