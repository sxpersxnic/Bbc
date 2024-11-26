package bbcspaceinvaders.game.gameobjects;

import bbcspaceinvaders.game.*;

public class Spaceship extends GameObject {

    private final static double SPEED = 100;
    private final static double SHIP_Y = 480;
    private final static double LASER_SHOT_TIME = 1;
    private final KeyEventHandler keyEventHandler;
    private final GameObjects gameObjects;
    private double shipBattery = 1;

    public Spaceship(KeyEventHandler keyEventHandler, GameObjects gameObjects) {
        super(Const.SCREEN_WIDTH / 2, SHIP_Y, Images.SPACE_SHIP);
        this.keyEventHandler = keyEventHandler;
        this.gameObjects = gameObjects;
    }

    @Override
    public void update(double deltaInSec) {
        handleNavigationEvents(deltaInSec);
        handleShootEvent(deltaInSec);
    }

    private void handleNavigationEvents(double deltaInSec) {

        double distanceToMove = SPEED * deltaInSec;

        if (keyEventHandler.isRightKeyPressed() && getX() < Const.SCREEN_WIDTH - getImage().getWidth())
            x += distanceToMove;
        if (keyEventHandler.isLeftKeyPressed() && getX() > 0)
            x -= distanceToMove;
    }

    private void handleShootEvent(double deltaInSec) {
        if (keyEventHandler.isSpaceKeyPressed() && shipBattery > LASER_SHOT_TIME) {
            gameObjects.add(new Laser(getX() + (getImage().getWidth() / 2), getY()));
            Sound.play(SoundEffectType.LASER_FIRED);
            shipBattery = 0;
        } else {
            shipBattery += deltaInSec;
        }
    }

}