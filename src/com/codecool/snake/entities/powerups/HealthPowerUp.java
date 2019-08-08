package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;

import java.util.Random;

public class HealthPowerUp extends GameEntity implements Interactable {
    private static Random rnd = new Random();

    public HealthPowerUp() {
        setImage(Globals.getInstance().getImage("HealthPowerUp"));

        double x = 0;
        double y = 0;

        while (x < 140 && y < 140) {
            x = rnd.nextDouble() * Globals.WINDOW_WIDTH - 40;
            y = rnd.nextDouble() * Globals.WINDOW_HEIGHT - 40;
        }

        setX(x);
        setY(y);
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
        return "My health restored by +10";
    }
}
