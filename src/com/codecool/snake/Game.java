package com.codecool.snake;

import com.codecool.snake.entities.enemies.Bump;
import com.codecool.snake.entities.enemies.Police;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.eventhandler.InputHandler;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class Game extends Pane {
    private ArrayList<Snake> snakes = new ArrayList<>();
    private GameTimer gameTimer = new GameTimer();
    private ArrayList<Police> polices = new ArrayList<>();


    public Game() {
        Globals.getInstance().game = this;
        Globals.getInstance().display = new Display(this);
        Globals.getInstance().setupResources();

        init();
    }

    public void init() {
        spawnSnakes();
        spawnBumps(0);
        spawnPowerUps(4);
        spawnPolice(0);
        spawnPolice(1);

        GameLoop gameLoop = new GameLoop(snakes);
        Globals.getInstance().setGameLoop(gameLoop);
        gameTimer.setup(gameLoop::step);
        gameTimer.play();
    }

    public void start() {
        setupInputHandling();
        Globals.getInstance().startGame();
    }

    private void spawnSnakes() {
        for (int i = 1; i <= 2; i++) {
            snakes.add(new Snake((new Vec2d(i * 100d, 600d)), i));
            Globals.getInstance().snakes = snakes;
        }
    }


    public void spawnEnemies(int numberOfEnemies) {
        for (int i = 0; i < numberOfEnemies; ++i) new SimpleEnemy();
    }

    public void spawnBumps(int numberOfBumps) {
        for (int i = 0; i < numberOfBumps; ++i) new Bump();
    }

    public void spawnPolice(int id) {
        if (id == 0) {
            polices.add(new Police(Globals.getInstance().snakes.get(0), "SnakeHead", 50));
            snakes.get(0).getHead().setChaser(polices.get(0));

        } else if(id == 1) {
            polices.add(new Police(Globals.getInstance().snakes.get(1), "SnakeHead", 50));
            snakes.get(1).getHead().setChaser(polices.get(1));
        }
        Globals.getInstance().polices = polices;

    }

    private void spawnPowerUps(int numberOfPowerUps) {
        for (int i = 0; i < numberOfPowerUps; ++i) new SimplePowerUp();
    }

    private void setupInputHandling() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> InputHandler.getInstance().setKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> InputHandler.getInstance().setKeyReleased(event.getCode()));
    }
}
