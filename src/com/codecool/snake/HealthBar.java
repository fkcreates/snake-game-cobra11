package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;

public class HealthBar extends GameEntity {

    public HealthBar(String picName, double x, double y) {
        setImage(Globals.getInstance().getImage(picName));
        setX(x);
        setY(y);
    }
}