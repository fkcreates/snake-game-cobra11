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
//        boolean cansSpwan = canSpawn();
//        if (!cansSpwan) {
//            destroy();
//        } else {
            setImage(Globals.getInstance().getImage("SimpleEnemy"));

            setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
            setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);

            double direction = rnd.nextDouble() * 360;
            setRotate(direction);

            int speed = 0;
            heading = Utils.directionToVector(direction, speed);
//        }
    }




    @Override
    public void step() {
        if (isOutOfBounds()) {
            destroy();
        }
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
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
