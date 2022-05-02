package com.example.javafxapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label greetingsText;

    @FXML
    private ImageView imageButtonGuitar;

    @FXML
    private Button loginSignOutButton;

    @FXML
    void initialize() {
        greetingsText.setText("Hello, " + User.getCurrentFirstName() + "!");
        assert imageButtonGuitar != null : "fx:id=\"imageButtonGuitar\" was not injected: check your FXML file 'app.fxml'.";
        loginSignOutButton.setOnAction(event ->{
            openNewScene("/com/example/javafxapp/hello-view.fxml");
        });

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
