package com.codecool.snake.entities.snakes;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.enemies.Enemy;

import com.codecool.snake.entities.enemies.Police;
import com.codecool.snake.entities.powerups.SimplePowerUp;

import com.codecool.snake.entities.powerups.*;


import com.sun.javafx.geom.Vec2d;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class SnakeHead extends GameEntity implements Interactable {
    private static final float turnRate = 2;
    private Snake snake;
    private Police chaser;

    public void setChaser(Police chaser) {
        this.chaser = chaser;
    }

    public Police getChaser() {
        return chaser;
    }

    public SnakeHead(Snake snake, Vec2d position, String imageName) {
        this.snake = snake;
        setImage(Globals.getInstance().getImage(imageName));
        setPosition(position);
    }

    public void updateRotation(SnakeControl turnDirection, float speed) {
        double headRotation = getRotate();

        if (turnDirection.equals(SnakeControl.TURN_LEFT)) {
            headRotation = headRotation - turnRate;
        }
        if (turnDirection.equals(SnakeControl.TURN_RIGHT)) {
            headRotation = headRotation + turnRate;
        }

        // set rotation and position
        setRotate(headRotation);
        Point2D heading = Utils.directionToVector(headRotation, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(GameEntity entity) {
        if (entity instanceof Enemy) {
            if (entity instanceof Police) {
                if (entity.equals(chaser)) {
                    snake.changeHealth(((Enemy) entity).getDamage());
                }
            } else {
                snake.changeHealth(((Enemy) entity).getDamage());
            }
        }
        if (entity instanceof SimplePowerUp) {
            System.out.println(getMessage());
            snake.addPart(4, snake.getBodyImageName());
            snake.setScore(4);
        }
        if (entity instanceof ChangeControlPowerUp) {
            int actualSnakeId = snake.getSnakeId();
            if (actualSnakeId == 1) {
                Snake oppositeSnake = Globals.getInstance().game.getSnakes().get(1);
                oppositeSnake.setAreControlsChanging(true);
            } else {
                Snake oppositeSnake = Globals.getInstance().game.getSnakes().get(0);
                oppositeSnake.setAreControlsChanging(true);
            }
        }
        if (entity instanceof DismissChangeControlPowerUp) {
            if (snake.getAreControlsChanging()) {
                snake.setAreControlsChanging(false);
            }
        }
        if (entity instanceof StopPowerUp) {
            int actualSnakeId = snake.getSnakeId();
            if (actualSnakeId == 1) {
                Snake oppositeSnake = Globals.getInstance().game.getSnakes().get(1);
                oppositeSnake.setSkipSteps(100);

            } else {
                Snake oppositeSnake = Globals.getInstance().game.getSnakes().get(0);
                oppositeSnake.setSkipSteps(100);
            }
        }
        if (entity instanceof HealthPowerUp) {
            if (snake.getHealth() >= 90) {
                snake.setHealth(100);
            } else {
                snake.changeHealth(-10);
            }
        }
        if (entity instanceof SnakeBody) {
            if (!snake.getBody().getList().contains(entity)) {
                snake.setHealth(0);
            }
        }
        if (entity instanceof SnakeHead) {
            ArrayList<Snake> snakes = Globals.getInstance().game.getSnakes();
            for (Snake snake : snakes) {
                snake.setHealth(0);
            }
        }
    }


    @Override
    public String getMessage() {
        return "";
    }
}
