package breakout.game.gameobjects;

import breakout.game.Const;
import breakout.game.Images;
import breakout.game.KeyEventHandler;

public class Paddle extends GameObject{

    private final static double SPEED = 500;
    private final static double PADDLE_Y = 550;
    private final KeyEventHandler keyEventHandler;

    public Paddle(KeyEventHandler keyEventHandler) {
        super(Const.SCREEN_WIDTH / 2, PADDLE_Y, Images.PADDLE);
        this.keyEventHandler = keyEventHandler;
    }


    @Override
    public void update(double deltaInSec) {
        handleNavigationEvents(deltaInSec);

    }

    private void handleNavigationEvents(double deltaInSec) {
        double distanceToMove = SPEED * deltaInSec;

        if (keyEventHandler.isRightKeyPressed() && getX() < Const.SCREEN_WIDTH - getImage().getWidth())
            x += distanceToMove;
        if (keyEventHandler.isLeftKeyPressed() && getX() > 0)
            x -= distanceToMove;


    }


}
