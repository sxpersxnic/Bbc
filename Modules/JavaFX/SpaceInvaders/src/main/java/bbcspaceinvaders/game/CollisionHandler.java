package bbcspaceinvaders.game;

import bbcspaceinvaders.game.gameobjects.GameObject;
import bbcspaceinvaders.game.gameobjects.Laser;
import bbcspaceinvaders.game.gameobjects.Spaceship;

import java.util.List;

public class CollisionHandler {

    private final GameObjects gameObjects;
    private final Score score;

    public CollisionHandler(GameObjects gameObjects, Score score) {
        this.gameObjects = gameObjects;
        this.score = score;
    }

    public void handle() {
        handleLaserAlienShipCollisions();
        handleLaserBombCollisions();
        handleBombSpaceShipCollisions();
    }

    private void handleLaserAlienShipCollisions() {
        for (Laser laser : gameObjects.getLasers()) {
            if (handleCollision(laser, gameObjects.getAlienShips())) {
                score.increaseScoreForAlienShip();
            }
        }
    }

    private void handleLaserBombCollisions() {
        for (Laser laser : gameObjects.getLasers()) {
            if (handleCollision(laser, gameObjects.getBombs())) {
                score.increaseScoreForBomb();
            }
        }
    }

    private void handleBombSpaceShipCollisions() {
        Spaceship spaceship = gameObjects.getSpaceShip();
        if (spaceship != null) {
            handleCollision(spaceship, gameObjects.getBombs());
        }
    }

    private boolean handleCollision(GameObject gameObject, List<? extends GameObject> gameObjects) {
        for (GameObject o : gameObjects) {
            if (o.collidesWith(gameObject)) {
                this.gameObjects.remove(o);
                this.gameObjects.remove(gameObject);
                return true;
            }
        }
        return false;

    }

}