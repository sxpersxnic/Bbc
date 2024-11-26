package breakout.gui;

import breakout.common.BaseScene;
import breakout.common.Navigator;
import javafx.scene.input.KeyCode;

public class GameOverScene extends BaseScene {

    public GameOverScene(Navigator<SceneType> navigator) {
        super(navigator);
        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                navigator.goTo(SceneType.GAME);
            }
        });
    }
}
