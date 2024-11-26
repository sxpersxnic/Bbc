package bbcspaceinvaders.main;

import bbcspaceinvaders.common.Navigator;
import bbcspaceinvaders.gui.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class BbcSpaceInvadersApp extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Bbc SpaceInvaders");

        Navigator<SceneType> navigator = new Navigator<>(stage);
        navigator.registerScene(SceneType.WELCOME, new WelcomeScene(navigator));
        navigator.registerScene(SceneType.GAME, new GameScene(navigator));
        navigator.registerScene(SceneType.GAME_OVER, new GameOverScene(navigator));
        navigator.registerScene(SceneType.GAME_WON, new GameWonScene(navigator));

        navigator.goTo(SceneType.WELCOME);

        stage.show();
    }

}