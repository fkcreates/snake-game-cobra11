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

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Police extends Enemy implements Animatable, Interactable {

    private Point2D heading;
    private static Random rnd = new Random();

    public Police() {
        super(10);
        boolean canSpwan = canSpawn();
        System.out.println("I just spawned");
//        if (!canSpwan)
//            Globals.getInstance().game.spawnEnemies(1);
//            destroy();
//        }

        setImage(Globals.getInstance().getImage("SnakeHead"));
//        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
//        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);

        setX(300);
        setY(300);


        double direction = rnd.nextDouble() * 360;
        setRotate(direction);

        int speed = 0;
        heading = Utils.directionToVector(direction, speed);




    }


    @Override
    public void step() {
        double x = 0;
        double y = 0;
        Vector snake1Pos;
        double snake1posX = 0;
        double snake1posY = 0;
        snake1posX = Globals.getInstance().snakes.get(0).getHead().getPosition().x;
        snake1posY = Globals.getInstance().snakes.get(0).getHead().getPosition().y;
//        snake1Pos = Globals.getInstance().snakes.get(0).getHead().getPosition();
        double angle = 0;
//        angle = Math.toDegrees(Math.atan((this.getY()-snake1posY)/(this.getX()-snake1posX)));
        angle = Math.toDegrees(Math.atan(snake1posY)/(snake1posX));
        heading = Utils.directionToVector(angle, 1);

//        heading = new Point2D(getX() - snake1posX, getY() - snake1posY);
//        if (isOutOfBounds()) {
//            System.out.println("I am just destroyed");
//            destroy();
//        }
        System.out.println("x  " + getX());
        System.out.println("y  " + getY());
//        setX(getX() + heading.getX());
//        setY(getY() + heading.getY());
//        double vectorMag = Math.sqrt((getX() - snake1posX)*(getX() - snake1posX) + (getY() - snake1posY )*(getY() - snake1posY ));
//        setX(getX() + ((getX() - snake1posX)/vectorMag));
//        setY(getY() + ((getY() - snake1posY)/vectorMag));

        double vectorMag = Math.sqrt((snake1posX - getX())*(snake1posX - getX()) +  (snake1posY - getY())*(snake1posY - getY()));
        setX(getX() + (snake1posX - getX())/vectorMag);
        setY(getY() + (snake1posY - getY())/vectorMag);

;

//        System.out.println((getX() + (getX() - snake1posX)/vectorMag));


//        double moveToX = snake1posX - getX();
//        double mo
    }


    @Override
    public void apply(GameEntity entity) {
        if (entity instanceof SnakeHead) {
            System.out.println(getMessage());
            Globals.getInstance().game.spawnEnemies(1);
            destroy();

        }
    }

    @Override
    public String getMessage() {
        return (getDamage() + " damage");
    }


}


