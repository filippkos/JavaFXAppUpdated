package com.example.javafxapp.models;

import com.example.javafxapp.controllers.AppController;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.Date;

public class userTask {
    private String id;
    private String task;
    private String state;
    private Date startTime;
    private Date deadline;
    private Date completionTime;

    public userTask(String task, Date deadline) {
        this.task = task;
        this.deadline = deadline;
    }

    public userTask(String id, String task, String state, Date startTime, Date deadline) {
        this.id = id;
        this.task = task;
        this.state = state;
        this.startTime = startTime;
        this.deadline = deadline;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
