package bbcspaceinvaders.game;

import javafx.scene.image.Image;

public class Images {

    public final static Image BOMB = getImage("bomb.png");
    public final static Image LASER = getImage("projectile.png");
    public final static Image ALIEN_SHIP = getImage("alienship.png");
    public final static Image SPACE_SHIP = getImage("spaceship.png");
    public final static Image GAME_BACKGROUND = getImage("background_game.png");
    public final static Image WELCOME_BACKGROUND = getImage("background_welcome.png");
    public final static Image GAME_OVER_BACKGROUND = getImage("background_gameover.png");
    public final static Image GAME_WON_BACKGROUND = getImage("background_mission_completed.png");

    private static Image getImage(String imagePath) {
        return new Image("/images/" + imagePath);
    }

}
