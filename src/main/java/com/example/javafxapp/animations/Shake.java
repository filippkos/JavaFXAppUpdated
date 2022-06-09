package com.example.javafxapp.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private TranslateTransition tt;

    public Shake (Node node) {
        tt = new TranslateTransition(Duration.millis(70), node);
        tt.setAutoReverse(true);
        tt.setFromX(0f);
        tt.setByX(10f);
        tt.setCycleCount(4);

    }

    public void playAnim() {
        tt.playFromStart();
    }
}
