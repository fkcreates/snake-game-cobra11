package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.entities.snakes.SnakeHead;
import com.sun.javafx.geom.Vec2d;
import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Police extends Enemy implements Animatable, Interactable {


    private static Random rnd = new Random();
    private int speed = 1;
    private int id;
    Snake snakeToChase;
    SnakeHead snakeHead;






    public void setId(int id) {
        this.id = id;
    }

    public Police(Snake snakeToChase, String Image, int damage) {
        super(snakeToChase, Image,damage);
        snakeToChase = snakeToChase;
        snakeHead = snakeToChase.getHead();
        double possibleX ;
        double possibleY ;
        possibleX = canSpawn()[0];
        possibleY = canSpawn()[1];
        setImage(Globals.getInstance().getImage(Image));
        setX(possibleX);
        setY(possibleY);
        double direction = rnd.nextDouble() * 360;
        setRotate(direction);
    }


    @Override
    public void step() {
        double snakePosX;
        double snakePosY;
//        snakePosX = Globals.getInstance().snakes.get(0).getHead().getPosition().x;
//        snakePosY = Globals.getInstance().snakes.get(0).getHead().getPosition().y;
        snakePosX = snake.getHead().getPosition().x;
        snakePosY = snake.getHead().getPosition().y;
        double angle;
        angle = Math.toDegrees(Math.atan((this.getY()-snakePosY)/(this.getX()-snakePosX)));
        angle -=90;
//        System.out.println(angle);
        double vectorMag = Math.sqrt((snakePosX - getX())*(snakePosX - getX()) +  (snakePosY - getY())*(snakePosY - getY()));
        setX(getX() + (snakePosX - getX())/vectorMag * this.speed);
        setY(getY() + (snakePosY - getY())/vectorMag * this.speed);
        if(snakePosX - getX() > 0){
            angle += 180;
        }
        setRotate(angle);
//        if(this.snakeToChase.getHealth() <= 0){
//            destroy();
//        }
//        System.out.println(snakeToChase);
    }


    @Override
    public void apply(GameEntity entity) {

        if (entity == snakeHead) {
//            System.out.println(getMessage());
//            Globals.getInstance().game.spawnPolice();
//            destroy();   random placement instead
            setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
            setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
            double direction = rnd.nextDouble() * 360;
            setRotate(direction);
        }

    }

    @Override
    public String getMessage() {
        return (getDamage() + " damage");
    }
}


