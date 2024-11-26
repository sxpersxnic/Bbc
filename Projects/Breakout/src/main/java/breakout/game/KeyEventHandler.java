package breakout.game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyEventHandler implements EventHandler<KeyEvent> {

    private boolean isLeftKeyPressed = false;
    private boolean isRightKeyPressed = false;

    @Override
    public void handle(KeyEvent event) {
        boolean pressed = event.getEventType() == KeyEvent.KEY_PRESSED;
        switch (event.getCode()) {
            case LEFT:
                isLeftKeyPressed = pressed;
                break;
            case RIGHT:
                isRightKeyPressed = pressed;

        }

    }

    public boolean isRightKeyPressed() {
        return isRightKeyPressed;
    }

    public boolean isLeftKeyPressed() {
        return  isLeftKeyPressed;
    }
}
