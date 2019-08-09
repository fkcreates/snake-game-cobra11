package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class Display {
    private Pane displayPane;
    private DelayedModificationList<GameEntity> gameObjects = new DelayedModificationList<>();

    public Display(Pane pane) {
        displayPane = pane;
    }

    public void add(GameEntity entity) {
        displayPane.getChildren().add(entity);
        gameObjects.add(entity);
    }

    public void remove(GameEntity entity) {
        displayPane.getChildren().remove(entity);
        gameObjects.remove(entity);
    }

    public List<GameEntity> getObjectList() {
        return gameObjects.getList();
    }

    public void frameFinished() {
        gameObjects.doPendingModifications();
    }

    public void updateSnakeHeadDrawPosition(GameEntity snakeHead) {
        displayPane.getChildren().remove(snakeHead);
        displayPane.getChildren().add(snakeHead);
    }

    public void clear() {
        displayPane.getChildren().clear();
        gameObjects.clear();
    }

    public void add(Button restartButton) {
        displayPane.getChildren().add(restartButton);
    }

    public void add(Text text) {
        displayPane.getChildren().add(text);
    }

    public void add(Label label){
        displayPane.getChildren().add(label);
    }
}
