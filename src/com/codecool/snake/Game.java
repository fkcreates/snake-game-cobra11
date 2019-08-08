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
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import java.util.ArrayList;
import javafx.scene.text.Font;
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
        snakes.add(new Snake((new Vec2d(100d, 600d)), 1, 4, "SnakeHead1", "SnakeBody1"));
        snakes.add(new Snake((new Vec2d(700d, 600d)), 2, 4, "SnakeHead2", "SnakeBody2"));
    }

    public void spawnBumps(int numberOfBumps) {
        for (int i = 0; i < numberOfBumps; ++i) new Bump();
    }

    public void spawnPolice() {
        for (Snake snake : snakes) {
            Police police = new Police(snake, "Police", 30);
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
        restartButton.setLayoutX(425);
        restartButton.setLayoutY(340);
        restartButton.setPrefSize(150, 60);
        restartButton.setStyle("-fx-font-family: 'Chalkboard'; -fx-background-color: #65A655; -fx-font-size: 20px");

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
        Font myFont = new Font("Chalkboard", 25);

        Label score1 = new Label("Player 1, your score is: " + snakes.get(0).getScore());
        score1.setStyle("-fx-text-fill: #3B529D");
        score1.setLayoutX(350);
        score1.setLayoutY(100);
        score1.setFont(myFont);

        Label score2 = new Label("Player 2, your score is: " + snakes.get(1).getScore());
        score2.setStyle("-fx-text-fill: #E976CA");
        score2.setLayoutX(350);
        score2.setLayoutY(150);
        score2.setFont(myFont);

        Label winnerLabel;

        if(snakes.get(0).getScore() > snakes.get(1).getScore()){
            winnerLabel = new Label("The winner is: Player 1");
        } else if (snakes.get(0).getScore() < snakes.get(1).getScore()){
            winnerLabel = new Label("The winner is: Player 2");
        } else {
            winnerLabel = new Label("This is a tie. Everybody wins!");
        }

        winnerLabel.setStyle("-fx-text-fill: #65A655");
        winnerLabel.setLayoutX(350);
        winnerLabel.setLayoutY(250);
        winnerLabel.setFont(myFont);

        Globals.getInstance().display.add(score1);
        Globals.getInstance().display.add(score2);
        Globals.getInstance().display.add(winnerLabel);
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

    public void setTableBackground(Image tableBackground) {
        setBackground(new Background(new BackgroundImage(tableBackground,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
    }

    private boolean isBothSnakesControlsChanged() {
        Snake snake1 = snakes.get(0);
        Snake snake2 = snakes.get(1);
        return snake1.getAreControlsChanging() && snake2.getAreControlsChanging();
    }
}
