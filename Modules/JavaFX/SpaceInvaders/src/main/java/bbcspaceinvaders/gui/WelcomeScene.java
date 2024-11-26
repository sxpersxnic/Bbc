package bbcspaceinvaders.gui;

import bbcspaceinvaders.common.BaseScene;
import bbcspaceinvaders.common.Initializable;
import bbcspaceinvaders.common.Navigator;
import bbcspaceinvaders.game.Images;
import bbcspaceinvaders.game.MusicType;
import bbcspaceinvaders.game.Sound;
import javafx.scene.input.KeyCode;

public class WelcomeScene extends BaseScene implements Initializable {

    public WelcomeScene(Navigator<SceneType> navigator) {
        super(navigator, Images.WELCOME_BACKGROUND);

        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                navigator.goTo(SceneType.GAME);
            }
        });
    }

    @Override
    public void onInitialize() {
        Sound.play(MusicType.INTRO);
    }

}
