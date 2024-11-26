package breakout.common;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Navigator<T> {

    private final Stage stage;
    private final Map<T, Scene> viewMap = new HashMap<>();

    public Navigator(Stage stage) {
        this.stage = stage;
    }

    public void registerScene(T enumScene, Scene scene) {
        viewMap.put(enumScene, scene);
    }

    public void goTo(T scene) {
        Scene activeScene = viewMap.get(scene);

        if (activeScene instanceof Initializable) {
            ((Initializable) activeScene).onInitialize();
        }

        stage.setScene(activeScene);
    }
}
