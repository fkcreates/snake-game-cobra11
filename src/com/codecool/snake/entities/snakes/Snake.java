package com.codecool.snake.entities.snakes;

import com.codecool.snake.DelayedModificationList;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.eventhandler.InputHandler;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.input.KeyCode;


public class Snake implements Animatable {
    private static final float speed = 2;
    private int health = 100;
    private int id;
    private SnakeHead head;
    private DelayedModificationList<GameEntity> body;
    private boolean areControlsChanging;
    private int skipSteps;

    public Snake(Vec2d position, int id) {
        head = new SnakeHead(this, position);
        body = new DelayedModificationList<>();
        this.id = id;

        addPart(4);
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

    public void addPart(int numParts) {
        GameEntity parent = getLastPart();
        Vec2d position = parent.getPosition();

        for (int i = 0; i < numParts; i++) {
            SnakeBody newBodyPart = new SnakeBody(this, position);
            body.add(newBodyPart);
        }
        Globals.getInstance().display.updateSnakeHeadDrawPosition(head);
    }

    public void changeHealth(int diff) {
        health -= diff;
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

    public DelayedModificationList<GameEntity> getBody(){
        return body;
    }

    public SnakeHead getHead() {
        return head;
    }
}
