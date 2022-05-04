package com.example.javafxapp.controllers;

import com.example.javafxapp.models.Task;
import com.example.javafxapp.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AppController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private TableColumn<?, ?> timeLeftColumn;

    @FXML
    private TableColumn<?, ?> editColumn;

    @FXML
    private Tab canceledTab;

    @FXML
    private AnchorPane canceledTabAnchorPane;

    @FXML
    private TableColumn<?, ?> cancellationTimeColumn;

    @FXML
    private TableView<?> cancelledTable;

    @FXML
    private TableColumn<?, ?> cancelledTasksColumn;

    @FXML
    private TableColumn<?, ?> completedTasksColumn;

    @FXML
    private TableColumn<?, ?> completionTimeColumn;

    @FXML
    private Tab doneTab;

    @FXML
    private AnchorPane doneTabAnchorPane;

    @FXML
    private TableView<?> doneTable;

    @FXML
    private Label greetingsText;

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

//    ObservableList<Task> tasks = FXCollections.observableArrayList(
//            new Task("Научиться программированию",new Date(),new Date(1212121212121L),new Date(1212121212121L),new Date(), true),
//            new Task("Заработать бабла",new Date(),new Date(1212121212121L),new Date(1212121212121L),new Date(), true)
//    );

    @FXML
    void initialize() {
        greetingsText.setText("Hello, " + User.getCurrentFirstName() + "!");
        assert imageButtonGuitar != null : "fx:id=\"imageButtonGuitar\" was not injected: check your FXML file 'app.fxml'.";
        loginSignOutButton.setOnAction(event ->{
            openNewScene("/com/example/javafxapp/hello-view.fxml");
        });

//        currentTasksColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
//        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Task, Date>("currentTime"));
//        deadlineColumn.setCellValueFactory(new PropertyValueFactory<Task, Date>("deadline"));
//
//        currentTable.setItems(tasks);



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

}
