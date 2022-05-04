package com.example.javafxapp.models;

import java.time.Period;
import java.util.Date;

public class Task {
    private String task;
    private Date currentTime;
    private Date deadline;
    private Date completionTime;
    private Date cancellationTime;
    private Period timeLeft;
    private boolean inTimeOrNot;

    public Task(String task, Date currentTime, Date deadline, Date completionTime, Date cancellationTime, boolean inTimeOrNot) {
        this.task = task;
        this.currentTime = currentTime;
        this.deadline = deadline;
        this.completionTime = completionTime;
        this.cancellationTime = cancellationTime;
        this.inTimeOrNot = inTimeOrNot;
    }

    public String getTask() {
        return task;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public Date getCancellationTime() {
        return cancellationTime;
    }

    public boolean isInTimeOrNot() {
        return inTimeOrNot;
    }

//    public Period getTimeLeft() {
//        return Period.between(deadline, currentTime);
//    }
}
