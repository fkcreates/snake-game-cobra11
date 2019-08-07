package com.codecool.snake;

import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.eventhandler.InputHandler;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class Game extends Pane {
    private ArrayList<Snake> snakes = new ArrayList<>();
    private GameTimer gameTimer = new GameTimer();


    public Game() {
        Globals.getInstance().game = this;
        Globals.getInstance().display = new Display(this);
        Globals.getInstance().setupResources();

        init();
    }

    public void init() {
        spawnSnakes();
        spawnEnemies(4);
        spawnPowerUps(4);

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
        for(int i = 1; i <= 2; i++){
            snakes.add(new Snake((new Vec2d(i * 100d, 600d)), i));
        }
    }

    private void spawnEnemies(int numberOfEnemies) {
        for(int i = 0; i < numberOfEnemies; ++i) new SimpleEnemy();
    }

    private void spawnPowerUps(int numberOfPowerUps) {
        for(int i = 0; i < numberOfPowerUps; ++i) new SimplePowerUp();
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


    private void displayGameOverPage(){
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

    public void restartGame(){
        snakes.clear();
        Globals.getInstance().display.clear();

        init();
        start();
    }

    public void checkGameOver() {
        int counter = 0;
        for(Snake snake: snakes) {
            if (snake.getHead().isOutOfBounds()){
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

}
