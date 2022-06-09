package com.example.javafxapp.controllers;

import com.example.javafxapp.Const;
import com.example.javafxapp.animations.Shake;
import com.example.javafxapp.models.DatabaseHandler;
import com.example.javafxapp.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginBackButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField signUpCountry;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private RadioButton signUpRadioButtonFemale;

    @FXML
    private RadioButton signUpRadioButtonMale;

    @FXML
    void initialize() {

        signUpRadioButtonMale.setOnAction(event -> {
            signUpRadioButtonFemale.setSelected(false);
        });
        signUpRadioButtonFemale.setOnAction(event -> {
            signUpRadioButtonMale.setSelected(false);
        });

        signUpButton.setOnAction(event -> {
            signUpNewUser();

        });

        loginBackButton.setOnAction(event -> {
            openNewScene("/com/example/javafxapp/helloView.fxml");
        });
    }

    /**
     * Метод закрывает текущее окно и запускает новое.
     *
     * */
    private void openNewScene(String window) {
        signUpButton.getScene().getWindow().hide();

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

    private void signUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String firstName = signUpName.getText();
        String lastName = signUpLastName.getText();
        String userName = login_field.getText();
        String password = password_field.getText();
        String location = signUpCountry.getText();
        String gender = "";
        if(signUpRadioButtonMale.isSelected()) {
            gender = "Male";
        }
        else
            gender = "Female";


        User user = new User(firstName, lastName, userName, password, location, gender);


        if(logInValidation(user)) {
            dbHandler.signUpUser(user);
            ResultSet rs = dbHandler.getTheLastUserFromDb();
            try {
                while (rs.next()) {
                    dbHandler.createNewTaskBase(rs.getString(Const.USERS_ID));
                    openNewScene("/com/example/javafxapp/helloView.fxml");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            login_field.setText("This username already exists.");
            Shake userNameAnim = new Shake(login_field);
            userNameAnim.playAnim();
        }
    }

    private boolean logInValidation(User user) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getAllUsers();
        try {
            while (result.next()) {
                if (user.getUserName().equals(result.getString(Const.USERS_USERNAME)) || user.getUserName().equals("This username already exists.")) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
