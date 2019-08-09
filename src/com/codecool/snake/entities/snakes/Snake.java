package com.codecool.snake.entities.snakes;

import com.codecool.snake.DelayedModificationList;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.eventhandler.InputHandler;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Snake implements Animatable {
    private static final float speed = 2;
    private int score;
    private int health = 100;
    private String bodyImageName;


    private int id;
    private SnakeHead head;
    private DelayedModificationList<GameEntity> body;
    private boolean areControlsChanging;
    private int skipSteps;
    private Text snakeHpText;


    public Snake(Vec2d position, int id, int score, String headImageName, String bodyImageName) {
        head = new SnakeHead(this, position, headImageName);
        body = new DelayedModificationList<>();
        this.bodyImageName = bodyImageName;
        this.id = id;

        addPart(4, bodyImageName);
        this.score = score;
    }

    public void step() {
        SnakeControl turnDir;

        if (skipSteps == 0) {
            if (areControlsChanging) {
                turnDir = getUserInput("D", "A", "RIGHT", "LEFT");
            } else {
                turnDir = getUserInput("A", "D", "LEFT", "RIGHT");
            }

            head.updateRotation(turnDir, speed);
            updateSnakeBodyHistory();
            body.doPendingModifications();
        } else {
            skipSteps--;
        }
    }

    private SnakeControl getUserInput(String keyToGoLeftFor1, String keyToGoRightFor1, String keyToGoLeftFor2, String keyToGoRightFor2) {
        SnakeControl turnDir = SnakeControl.INVALID;
        if (id == 1) {
            if (InputHandler.getInstance().isKeyPressed(KeyCode.valueOf(keyToGoLeftFor1)))
                turnDir = SnakeControl.TURN_LEFT;
            if (InputHandler.getInstance().isKeyPressed(KeyCode.valueOf(keyToGoRightFor1)))
                turnDir = SnakeControl.TURN_RIGHT;
        }
        if (id == 2) {
            if (InputHandler.getInstance().isKeyPressed(KeyCode.valueOf(keyToGoLeftFor2)))
                turnDir = SnakeControl.TURN_LEFT;
            if (InputHandler.getInstance().isKeyPressed(KeyCode.valueOf(keyToGoRightFor2)))
                turnDir = SnakeControl.TURN_RIGHT;
        }
        return turnDir;
    }

    public void addPart(int numParts, String bodyImageName) {
        GameEntity parent = getLastPart();
        Vec2d position = parent.getPosition();

        for (int i = 0; i < numParts; i++) {
            SnakeBody newBodyPart = new SnakeBody(this, position, bodyImageName);
            body.add(newBodyPart);
        }
        Globals.getInstance().display.updateSnakeHeadDrawPosition(head);
    }

    public void changeHealth(int diff) {
        if ((health - diff) < 0) {
            health = 0;
            this.getHead().getChaser().destroy();
        } else {
            health -= diff;
        }
    }

    private void updateSnakeBodyHistory() {
        GameEntity prev = head;
        for (GameEntity currentPart : body.getList()) {
            currentPart.setPosition(prev.getPosition());
            prev = currentPart;
        }
    }

    private GameEntity getLastPart() {
        GameEntity result = body.getLast();

        if (result != null) return result;
        return head;
    }

    public int getSnakeId() {
        return id;
    }

    public boolean getAreControlsChanging() {
        return areControlsChanging;
    }

    public void setAreControlsChanging(boolean isControlChanging) {
        areControlsChanging = isControlChanging;
    }

    public void setSkipSteps(int skipSteps) {
        this.skipSteps = skipSteps;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public DelayedModificationList<GameEntity> getBody() {
        return body;
    }

    public SnakeHead getHead() {
        return head;
    }

    public void displayHealth(double x, double y, int snakeHP, Color color) {
        snakeHpText = new Text();
        snakeHpText.setText(String.valueOf(snakeHP));
        snakeHpText.setX(x);
        snakeHpText.setY(y);
        snakeHpText.setFill(color);
        snakeHpText.setFont(Font.font("Verdana", FontWeight.BOLD, 45));
        Globals.getInstance().display.add(snakeHpText);
    }

    public void setSnakeHpText(int snakeHp) {
        snakeHpText.setText(String.valueOf(snakeHp));
    }

    public void setScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return this.score;
    }

    public String getBodyImageName() {
        return bodyImageName;
    }
}
