package bbcspaceinvaders.gui;

import bbcspaceinvaders.common.BaseScene;
import bbcspaceinvaders.common.FancyAnimationTimer;
import bbcspaceinvaders.common.Initializable;
import bbcspaceinvaders.common.Navigator;
import bbcspaceinvaders.game.*;

public class GameScene extends BaseScene implements Initializable {

    private FancyAnimationTimer gameLoop;
    private Score score;

    public GameScene(Navigator<SceneType> navigator) {
        super(navigator);
    }

    @Override
    public void onInitialize() {

        KeyEventHandler keyEventHandler = new KeyEventHandler();

        this.setOnKeyPressed(keyEventHandler);
        this.setOnKeyReleased(keyEventHandler);

        Sound.play(MusicType.BACKGROUND);

        Space space = new Space(keyEventHandler, navigator, () -> gameLoop.stop());
        space.load();

        gameLoop = new FancyAnimationTimer() {
            @Override
            public void doHandle(double deltaInSec) {
                space.update(deltaInSec);
                space.draw(canvas.getGraphicsContext2D());


            }
        };
        gameLoop.start();
    }
}
