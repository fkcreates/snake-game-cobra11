package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;

import java.util.Random;

public class DismissChangeControlPowerUp extends GameEntity implements Interactable {
    private static Random rnd = new Random();

    public DismissChangeControlPowerUp() {
        setImage(Globals.getInstance().getImage("PowerUpClearChangeControl"));

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
    public void apply(GameEntity entity) {
        if (entity instanceof SnakeHead) {
            System.out.println(getMessage());
            destroy();
            Globals.getInstance().game.spawnPowerUps(1);
        }
    }

    @Override
    public String getMessage() {
        return "Got my controls cleared!";
    }

}
