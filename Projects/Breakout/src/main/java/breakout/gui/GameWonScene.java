package breakout.gui;

import breakout.common.BaseScene;
import breakout.common.Navigator;
import javafx.scene.input.KeyCode;

public class GameWonScene extends BaseScene {

    public GameWonScene(Navigator<SceneType> navigator) {
        super(navigator);

        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                navigator.goTo(SceneType.GAME);
            }
        });
    }
}
