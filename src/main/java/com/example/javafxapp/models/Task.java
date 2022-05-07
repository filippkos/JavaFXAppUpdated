package com.example.javafxapp.models;

import javafx.scene.control.Button;

import java.util.Date;

public class Task {
    private String id;
    private String task;
    private Date currentTime;
    private Date deadline;
    private Button delete;

    public Task(String id, String task, Date currentTime, Date deadline, Button delete) {
        this.id = id;
        this.task = task;
        this.currentTime = currentTime;
        this.deadline = deadline;
        this.delete = delete;
    }

    public Task(String id, String task) {
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

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
