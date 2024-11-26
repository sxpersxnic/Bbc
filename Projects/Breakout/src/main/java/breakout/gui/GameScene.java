package breakout.gui;

import breakout.common.AnimationTimer;
import breakout.common.BaseScene;
import breakout.common.Initializable;
import breakout.common.Navigator;
import breakout.game.GameManager;
import breakout.game.Images;
import breakout.game.KeyEventHandler;


public class GameScene extends BaseScene implements Initializable {

    private AnimationTimer gameLoop;

    public GameScene(Navigator<SceneType> navigator) {
        super(navigator);
    }

    @Override
    public void onInitialize() {

        KeyEventHandler keyEventHandler = new KeyEventHandler();

        this.setOnKeyPressed(keyEventHandler);
        this.setOnKeyReleased(keyEventHandler);

        GameManager gameManager = new GameManager(keyEventHandler, navigator, () -> gameLoop.stop());
        gameManager.load(7, 10);

        gameLoop = new AnimationTimer() {
            @Override
            protected void doHandle(double deltaInSec) {
                gameManager.update(deltaInSec);
                gameManager.draw(canvas.getGraphicsContext2D());
            }
        };
        gameLoop.start();
    }
}
