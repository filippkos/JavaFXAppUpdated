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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
    private TableColumn<com.example.javafxapp.models.userTask, Date> cancellationTimeColumn;

    @FXML
    private TableView<userTask> cancelledTable;

    @FXML
    private TableColumn<userTask, String> cancelledTasksColumn;

    @FXML
    private TableColumn<userTask, String> completedTasksColumn;

    @FXML
    private TableColumn<com.example.javafxapp.models.userTask, Date> completedDeadlineColumn;

    @FXML
    private TableColumn<com.example.javafxapp.models.userTask, Date> completionTimeColumn;

    @FXML
    private TableColumn<userTask, String> currentIdColumn;

    @FXML
    private TableView<userTask> currentTable;

    @FXML
    private TableColumn<userTask, String> currentTasksColumn;

    @FXML
    private TableColumn<com.example.javafxapp.models.userTask, Date> deadlineColumn;

    @FXML
    private TableView<userTask> doneTable;

    @FXML
    private TableColumn<userTask, userTask> editColumn;

    @FXML
    private Label greetingsText;

    @FXML
    private Button loginSignOutButton;

    @FXML
    private TableColumn<userTask, Boolean> isInTimeColumn;

    @FXML
    private TableColumn<com.example.javafxapp.models.userTask, String> reasonColumn;

    @FXML
    private TableColumn<com.example.javafxapp.models.userTask, Date> startTimeColumn;

    @FXML
    private TableColumn<com.example.javafxapp.models.userTask, String> timeLeftColumn;


    static ObservableList<userTask> currentUserTasks = FXCollections.observableArrayList();
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
            openNewScene("/com/example/javafxapp/helloView.fxml", true);
        });
        getAllTasksFromDbToList();
        initCols();
        editableCols();

        addTaskButton.setOnAction(event -> {
            openNewScene("/com/example/javafxapp/addNewTaskDialogue.fxml", false);

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
                getAllTasksFromDbToList();
            });

            cancel.setOnAction(event -> {
                userTask task = getItem();

                Optional<String> result = getPopupDialogueWindow().showAndWait();
                if (result.isPresent()) {
                    task.setReasonForCancellation(result.get());
                } else {
                    ((Node) event.getSource()).getScene().getWindow().hide();
                }
                changeStatus(task, TaskState.CANCELLED.getTitle());
                initCols();
                getAllTasksFromDbToList();


            });

            remove.setOnAction(event -> {
                userTask task = getItem();
                System.out.println("remove" + task.getTask());
                removeTaskFromDbTable(task);
                initCols();
                getAllTasksFromDbToList();
            });
        }


        private TextInputDialog getPopupDialogueWindow(){
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Reason for cancellation");
            textInputDialog.setGraphic(null);
            textInputDialog.setHeaderText("What is the reason for the cancellation?");
            textInputDialog.setContentText(null);
            return textInputDialog;
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
     * Надо, чтобы данные загружались из БД
     */
    private void initCols() {




        currentIdColumn.setCellValueFactory(new PropertyValueFactory<userTask, String>("id"));
        currentTasksColumn.setCellValueFactory(new PropertyValueFactory<userTask, String>("task"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<userTask, Date>("startTime"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<userTask, Date>("deadline"));
        timeLeftColumn.setCellValueFactory(new PropertyValueFactory<userTask, String>("timeLeft"));


        completedTasksColumn.setCellValueFactory(new PropertyValueFactory<userTask, String>("task"));
        completedDeadlineColumn.setCellValueFactory(new PropertyValueFactory<userTask, Date>("deadline"));
        completionTimeColumn.setCellValueFactory(new PropertyValueFactory<userTask, Date>("completionTime"));
        isInTimeColumn.setCellValueFactory(new PropertyValueFactory<userTask, Boolean>("isInTime"));
        cancelledTasksColumn.setCellValueFactory(new PropertyValueFactory<userTask, String>("task"));
        cancellationTimeColumn.setCellValueFactory(new PropertyValueFactory<userTask, Date>("cancellationTime"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<userTask, String>("reasonForCancellation"));


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
    private void getAllTasksFromDbToList() {
        ResultSet rs = new DatabaseHandler().getTaskTableFromDb();
        try {
            while (rs.next()) {
                if (rs.getString("state").equals(TaskState.CURRENT.getTitle())) {
                    currentUserTasks.add(new userTask(rs.getString("idtask"),
                            rs.getString("task"),
                            rs.getString("state"),
                            rs.getTimestamp("startdate").toLocalDateTime(),
                            rs.getTimestamp("deadline").toLocalDateTime(),
                            null,
                            null,
                            rs.getBoolean("isintime"),
                            null,
                            getRemainingTime(rs.getTimestamp("deadline").toLocalDateTime())));
                }
                if (rs.getString("state").equals(TaskState.DONE.getTitle())) {
                    doneUserTasks.add(new userTask(rs.getString("idtask"),
                            rs.getString("task"),
                            rs.getString("state"),
                            rs.getTimestamp("startdate").toLocalDateTime(),
                            rs.getTimestamp("deadline").toLocalDateTime(),
                            rs.getTimestamp("completiontime").toLocalDateTime(),
                            null,
                            rs.getBoolean("isintime"),
                            null,
                            null));
                }
                if (rs.getString("state").equals(TaskState.CANCELLED.getTitle())) {
                    cancelledUserTasks.add(new userTask(rs.getString("idtask"),
                            rs.getString("task"),
                            rs.getString("state"),
                            rs.getTimestamp("startdate").toLocalDateTime(),
                            rs.getTimestamp("deadline").toLocalDateTime(),
                            null,
                            rs.getTimestamp("cancellationtime").toLocalDateTime(),
                            rs.getBoolean("isintime"),
                            rs.getString("reasonforcancellation"),
                            null));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getRemainingTime(LocalDateTime deadline) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime tempDateTime = LocalDateTime.from( currentTime );

        long years = tempDateTime.until(deadline, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears( years );
        String yearsString = years + " y, ";
        if (years == 0) {
            yearsString = "";
        }
        long months = tempDateTime.until(deadline, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths( months );
        String monthString = months + " m, ";
        if (months == 0) {
            monthString = "";
        }
        long days = tempDateTime.until(deadline, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays( days );
        String daysString = days + " d, ";
        if (days == 0) {
            daysString = "";
        }
        long hours = tempDateTime.until(deadline, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours( hours );
        String hoursString = hours + " h, ";
        if (hours == 0) {
            hoursString = "";
        }
        long minutes = tempDateTime.until(deadline, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);
        String minutesString = minutes + " min, ";
        if (minutes == 0) {
            minutesString = "";
        }
        long seconds = tempDateTime.until( deadline, ChronoUnit.SECONDS);
        String secondsString = seconds + " s";
        if (seconds < 0) {
            return "";
        }
        return yearsString + monthString + daysString + hoursString + minutesString + secondsString;
    }

    /**
     * Надо реализовать редактирование записи с апдейтом в БД
     */
    private void editableCols() {
        currentTasksColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        currentTasksColumn.setOnEditCommit(event -> {
            String id = event.getTableView().getItems().get(event.getTablePosition().getRow()).getId();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setTask(event.getNewValue());

            /** метод редактирования */
            editTaskInDbTable(id, event.getNewValue());
        });
        currentTable.setEditable(true);
    }

    private void openNewScene(String window, boolean hidePrevious) {
        if(hidePrevious) {
            loginSignOutButton.getScene().getWindow().hide();
        }

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
