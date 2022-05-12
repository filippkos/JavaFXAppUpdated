package com.example.javafxapp.models;

import com.example.javafxapp.Configs;
import com.example.javafxapp.Const;
import javafx.scene.control.TableView;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + "," +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + "," +
                Const.USERS_LOCATION + "," + Const.USERS_GENDER + ")" +
                "VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getFirstName());
            prSt.setString(2, user.getLastName());
            prSt.setString(3, user.getUserName());
            prSt.setString(4, user.getPassword());
            prSt.setString(5, user.getLocation());
            prSt.setString(6, user.getGender());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param user
     * @return Подготовленный сет из базы данных
     */
    public ResultSet getUser(User user) {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return  resSet;
    }

    public void addTaskToDb(Task task) {
        String insert = "INSERT INTO " + Const.TASKS_TABLE + "(" +
                Const.TASKS_TASK + "," + Const.TASKS_STATE + ")" +
                "VALUES(?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, task.getTask());
            prSt.setString(2, "current");
//            prSt.setDate(3, (Date) task.getDeadline());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void editTaskInDb(Task task) {

        String update = "UPDATE " + Const.TASKS_TABLE + " SET " +
                Const.TASKS_TASK + " = " + "\"" + task.getTask() + "\"" + " WHERE " + Const.TASKS_ID + " = " + "\"" + task.getId() + "\"";

        try {
            getDbConnection().createStatement().executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void removeTaskFromDb(Task task) {
        String delete = "DELETE FROM " + Const.TASKS_TABLE + " WHERE " + Const.TASKS_ID + " = " + "\"" + task.getId() + "\"";

        try {
            getDbConnection().createStatement().executeUpdate(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ResultSet getTaskTableFromDb() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.TASKS_TABLE;
        try {
            resSet = getDbConnection().createStatement().executeQuery(select);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

}
