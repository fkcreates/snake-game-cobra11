package com.codecool.snake;

import com.codecool.snake.entities.enemies.Police;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.resources.Resources;
import javafx.scene.image.Image;

import java.util.ArrayList;

// class for holding all static stuff
public class Globals {
    private static Globals instance = null;

    public static final double WINDOW_WIDTH = 1000;
    public static final double WINDOW_HEIGHT = 700;

    public Display display;
    public Game game;
    public ArrayList<Snake> snakes;
    public ArrayList<Police> polices;



    private GameLoop gameLoop;
    private Resources resources;


    public static Globals getInstance() {
        if(instance == null) instance = new Globals();
        return instance;
    }

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void setupResources() {
        resources = new Resources();
        resources.addImage("SnakeHead", new Image("snake_head.png"));
        resources.addImage("SnakeBody", new Image("snake_body.png"));
        resources.addImage("SimpleEnemy", new Image("simple_enemy.png"));
        resources.addImage("PowerUpBerry", new Image("powerup_berry.png"));
        resources.addImage("PowerUpChangeControl", new Image("powerup_change_controls.png"));
        resources.addImage("PowerUpClearChangeControl", new Image("powerup_clear_change_controls.png"));
        resources.addImage("StopPowerUp", new Image("powerup_stop.png"));
        resources.addImage("HealthPowerUp", new Image("powerup_medkit.png"));

    }

    public Image getImage(String name) { return resources.getImage(name); }

    public void startGame() { gameLoop.start(); }

    public void stopGame() { gameLoop.stop(); }

    private Globals() {
        // singleton needs the class to have private constructor
    }
}
