package com.codecool.snake;


import com.codecool.snake.entities.enemies.Bump;
import com.codecool.snake.entities.enemies.Police;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.*;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.eventhandler.InputHandler;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

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

        spawnBumps(5);
        spawnPowerUps(4);
        spawnPolice();

//        spawnEnemies(4);



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



    public void spawnBumps(int numberOfBumps) {
        for (int i = 0; i < numberOfBumps; ++i) new Bump();
    }

    public void spawnPolice() {
        for (Snake snake : snakes) {
            Police police = new Police(snake, "SnakeHead", 50);
            System.out.println(snake);
            polices.add(police);
            snake.getHead().setChaser(police);
        }
    }



    public void spawnEnemies(int numberOfEnemies) {
        for (int i = 0; i < numberOfEnemies; ++i) new SimpleEnemy();
    }

    private boolean isSnakesControlsChanged() {
        for (Snake snake : snakes) {
            if (snake.getAreControlsChanging() && snake.getHealth() > 0) {
                return true;
            }
        }
        return false;
    }

    public void spawnPowerUps(int numberOfPowerUps) {
        Random rnd = new Random();

        for (int i = 0; i < numberOfPowerUps; i++) {
            int randomNumber = rnd.nextInt(100);
            if (randomNumber < 10) {
                if (isSnakesControlsChanged()) {
                    new DismissChangeControlPowerUp();
                } else {
                    new SimplePowerUp();
                }
            } else if (randomNumber < 20) {
                if (isBothSnakesControlsChanged()) {
                    new DismissChangeControlPowerUp();
                } else {
                    new ChangeControlPowerUp();
                }
            } else if (randomNumber < 30) {
                new HealthPowerUp();
            } else if (randomNumber < 45) {
                if (snakes.get(0).getHealth() > 0 && snakes.get(1).getHealth() > 0) {
                    new StopPowerUp();
                } else {
                    new SimplePowerUp();
                }
            } else {
                new SimplePowerUp();
            }
        }

    }

    private void setupInputHandling() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> InputHandler.getInstance().setKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> InputHandler.getInstance().setKeyReleased(event.getCode()));
    }

    public ArrayList<Snake> getSnakes() {
        return snakes;
    }

    private void createRestartButton() {
        Button restartButton = new Button("RESTART");
        restartButton.setLayoutX(500);
        restartButton.setLayoutY(350);
        Globals.getInstance().display.add(restartButton);
        restartButton.setOnMouseClicked(event -> {
            restartGame();
        });
    }

    private void displayGameOverPage() {
        Globals.getInstance().display.clear();
        displayScores();
        createRestartButton();
    }

    private void displayScores() {
        //TODO
        //ide jon a score (snake hossz) kiiras, meg hogy ki nyert
        //javafx fincsiseg
        //FRUZSI
    }

    public void restartGame() {
        snakes.clear();
        Globals.getInstance().display.clear();

        init();
        start();
    }

    public void checkGameOver() {
        int counter = 0;
        for (Snake snake : snakes) {
            if (snake.getHead().isOutOfBounds()) {
                snake.setHealth(0);
                counter += 1;
            } else if (snake.getHealth() <= 0) {
                counter += 1;
            }
        }
        if (counter == 2) {
            Globals.getInstance().stopGame();
            displayGameOverPage();
        }
    }

    private boolean isBothSnakesControlsChanged() {
        Snake snake1 = snakes.get(0);
        Snake snake2 = snakes.get(1);
        return snake1.getAreControlsChanging() && snake2.getAreControlsChanging();
    }
}
