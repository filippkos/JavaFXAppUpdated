package com.example.javafxapp.controllers;

import com.example.javafxapp.models.DatabaseHandler;
import com.example.javafxapp.models.TaskState;
import com.example.javafxapp.models.User;
import com.example.javafxapp.models.userTask;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.stage.StageStyle;

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
    private TableView<userTask> cancelledTable;

    @FXML
    private TableColumn<userTask, String> cancelledTasksColumn;

    @FXML
    private TableColumn<userTask, String> completedTasksColumn;

    @FXML
    private TableColumn<?, ?> CompletedDeadlineColumn;

    @FXML
    private TableColumn<?, ?> completionTimeColumn;

    @FXML
    private TableColumn<userTask, String> currentIdColumn;

    @FXML
    private Tab currentTab;

    @FXML
    private AnchorPane currentTabAnchorPane;

    @FXML
    private TableView<userTask> currentTable;

    @FXML
    private TableColumn<userTask, String> currentTasksColumn;

    @FXML
    private TableColumn<?, ?> deadlineColumn;

    @FXML
    private Tab doneTab;

    @FXML
    private AnchorPane doneTabAnchorPane;

    @FXML
    private TableView<userTask> doneTable;

    @FXML
    private TableColumn<userTask, userTask> editColumn;

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


    ObservableList<userTask> currentUserTasks = FXCollections.observableArrayList();
    ObservableList<userTask> doneUserTasks = FXCollections.observableArrayList();
    ObservableList<userTask> cancelledUserTasks = FXCollections.observableArrayList();

    userTask userTask = null;

    /**
     * Инициализация контроллера
     */
    @FXML
    void initialize() {
        greetingsText.setText("Hello, " + User.getCurrentFirstName() + "!");
        loginSignOutButton.setOnAction(event -> {
            openNewScene("/com/example/javafxapp/hello-view.fxml");
        });
        getAllTasks();
        initCols();
        editableCols();

        addTaskButton.setOnAction(event -> {
            Optional<String> result = getPopupDialogueWindow().showAndWait();
            if (result.isPresent()) {
                addNewTaskToDbTable(result.get());
                initCols();
                getAllTasks();
            }
        });
    }

    public class TaskActionCell extends TableCell<userTask, userTask> {

        private final HBox graphic;

        public TaskActionCell() {
            Button done = new Button("Done");
            Button cancel = new Button("Cancel");
            Button remove = new Button("Remove");
            graphic = new HBox(5, done, cancel, remove);

            done.setOnAction(event -> {
                userTask task = getItem();
                changeStatus(task, TaskState.DONE.getTitle());
                initCols();
                getAllTasks();
            });

            cancel.setOnAction(event -> {
                userTask task = getItem();
                changeStatus(task, TaskState.CANCELLED.getTitle());
                initCols();
                getAllTasks();
            });

            remove.setOnAction(event -> {
                userTask task = getItem();
                System.out.println("remove" + task.getTask());
                removeTaskFromDbTable(task);
                initCols();
                getAllTasks();
            });
        }

        private void changeStatus(userTask task, String state) {
            DatabaseHandler dbHandler = new DatabaseHandler();
            dbHandler.editTaskStateInDb(task, state);
        }

        @Override
        protected void updateItem(userTask task, boolean empty) {
            super.updateItem(task, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(graphic);
            }
        }
    }

    /**
     * Создаёт всплывающее окно с текстовым полем для ввода нового ТАСКа
     */
    private TextInputDialog getPopupDialogueWindow(){
        TextInputDialog newInputDialogue = new TextInputDialog();
        newInputDialogue.setTitle("New task creation");
        newInputDialogue.setGraphic(null);
        newInputDialogue.setHeaderText(null);
        newInputDialogue.setContentText("Text: ");
        return newInputDialogue;
    }

    /**
     * Надо, чтобы данные загружались из БД
     */
    private void initCols(){
        currentIdColumn.setCellValueFactory(new PropertyValueFactory<userTask, String>("id"));
        currentTasksColumn.setCellValueFactory(new PropertyValueFactory<userTask, String>("task"));

        completedTasksColumn.setCellValueFactory(new PropertyValueFactory<userTask, String>("task"));
        cancelledTasksColumn.setCellValueFactory(new PropertyValueFactory<userTask, String>("task"));

        editColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        editColumn.setCellFactory(col -> new TaskActionCell());

        currentTable.getItems().clear();
        doneTable.getItems().clear();
        cancelledTable.getItems().clear();

        currentTable.setItems(currentUserTasks);
        doneTable.setItems(doneUserTasks);
        cancelledTable.setItems(cancelledUserTasks);
    }

    /**
     * Достать все таски из ДБ и положить в ТАСКС лист
     */
    private void getAllTasks() {
        ResultSet rs = new DatabaseHandler().getTaskTableFromDb();
            try {
                while (rs.next()) {
                    if(rs.getString("state").equals("current")) {
                        currentUserTasks.add(new userTask(rs.getString("idtask"),
                                rs.getString("task"),
                                rs.getString("state")));
                    }
                    if(rs.getString("state").equals("done")) {
                        doneUserTasks.add(new userTask(rs.getString("idtask"),
                                rs.getString("task"),
                                rs.getString("state")));
                    }
                    if(rs.getString("state").equals("cancelled")) {
                        cancelledUserTasks.add(new userTask(rs.getString("idtask"),
                                rs.getString("task"),
                                rs.getString("state")));
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
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setTask(event.getNewValue());

            /** метод редактирования */
            editTaskInDbTable(id, event.getNewValue());
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
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    /**
     * Надо загружать ТАСК в БД
     */
    private void addNewTaskToDbTable(String text) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        userTask userTask = new userTask(text);
        dbHandler.addTaskToDb(userTask);
    }

    /**
     * Надо редактировать определённый ТАСК в БД
     */
    private void editTaskInDbTable(String id, String newTask) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        userTask userTask = new userTask(id, newTask);
        dbHandler.editTaskInDb(userTask);
    }

    /**
     * Надо удалить определённый ТАСК из БД
     */
    private void removeTaskFromDbTable(userTask userTask) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.removeTaskFromDb(userTask);
    }
}
