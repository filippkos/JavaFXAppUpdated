package com.example.javafxapp.models;

import com.example.javafxapp.controllers.AppController;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.Date;

public class userTask {
    private String id;
    private String task;
    private String state;



    public userTask(String task) {
        this.task = task;
    }

    public userTask(String id, String task, String state) {
        this.id = id;
        this.task = task;
        this.state = state;

    }


    public userTask(String id, String task) {
        this.id = id;
        this.task = task;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
