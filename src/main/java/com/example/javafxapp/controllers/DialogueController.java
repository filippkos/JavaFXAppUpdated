package com.example.javafxapp.controllers;

import com.example.javafxapp.models.DatabaseHandler;
import com.example.javafxapp.models.TaskState;
import com.example.javafxapp.models.userTask;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tornadofx.control.DateTimePicker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.Date;

public class DialogueController {

    @FXML
    private DateTimePicker inputNewTaskDatePicker;

    @FXML
    private TextField inputNewTaskTextField;

    @FXML
    private Button inputNewTaskOkButton;

    @FXML
    private Button inputNewTaskCancelButton;

    @FXML
    void initialize() {
        inputNewTaskOkButton.setOnAction(event -> {
            LocalDateTime localDateTime = inputNewTaskDatePicker.getDateTimeValue();
            System.out.println(localDateTime);
            addNewTaskToDbTable(inputNewTaskTextField.getText(), localDateTime);
            ((Node) event.getSource()).getScene().getWindow().hide();
        });

        inputNewTaskCancelButton.setOnAction(event -> {
            ((Node) event.getSource()).getScene().getWindow().hide();
        });
    }


    /**
     * Надо загружать ТАСК в БД
     */
    private void addNewTaskToDbTable(String text, LocalDateTime deadline) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        userTask userTask = new userTask(text, deadline);
        dbHandler.addTaskToDb(userTask);
        addTheLastUserTaskToList();
    }


    private void addTheLastUserTaskToList() {
        ResultSet rs = new DatabaseHandler().getTheLastTaskFromDb();
        try {
            while (rs.next()) {
                if (rs.getString("state").equals(TaskState.CURRENT.getTitle())) {
                    AppController.currentUserTasks.add(new userTask(rs.getString("idtask"),
                            rs.getString("task"),
                            rs.getString("state"),
                            rs.getDate("startdate").toLocalDate().atTime(LocalTime.now()),
                            rs.getTimestamp("deadline").toLocalDateTime(),
                            null,
                            null,
                            rs.getBoolean("isintime"),
                            null,
                            null));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
