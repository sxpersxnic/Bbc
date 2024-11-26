package bbcspaceinvaders.game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyEventHandler implements EventHandler<KeyEvent> {

    private boolean isLeftKeyPressed = false;
    private boolean isRightKeyPressed = false;
    private boolean isSpaceKeyPressed = false;

    @Override
    public void handle(KeyEvent event) {
        boolean pressed = event.getEventType() == KeyEvent.KEY_PRESSED;
        switch (event.getCode()) {
            case LEFT:
                isLeftKeyPressed = pressed;
                break;
            case RIGHT:
                isRightKeyPressed = pressed;
                break;
            case SPACE:
                isSpaceKeyPressed = pressed;
                break;
        }
    }

    public boolean isLeftKeyPressed() {
        return isLeftKeyPressed;
    }

    public boolean isRightKeyPressed() {
        return isRightKeyPressed;
    }

    public boolean isSpaceKeyPressed() {
        return isSpaceKeyPressed;
    }

}
