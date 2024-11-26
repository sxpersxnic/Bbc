package breakout.common;

import breakout.game.Const;
import breakout.gui.SceneType;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BaseScene extends Scene {

    protected final Navigator<SceneType> navigator;
    protected final Canvas canvas;
    public BaseScene(Navigator<SceneType> navigator) {
        super(new Group());
        this.navigator = navigator;
        canvas = new Canvas(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
        ((Group) getRoot()).getChildren().add(canvas);
    }

    public BaseScene(Navigator<SceneType> navigator, Image backgroundImage) {
        this(navigator);
        drawBackgroundImage(backgroundImage);
    }

    private void drawBackgroundImage(Image backgroundImage) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(backgroundImage, 0, 0);
    }

}
