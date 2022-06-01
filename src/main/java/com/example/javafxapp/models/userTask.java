package com.example.javafxapp.models;

import com.example.javafxapp.controllers.AppController;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.time.LocalDateTime;
import java.util.Date;

public class userTask {
    private String id;
    private String task;
    private String state;
    private LocalDateTime startTime;
    private LocalDateTime deadline;
    private LocalDateTime completionTime;
    private boolean isInTime;
    private LocalDateTime cancellationTime;
    private String reasonForCancellation;

    public userTask(String task, LocalDateTime deadline) {
        this.task = task;
        this.deadline = deadline;
    }

    public userTask(String id, String task, String state, LocalDateTime startTime, LocalDateTime deadline, LocalDateTime completionTime, LocalDateTime cancellationTime, boolean isInTime, String reasonForCancellation) {
        this.id = id;
        this.task = task;
        this.state = state;
        this.startTime = startTime;
        this.deadline = deadline;
        this.completionTime = completionTime;
        this.cancellationTime = cancellationTime;
        this.isInTime = isInTime;
        this.reasonForCancellation = reasonForCancellation;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(LocalDateTime completionTime) {
        this.completionTime = completionTime;
    }

    public LocalDateTime getCancellationTime() {
        return cancellationTime;
    }

    public void setCancellationTime(LocalDateTime cancellationTime) {
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
}
