package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Snake;

import java.util.List;

public abstract class Enemy extends GameEntity{
    private final int damage;
    public Snake snake;
    private int speed = 1;
    private String Image;

    public Enemy(int damage) {
        this.damage = damage;
    }

    public Enemy (Snake snakeToChase, String Image, int damage){
        this.damage = damage;
        this.snake = snakeToChase;
        this.Image = Image;

    }

    public boolean canSpawn() {
        boolean canspawn = true;
        List<GameEntity> gameObjs = Globals.getInstance().display.getObjectList();
        for (int i = 0; i < gameObjs.size(); ++i) {
            GameEntity otherObj = gameObjs.get(i);
            if (otherObj instanceof Interactable) {
                if (this.getBoundsInParent().intersects(otherObj.getBoundsInParent())) {
                    canspawn = false;

                }
            }
        }
        return canspawn;

    }

    public int getDamage() {
        return damage;
    }
}
