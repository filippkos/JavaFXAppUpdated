package com.example.javafxapp.models;

public enum TaskState {

    CURRENT("current"),
    DONE("done"),
    CANCELLED("cancelled");

    private String title;

    TaskState(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
