package com.example.todoapp.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Prompt {
    private TranslateTransition translateTransition;

    public Prompt(Node node) {
        translateTransition =
                new TranslateTransition(Duration.millis(50), node);
        translateTransition.setFromX(0f);
        translateTransition.setByX(20f);
        translateTransition.setCycleCount(6);
        translateTransition.setAutoReverse(true);
    }

    public void show() {
        translateTransition.playFromStart();
    }
}


