package com.example.javafxapp.controllers;

import com.example.javafxapp.models.DatabaseHandler;
import com.example.javafxapp.models.Task;
import com.example.javafxapp.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class AppController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addTaskButton;

    @FXML
    private Tab currentTab;

    @FXML
    private AnchorPane currentTabAnchorPane;

    @FXML
    private TableView<Task> currentTable;

    @FXML
    private TableColumn<Task, String> currentTasksColumn;

    @FXML
    private TableColumn<Task, Date> deadlineColumn;

    @FXML
    private TableColumn<Task, Button> editColumn;

    @FXML
    private Label greetingsText;

    @FXML
    private TableColumn<Task, String> currentIdColumn;

    @FXML
    private ImageView imageButtonGuitar;

    @FXML
    private Button loginSignOutButton;

    @FXML
    private TableColumn<Task, Boolean> onTimeOrNotColumn;

    @FXML
    private TableColumn<?, ?> reasonColumn;

    @FXML
    private TableColumn<Task, Date> startTimeColumn;

    ObservableList<Task> tasks = FXCollections.observableArrayList();

    /**
     * Инициализация контроллера
     */
    @FXML
    void initialize() {
        greetingsText.setText("Hello, " + User.getCurrentFirstName() + "!");
        assert imageButtonGuitar != null : "fx:id=\"imageButtonGuitar\" was not injected: check your FXML file 'app.fxml'.";
        loginSignOutButton.setOnAction(event ->{
            openNewScene("/com/example/javafxapp/hello-view.fxml");
        });


        initCols();
        currentTable.setItems(tasks);

        addTaskButton.setOnAction(event ->{
            //addNewTaskToTable();
        });
    }

    /**
     * Надо, чтобы данные загружались из БД
     */
    private void initCols(){
        getAllTasks();
        currentIdColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("id"));
        currentTasksColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));

        editableCols();
    }

    /**
     * Достать все таски из ДБ и положить в ТАСКС лист
     */
    private void getAllTasks() {
        ResultSet rs = new DatabaseHandler().getTaskTable();
            try {
                while (rs.next()) {
                    tasks.add(new Task(rs.getString("idtask"), rs.getString("task")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    /**
     * Надо реализовать редактирование записи с апдейтом в БД
     */
    private void editableCols() {
        currentTasksColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        currentTasksColumn.setOnEditCommit(event ->{
            String id = event.getTableView().getItems().get(event.getTablePosition().getRow()).getId();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setTask(event.getNewValue());

            /** метод редактирования */
            editTaskInTable(id, event.getNewValue());
        });
        currentTable.setEditable(true);
    }


    private void openNewScene(String window) {
        loginSignOutButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Надо загружать ТАСК в БД
     */
    private void addNewTaskToTable(String id, String text) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        Task task = new Task(id, text);
        dbHandler.addTaskToTable(task);
    }

    /**
     * Надо редактировать определённый ТАСК в БД
     */
    private void editTaskInTable(String id, String newTask) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        Task task = new Task(id, newTask);
        dbHandler.editTask(task);
    }

}
