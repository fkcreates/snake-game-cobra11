package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.entities.snakes.SnakeHead;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Enemy extends GameEntity {
    private final int damage;
    public Snake snake;
    private int speed = 1;
    private String Image;
    private static Random rnd = new Random();
    ArrayList<SnakeHead> snakeHeads;



    public Enemy(int damage) {
        this.damage = damage;
    }

    public Enemy(Snake snakeToChase, String Image, int damage) {
        this.damage = damage;
        this.snake = snakeToChase;
        this.Image = Image;

    }

    public double[] canSpawn() {
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

        double[] snakeHead1Pos = {Globals.getInstance().snakes.get(0).getHead().getPosition().x, Globals.getInstance().snakes.get(0).getHead().getPosition().y };
        double[] snakeHead2Pos = {Globals.getInstance().snakes.get(1).getHead().getPosition().x, Globals.getInstance().snakes.get(1).getHead().getPosition().y };
        double x;
        double y;
        double distanceFromSnake1;
        double distanceFromSnake2;
        while (true) {
            x = (rnd.nextDouble() * Globals.WINDOW_WIDTH);
            y = (rnd.nextDouble() * Globals.WINDOW_HEIGHT);
            coordinates[0] = x;
            coordinates[1] = y;
            distanceFromSnake1 = Math.sqrt((x - snakeHead1Pos[0]) * (x - snakeHead1Pos[0]) + (y - snakeHead1Pos[1]) * (y - snakeHead1Pos[1]));
            distanceFromSnake2 = Math.sqrt((x - snakeHead2Pos[0]) * (x - snakeHead2Pos[0]) + (y - snakeHead2Pos[1]) * (y - snakeHead2Pos[1]));
            if (distanceFromSnake1 > 300 && distanceFromSnake2 > 300 ) {
                break;
            }

        }
        return coordinates;
    }

    public int getDamage() {
        return damage;
    }
}
