package breakout.game;

import breakout.common.Util;
import breakout.game.gameobjects.Ball;
import breakout.game.gameobjects.Block;
import breakout.game.gameobjects.GameObject;
import breakout.game.gameobjects.Paddle;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameObjects extends CopyOnWriteArrayList<GameObject> {
    public boolean hasPaddle() {
        return !getAllObjectsFromType(Paddle.class).isEmpty();
    }

    public Paddle getPaddle() {
        return getAllObjectsFromType(Paddle.class).get(0);
    }

    public boolean hasBall() {
        return !getAllObjectsFromType(Ball.class).isEmpty();
    }

    public Ball getBall() {
        return getAllObjectsFromType(Ball.class).get(0);
    }

    public List<Block> getBlocks() {
        return getAllObjectsFromType(Block.class);
    }



    public <T> List<T> getAllObjectsFromType(Class<T> clazz) {
        return Util.getAllObjectsFromType(clazz, this);
    }

}

