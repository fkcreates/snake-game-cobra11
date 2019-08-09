package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.enemies.Enemy;
import com.codecool.snake.entities.snakes.SnakeBody;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.geometry.Point2D;

import java.util.List;
import java.util.Random;

public class Bump extends Enemy implements Animatable, Interactable {

    private Point2D heading;
    private static Random rnd = new Random();

    public Bump() {
        super(10);
        setImage(Globals.getInstance().getImage("Bump"));

        boolean areCorrectCoordinates = false;
        while (!areCorrectCoordinates) {
            double x = rnd.nextDouble() * Globals.WINDOW_WIDTH;
            double y = rnd.nextDouble() * Globals.WINDOW_HEIGHT;
            if (((x > 90) && (x < 900)) && ((y > 90) && (y < 600))) {
                setX(x);
                setY(y);
                areCorrectCoordinates = true;
            }
        }
    }

    @Override
    public void step() {
        if (isOutOfBounds()) {
            destroy();
        }
    }

    @Override
    public void apply(GameEntity entity) {
        if (entity instanceof SnakeHead) {
            System.out.println(getMessage());
            destroy();
            Globals.getInstance().game.spawnBumps(1);
        }
        if (entity instanceof SnakeBody) {
            destroy();
        }
    }

    @Override
    public String getMessage() {
        return (getDamage() + " damage");
    }

}
