package breakout.game;

import breakout.common.Navigator;
import breakout.game.gameobjects.*;
import breakout.gui.SceneType;
import javafx.scene.canvas.GraphicsContext;

public class GameManager {

    private final KeyEventHandler keyEventHandler;
    private final Navigator<SceneType> navigator;
    private final Runnable gameLoopStopper;
    private final GameObjects gameObjects;
    private final Score score;
    private final CollisionHandler collisionHandler;

    public GameManager(KeyEventHandler keyEventHandler, Navigator<SceneType> navigator, Runnable gameLoopStopper) {
        this.keyEventHandler = keyEventHandler;
        this.navigator = navigator;
        this.gameLoopStopper = gameLoopStopper;
        this.gameObjects = new GameObjects();
        this.score = new Score();
        this.collisionHandler = new CollisionHandler(gameObjects, score);
    }

    public void load(int numRows, int numCols) {
        gameObjects.add(new Paddle(keyEventHandler));
        gameObjects.add(new Ball(Const.SCREEN_WIDTH / 2, Const.SCREEN_HEIGHT / 2));
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                double x = col * Images.TEST_BLOCK.getWidth();
                double y = row * Images.TEST_BLOCK.getHeight();
                gameObjects.add(new Block(x, y, gameObjects));
            }
        }
    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
        gc.drawImage(Images.BACKGROUND, 0, 0);
        for (GameObject object : gameObjects) {
            object.draw(gc);
        }
        score.draw(gc);
    }

    public void update(double deltaInSec) {
        for (GameObject object : gameObjects) {
            object.update(deltaInSec);
        }
        collisionHandler.handle();

        if (gameObjects.getBlocks().isEmpty()) {
            navigator.goTo(SceneType.GAME_WON);
            stop();
        }


    }

    private void stop() {
        gameLoopStopper.run();
        gameObjects.clear();
    }
}
