package bbcspaceinvaders.game;

import bbcspaceinvaders.common.Util;
import bbcspaceinvaders.game.gameobjects.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameObjects extends CopyOnWriteArrayList<GameObject> {

    public boolean hasSpaceShip() {
        return !getAllObjectsFromType(Spaceship.class).isEmpty();
    }

    public Spaceship getSpaceShip() {
        return getAllObjectsFromType(Spaceship.class).get(0);
    }

    public List<Alienship> getAlienShips() {
        return getAllObjectsFromType(Alienship.class);
    }

    public List<Bomb> getBombs() {
        return getAllObjectsFromType(Bomb.class);
    }

    public List<Laser> getLasers() {
        return getAllObjectsFromType(Laser.class);
    }

    public <T> List<T> getAllObjectsFromType(Class<T> clazz) {
        return Util.getAllObjectsFromType(clazz, this);
    }

}
