package bbcspaceinvaders.game.gameobjects;

import bbcspaceinvaders.game.Images;

public class Bomb extends GameObject {

    private final static double SPEED = 100;

    public Bomb(double x, double y) {
        super(x, y, Images.BOMB);
    }

    @Override
    public void update(double deltaInSec) {
        y += SPEED * deltaInSec;
    }

}
