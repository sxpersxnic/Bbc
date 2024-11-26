package breakout;

import breakout.common.Navigator;
import breakout.gui.GameOverScene;
import breakout.gui.GameScene;
import breakout.gui.GameWonScene;
import breakout.gui.SceneType;
import javafx.application.Application;
import javafx.stage.Stage;

public class BreakoutApp extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Breakout");

        Navigator<SceneType> navigator = new Navigator<>(stage);
        navigator.registerScene(SceneType.GAME, new GameScene(navigator));
        navigator.registerScene(SceneType.GAME_OVER, new GameOverScene(navigator));
        navigator.registerScene(SceneType.GAME_WON, new GameWonScene(navigator));

        navigator.goTo(SceneType.GAME);

        stage.show();

    }
}
