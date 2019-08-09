package com.codecool.snake.entities.snakes;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import java.util.LinkedList;
import java.util.Queue;

import com.codecool.snake.entities.Interactable;
import com.sun.javafx.geom.Vec2d;


public class SnakeBody extends GameEntity implements Interactable {
    private Queue<Vec2d> history = new LinkedList<>();
    private static final int historySize = 10;

    public SnakeBody(Snake snake, Vec2d coord, String bodyImage) {
        setImage(Globals.getInstance().getImage(bodyImage));
        setX(coord.x);
        setY(coord.y);

        for (int i = 0; i < historySize; i++) {
            history.add(coord);
        }
    }

    @Override
    public void setPosition(Vec2d pos) {
        Vec2d currentPos = history.poll(); // remove the oldest item from the history
        setX(currentPos.x);
        setY(currentPos.y);
        history.add(pos); // add the parent's current position to the beginning of the history
    }

    @Override
    public void apply(GameEntity entity) {
    }

    @Override
    public String getMessage() {
        return null;
    }

}