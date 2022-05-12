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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addTaskButton;

    @FXML
    private AnchorPane appPane;

    @FXML
    private Tab canceledTab;

    @FXML
    private AnchorPane canceledTabAnchorPane;

    @FXML
    private TableColumn<?, ?> cancellationTimeColumn;

    @FXML
    private TableView<Task> cancelledTable;

    @FXML
    private TableColumn<Task, String> cancelledTasksColumn;

    @FXML
    private TableColumn<Task, String> completedTasksColumn;

    @FXML
    private TableColumn<?, ?> completionTimeColumn;

    @FXML
    private TableColumn<Task, String> currentIdColumn;

    @FXML
    private Tab currentTab;

    @FXML
    private AnchorPane currentTabAnchorPane;

    @FXML
    private TableView<Task> currentTable;

    @FXML
    private TableColumn<Task, String> currentTasksColumn;

    @FXML
    private TableColumn<?, ?> deadlineColumn;

    @FXML
    private Tab doneTab;

    @FXML
    private AnchorPane doneTabAnchorPane;

    @FXML
    private TableView<Task> doneTable;

    @FXML
    private TableColumn<Task, HBox> editColumn;

    @FXML
    private Label greetingsText;

    @FXML
    private Button loginSignOutButton;

    @FXML
    private TableColumn<?, ?> onTimeOrNotColumn;

    @FXML
    private TableColumn<?, ?> reasonColumn;

    @FXML
    private TableColumn<?, ?> startTimeColumn;

    @FXML
    private TableColumn<?, ?> timeLeftColumn;


    ObservableList<Task> currentTasks = FXCollections.observableArrayList();
    ObservableList<Task> doneTasks = FXCollections.observableArrayList();
    ObservableList<Task> cancelledTasks = FXCollections.observableArrayList();


    class myThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                currentTabAnchorPane.requestLayout();
            }
        }
    }

    /**
     * Инициализация контроллера
     */
    @FXML
    void initialize() {
        greetingsText.setText("Hello, " + User.getCurrentFirstName() + "!");
        loginSignOutButton.setOnAction(event ->{
            openNewScene("/com/example/javafxapp/hello-view.fxml");
        });

        getAllTasks();
        initCols();
        editableCols();
        currentTable.setItems(currentTasks);
        doneTable.setItems(doneTasks);
        cancelledTable.setItems(cancelledTasks);
        Thread newTh = new Thread(new myThread());
        newTh.start();

        addTaskButton.setOnAction(event ->{
            TextInputDialog newTaskDial = new TextInputDialog();
            newTaskDial.setTitle("New task creation");
            newTaskDial.setGraphic(null);
            newTaskDial.setHeaderText(null);
            newTaskDial.setContentText("Text: ");
            Optional<String> result = newTaskDial.showAndWait();
            if (result.isPresent()) {
                addNewTaskToDbTable(result.get());
            } else {
                newTaskDial.close();
            }
        });
    }


    /**
     * Надо, чтобы данные загружались из БД
     */
    private void initCols(){
        currentIdColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("id"));
        currentTasksColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
        editColumn.setCellValueFactory(new PropertyValueFactory<Task, HBox>("edit"));
        completedTasksColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
        cancelledTasksColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
    }

    /**
     * Достать все таски из ДБ и положить в ТАСКС лист
     */
    private void getAllTasks() {
        ResultSet rs = new DatabaseHandler().getTaskTableFromDb();
            try {
                while (rs.next()) {
                    if(rs.getString("state").equals("current")) {
                        currentTasks.add(new Task(rs.getString("idtask"),
                                rs.getString("task"),
                                rs.getString("state"),
                                new HBox(),
                                new Button("done"),
                                new Button("cancel"),
                                new Button("remove")));
                    }
                    if(rs.getString("state").equals("done")) {
                        doneTasks.add(new Task(rs.getString("idtask"),
                                rs.getString("task"),
                                rs.getString("state"),
                                new HBox(),
                                new Button("done"),
                                new Button("cancel"),
                                new Button("remove")));
                    }
                    if(rs.getString("state").equals("cancelled")) {
                        cancelledTasks.add(new Task(rs.getString("idtask"),
                                rs.getString("task"),
                                rs.getString("state"),
                                new HBox(),
                                new Button("done"),
                                new Button("cancel"),
                                new Button("remove")));
                    }
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
            Button done = event.getTableView().getItems().get(event.getTablePosition().getRow()).getDone();
            Button cancel = event.getTableView().getItems().get(event.getTablePosition().getRow()).getCancel();
            Button remove = event.getTableView().getItems().get(event.getTablePosition().getRow()).getRemove();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setTask(event.getNewValue());


            /** метод редактирования */
            editTaskInDbTable(id, event.getNewValue(), done, cancel, remove);
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
    private void addNewTaskToDbTable(String text) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        Task task = new Task(text);
        dbHandler.addTaskToDb(task);
    }

    /**
     * Надо редактировать определённый ТАСК в БД
     */
    private void editTaskInDbTable(String id, String newTask, Button done, Button cancel, Button remove) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        Task task = new Task(id, newTask, done, cancel, remove);
        dbHandler.editTaskInDb(task);
    }

    /**
     * Надо удалить определённый ТАСК из БД
     */
    private void removeTaskFromDbTable(Task task) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.removeTaskFromDb(task);
    }

}
