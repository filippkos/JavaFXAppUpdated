module com.example.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.javafxapp to javafx.fxml;
    exports com.example.javafxapp;
    exports com.example.javafxapp.models;
    opens com.example.javafxapp.models to javafx.fxml;
    exports com.example.javafxapp.controllers;
    opens com.example.javafxapp.controllers to javafx.fxml;
}