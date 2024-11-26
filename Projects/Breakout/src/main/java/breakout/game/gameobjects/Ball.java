package breakout.game.gameobjects;

import breakout.game.*;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;


public class Ball extends GameObject {
    Bounce bounceLeftRight = Bounce.LEFT_BOUNCE;
    Bounce bounce = Bounce.BLOCK_BOUNCE;
    private double SPEED = 150;
    public Ball(double x, double y) {
        super(x, y, Images.BALL);
    }

    public void update(double deltaInSec) {
        bounceIfNeeded();
        moveInDirection(deltaInSec);
    }

    private void bounceIfNeeded() {
        if(x > (Const.SCREEN_WIDTH - Images.BALL.getWidth())) {
            bounceLeftRight = Bounce.RIGHT_BOUNCE;
        }
        if(x < 0){
            bounceLeftRight = Bounce.LEFT_BOUNCE;
        }
        if(y > (Const.SCREEN_HEIGHT - Images.BALL.getHeight())){
            bounce = Bounce.BOTTOM_BOUNCE;
        }
        if(y < 0){
            bounce = Bounce.TOP_BOUNCE;
        }
    }

    private void moveInDirection(double deltaTimeInSec) {
        double distanceToMove = SPEED * deltaTimeInSec;
        double negative_distanceToMove = -1 * (distanceToMove);
        switch (bounce){

            case BLOCK_BOUNCE:
                y += distanceToMove;
                break;

            case PADDLE_BOUNCE:
                y += negative_distanceToMove;
                break;

            case TOP_BOUNCE:
                System.out.println("BREAKOUT!");
                break;

            case BOTTOM_BOUNCE:
                bounceLeftRight = Bounce.BOTTOM_BOUNCE;
                // Game over FMXL Code da rein
                System.out.println("Game Over");
                break;
        }
        switch (bounceLeftRight){
            case BOTTOM_BOUNCE:
                break;
            case RIGHT_BOUNCE:
                x += negative_distanceToMove;
                break;

            case LEFT_BOUNCE:
                x += distanceToMove;
                break;
        }
    }
    public void increaseSpeed(double speedIncrease){
        SPEED += speedIncrease;
        System.out.println(SPEED);
    }
    public BoundingBox getBoundingBox() {
        return new BoundingBox(this.x, this.y, Images.BALL.getWidth(), Images.BALL.getHeight());
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(Images.BALL, x, y);
    }

    public Bounce getBounce() {
        return bounce;
    }

    public void setBounce(Bounce bounce) {
        this.bounce = bounce;
    }
}
