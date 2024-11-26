package bbcspaceinvaders.game.gameobjects;

import bbcspaceinvaders.game.Const;
import bbcspaceinvaders.game.GameObjects;
import bbcspaceinvaders.game.Images;

public class Alienship extends GameObject {

    private final static double SPEED = 100;
    private final static double BOMB_DROP_CHANCE = 0.1;
    private final GameObjects gameObjects;
    private Direction direction = Direction.RIGHT;

    public Alienship(double x, double y, GameObjects gameObjects) {
        super(x, y, Images.ALIEN_SHIP);
        this.gameObjects = gameObjects;
    }

    @Override
    public void update(double deltaInSec) {
        moveInCurrentDirection(deltaInSec);
        changeDirectionIfNeeded();
        dropBomb(deltaInSec);
    }

    private void dropBomb(double deltaInSec) {
        if (Math.random() < deltaInSec * BOMB_DROP_CHANCE) {
            gameObjects.add(new Bomb(getBoundingBox().getCenterX(), getBoundingBox().getMaxY()));
        }
    }

    private void moveInCurrentDirection(double deltaTimeInSec) {
        double distanceToMove = SPEED * deltaTimeInSec;

        if (direction == Direction.RIGHT) {
            x += distanceToMove;
        } else if (direction == Direction.LEFT) {
            x -= distanceToMove;
        }
    }

    private void changeDirectionIfNeeded() {
        if (getX() + getImage().getWidth() > Const.SCREEN_WIDTH) {
            direction = Direction.LEFT;
        }
        if (getX() < 0) {
            direction = Direction.RIGHT;
        }
    }

}