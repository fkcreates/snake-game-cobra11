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

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Police1 extends Enemy implements Animatable, Interactable {

    private Point2D heading;
    private static Random rnd = new Random();

    public Police1() {
        super(10);
        boolean canSpwan = canSpawn();
        System.out.println("I just spawned");
        if (!canSpwan){
            Globals.getInstance().game.spawnPolice();
            destroy();
        }
        setImage(Globals.getInstance().getImage("SnakeHead"));
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);

        double direction = rnd.nextDouble() * 360;
        setRotate(direction);

        int speed = 0;
        heading = Utils.directionToVector(direction, speed);
    }


    @Override
    public void step() {
        double snake1posX;
        double snake1posY;
        snake1posX = Globals.getInstance().snakes.get(0).getHead().getPosition().x;
        snake1posY = Globals.getInstance().snakes.get(0).getHead().getPosition().y;
        double angle;
        angle = Math.toDegrees(Math.atan((this.getY()-snake1posY)/(this.getX()-snake1posX)));
        angle -=90;
//        angle = Math.toDegrees(Math.atan(snake1posY - getY())/(snake1posX - getX()));
        heading = Utils.directionToVector(angle, 1);
        System.out.println(angle);
        double vectorMag = Math.sqrt((snake1posX - getX())*(snake1posX - getX()) +  (snake1posY - getY())*(snake1posY - getY()));
        setX(getX() + (snake1posX - getX())/vectorMag);
        setY(getY() + (snake1posY - getY())/vectorMag);
        if(snake1posX - getX() > 0){
            angle += 180;
        }
        setRotate(angle);
    }


    @Override
    public void apply(GameEntity entity) {
        if (entity instanceof SnakeHead) {
            System.out.println(getMessage());
            Globals.getInstance().game.spawnPolice();
            destroy();
        }
    }

    @Override
    public String getMessage() {
        return (getDamage() + " damage");
    }
}


