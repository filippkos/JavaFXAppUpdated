package com.example.javafxapp;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.javafxapp.animations.Shake;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authSignInButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    /**
     * При нажатии на кнопку "вход" берёт логин и пароль из полей шаблона, обрезает пробелы по краям и кладёт в стринги.
     * Если поля не пустые, запускает метод loginUser, если пустые - выводит сообщение в консоль.
     * При нажатии на кнопку регистрация, запускает сцену (окно) с регистрацией.
     * */
    @FXML
    void initialize() {
        authSignInButton.setOnAction(event ->{
            String loginText = login_field.getText().trim();
            String loginPassword = password_field.getText().trim();

            if(!loginText.equals("") && !loginPassword.equals(""))
                loginUser(loginText, loginPassword);
            else
                System.out.println("Login and pass is empty");
        });


        loginSignUpButton.setOnAction(event ->{
            openNewScene("/com/example/javafxapp/signUp.fxml");
        });
    }

    /**
     * Метод принимает логин и пароль от пользователя.
     * Проверяет есть ли такой юзер в базе.
     * Если есть, то открывает сцену приложения. Если нет - запускает анимацию.
     * */
    private void loginUser(String loginText, String loginPassword) {
        //Создаём объект dbHandler
        DatabaseHandler dbHandler = new DatabaseHandler();
        //Создаём объект User
        User user = new User();
        //Устанавливаем в новый User введённые логин и пароль
        user.setUserName(loginText);
        user.setPassword(loginPassword);

        //Получаем ResultSet из базы по таким данным. ResultSet объект, где каждая строка - параметр 1 пользователя.
        ResultSet result = dbHandler.getUser(user);



        int counter = 0;

        //Просто считаем кол-во строк.
            try {


                String name = "";
                while(result.next()) {
                    name = result.getString("firstname");
                    counter++;
                }

                user.setCurrentFirstName(name);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        //Если строк больше 0, логинимся
        if(counter >= 1) {
            openNewScene("/com/example/javafxapp/app.fxml");
        } else {
            Shake userLoginAnim = new Shake(login_field);
            Shake userPassAnim = new Shake(password_field);
            userLoginAnim.playAnim();
            userPassAnim.playAnim();
        }

    }

    /**
     * Метод закрывает текущее окно и запускает новое.
     *
     * */
    private void openNewScene(String window) {
        loginSignUpButton.getScene().getWindow().hide();

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
        stage.showAndWait();
    }
}