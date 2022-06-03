package com.example.javafxapp.models;

import com.example.javafxapp.controllers.AppController;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.time.LocalDateTime;
import java.util.Date;

public class userTask {
    private int id;
    private String task;
    private String state;
    private String startTime;
    private String deadline;
    private String completionTime;
    private boolean isInTime;
    private String cancellationTime;
    private String reasonForCancellation;
    private String timeLeft;

    public userTask(String task, String deadline) {
        this.task = task;
        this.deadline = deadline;
    }

    public userTask(int id, String task, String state, String startTime, String deadline, String completionTime, String cancellationTime, boolean isInTime, String reasonForCancellation, String timeLeft) {
        this.id = id;
        this.task = task;
        this.state = state;
        this.startTime = startTime;
        this.deadline = deadline;
        this.completionTime = completionTime;
        this.cancellationTime = cancellationTime;
        this.isInTime = isInTime;
        this.reasonForCancellation = reasonForCancellation;
        this.timeLeft = timeLeft;
    }

    public userTask(int id, String task) {
        this.id = id;
        this.task = task;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public String getCancellationTime() {
        return cancellationTime;
    }

    public void setCancellationTime(String cancellationTime) {
        this.cancellationTime = cancellationTime;
    }

    public boolean getIsInTime() {
        return isInTime;
    }

    public void setInTime(boolean inTime) {
        isInTime = inTime;
    }

    public String getReasonForCancellation() {
        return reasonForCancellation;
    }

    public void setReasonForCancellation(String reasonForCancellation) {
        this.reasonForCancellation = reasonForCancellation;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }
}
