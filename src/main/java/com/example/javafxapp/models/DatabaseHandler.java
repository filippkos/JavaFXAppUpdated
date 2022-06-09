package com.example.javafxapp.models;

import com.example.javafxapp.Configs;
import com.example.javafxapp.Const;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

        String select = "SELECT * FROM " + Const.USER_TABLE +
                " WHERE " + Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";
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

    public ResultSet getAllUsers() {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return  resSet;
    }

    public void addTaskToDb(userTask task, LocalDateTime deadline) {
        String insert = "INSERT INTO " + Const.TASKS_TABLE + "_" + User.getCurrentId() + "(" +
                Const.TASKS_TASK + "," + Const.TASKS_STATE + "," +
                Const.TASKS_START_DATE + "," + Const.TASKS_DEADLINE + ")" +
                "VALUES(?,?,NOW(),?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, task.getTask());
            prSt.setString(2, TaskState.CURRENT.getTitle());
            prSt.setTimestamp(3, Timestamp.valueOf(deadline));


            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void editTaskStateInDb(userTask task, String state) {
        String update = "";
        String isInTime = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm");
        LocalDateTime deadlineLocal = LocalDateTime.parse(task.getDeadline(), formatter);
        if(deadlineLocal.isEqual(LocalDateTime.now())||deadlineLocal.isAfter(LocalDateTime.now())){
            isInTime = ", " + Const.TASKS_IS_IN_TIME + " = 1";
        }
        if(state.equals(TaskState.DONE.getTitle())) {
            update = "UPDATE " + Const.TASKS_TABLE + "_" + User.getCurrentId() + " SET " +
                    Const.TASKS_STATE + " = " + "\"" + state + "\"" + ", " +
                    Const.TASKS_COMPLETION_TIME + " = " + "NOW()" +
                    isInTime +
                    " WHERE " + Const.TASKS_ID + " = " + "\"" + task.getId() + "\"";
        }
        if(state.equals(TaskState.CANCELLED.getTitle())) {
            update = "UPDATE " + Const.TASKS_TABLE + "_" + User.getCurrentId() + " SET " +
                    Const.TASKS_STATE + " = " + "\"" + state + "\"" + ", " +
                    Const.TASKS_CANCELLATION_TIME + " = " + "NOW()" + ", " +
                    Const.TASKS_REASON_FOR_CANCELLATION + " = " + "\"" + task.getReasonForCancellation() + "\"" +
                    " WHERE " + Const.TASKS_ID + " = " + "\"" + task.getId() + "\"";
        }

        try {
            getDbConnection().createStatement().executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void editTaskInDb(userTask task) {
        String update = "UPDATE " + Const.TASKS_TABLE + "_" + User.getCurrentId() + " SET " +
                Const.TASKS_TASK + " = " + "\"" + task.getTask() + "\"" +
                " WHERE " + Const.TASKS_ID + " = " + "\"" + task.getId() + "\"";

        try {
            getDbConnection().createStatement().executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void removeTaskFromDb(userTask userTask) {
        String delete = "DELETE FROM " + Const.TASKS_TABLE + "_" + User.getCurrentId() + " WHERE " + Const.TASKS_ID + " = " + "\"" + userTask.getId() + "\"";

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
        String select = "SELECT * FROM " + Const.TASKS_TABLE + "_" + User.getCurrentId();
        try {
            resSet = getDbConnection().createStatement().executeQuery(select);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }

    public ResultSet getTheLastTaskFromDb() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.TASKS_TABLE + "_" + User.getCurrentId() + " ORDER BY " + Const.TASKS_ID + " DESC LIMIT 0, 1";
        try {
            resSet = getDbConnection().createStatement().executeQuery(select);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public void createNewTaskBase(String idOfNewUser) {
        ResultSet resSet = null;
        String create = "CREATE TABLE `myfirstapp`.`tasks_" + idOfNewUser + "` (" +
  "`idtask` INT NOT NULL primary key AUTO_INCREMENT," +
  "`task` VARCHAR(45) NOT NULL," +
  "`state` VARCHAR(45) NOT NULL," +
  "`startdate` DATETIME NULL DEFAULT NULL," +
  "`deadline` DATETIME NULL DEFAULT NULL," +
  "`completiontime` DATETIME NULL DEFAULT NULL," +
  "`cancellationtime` DATETIME NULL DEFAULT NULL," +
  "`isintime` TINYINT NOT NULL DEFAULT '0'," +
  "`reasonforcancellation` VARCHAR(45) NULL DEFAULT NULL)" +
        "ENGINE = InnoDB " +
        "DEFAULT CHARACTER SET = utf8mb4 " +
        "COLLATE = utf8mb4_0900_ai_ci;";
        try {
            getDbConnection().createStatement().executeUpdate(create);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getTheLastUserFromDb() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " ORDER BY " + Const.USERS_ID + " DESC LIMIT 0, 1";
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
