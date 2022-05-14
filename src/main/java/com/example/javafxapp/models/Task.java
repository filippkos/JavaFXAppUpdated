package com.example.javafxapp.models;

import com.example.javafxapp.controllers.AppController;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.Date;

public class Task {
    private String id;
    private String task;
    private String state;
    private Date currentTime;
    private Date deadline;
    private Button done;
    private Button cancel;
    private Button remove;
    private HBox edit;


    public Task(String task) {
        this.task = task;
    }



    public Task(String id, String task, String state, Date currentTime, Date deadline, Button done, Button cancel, Button remove) {
        this.id = id;
        this.task = task;
        this.state = state;
        this.currentTime = currentTime;
        this.deadline = deadline;
        this.done = done;
        this.cancel = cancel;
        this.remove = remove;
    }

    public Task(String id, String task, String state, HBox edit, Button done, Button cancel, Button remove) {
        this.id = id;
        this.task = task;
        this.state = state;
        this.edit = edit;
        this.done = done;
        this.cancel = cancel;
        this.remove = remove;

        edit.getChildren().addAll(done, cancel, remove);
        edit.setSpacing(5);
        handleButtonActions();

    }

    public final void handleButtonActions(){
        done.setOnAction(event ->{
            System.out.println("done");
        });
        cancel.setOnAction(event ->{
            System.out.println("cancel");
        });
        remove.setOnAction(event ->{
            System.out.println("remove");
        });
    }

    public Task(String id, String task, Button done, Button cancel, Button remove) {
        this.id = id;
        this.task = task;
        this.done = done;
        this.cancel = cancel;
        this.remove = remove;
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

    public Button getDone() {
        return done;
    }

    public void setDone(Button done) {
        this.done = done;
    }

    public Button getCancel() {
        return cancel;
    }

    public void setCancel(Button cancel) {
        this.cancel = cancel;
    }

    public HBox getEdit() {
        return edit;
    }

    public void setEdit(HBox edit) {
        this.edit = edit;
    }

    public Button getRemove() {
        return remove;
    }

    public void setRemove(Button remove) {
        this.remove = remove;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
