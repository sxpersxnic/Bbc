package breakout.game;

import breakout.game.gameobjects.Ball;
import breakout.game.gameobjects.Bounce;
import breakout.game.gameobjects.GameObject;
import breakout.game.gameobjects.Paddle;


import java.util.List;

public class CollisionHandler{

    private final GameObjects gameObjects;
    private final Score score;

    public CollisionHandler(GameObjects gameObjects, Score score) {
        this.gameObjects = gameObjects;
        this.score = score;
    }

    public void handle() {
        handleBallBlockCollisions();
        handlePaddleCollisions();
    }

    private void handlePaddleCollisions() {
        Paddle paddle= gameObjects.getPaddle();
        if (paddle != null) {
            handleCollision(paddle, gameObjects.getBall());
        }
    }

    private void handleBallBlockCollisions() {
        Ball ball = gameObjects.getBall();
        if (ball != null) {
            handleBlockCollision(ball, gameObjects.getBlocks());
        }
    }

    private void handleCollision(GameObject gameObject1, GameObject gameObject2){
        if (gameObject1.collidesWith(gameObject2)){
            Ball ball = (Ball) gameObject2;
            ball.setBounce(Bounce.PADDLE_BOUNCE);
        }
    }
    private void handleBlockCollision(GameObject gameObject, List<? extends GameObject> gameObjects) {
        for (GameObject o : gameObjects) {
            if (o.collidesWith(gameObject)) {
                this.gameObjects.remove(o);
                Ball ball = (Ball) gameObject;
                ball.setBounce(Bounce.BLOCK_BOUNCE);
                ball.increaseSpeed(10);
                score.increaseScore();
                return;
            }
        }
    }

}
