package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.entities.snakes.SnakeHead;

import java.util.List;
import java.util.Random;

public abstract class Enemy extends GameEntity {
    private final int damage;
    public Snake snake;
    private int speed = 1;
    private String Image;
    private static Random rnd = new Random();


    public Enemy(int damage) {
        this.damage = damage;
    }

    public Enemy(Snake snakeToChase, String Image, int damage) {
        this.damage = damage;
        this.snake = snakeToChase;
        this.Image = Image;

    }

    public double[] canSpawn(SnakeHead snakeHead) {
        double[] coordinates = new double[2];
//        boolean canspawn = true;
//        List<GameEntity> gameObjs = Globals.getInstance().display.getObjectList();
//        for (int i = 0; i < gameObjs.size(); ++i) {
//            GameEntity otherObj = gameObjs.get(i);
//            if (otherObj instanceof Interactable) {
//                if (this.getBoundsInParent().intersects(otherObj.getBoundsInParent())) {
//                    canspawn = false;
//
//                }
//            }
//        }
        //get snakhead coords
        double[] snakeHeadPos = {snakeHead.getPosition().x, snakeHead.getPosition().y};
        double x;
        double y;
        double distance;
        while (true) {
            x = (rnd.nextDouble() * Globals.WINDOW_WIDTH);
            y = (rnd.nextDouble() * Globals.WINDOW_HEIGHT);
            coordinates[0] = x;
            coordinates[1] = y;
            distance = Math.sqrt((x - snakeHeadPos[0]) * (x - snakeHeadPos[0]) + (y - snakeHeadPos[1]) * (y - snakeHeadPos[1]));
            if (distance > 100) {
                break;
            }

        }
        return coordinates;
    }

    public int getDamage() {
        return damage;
    }
}
