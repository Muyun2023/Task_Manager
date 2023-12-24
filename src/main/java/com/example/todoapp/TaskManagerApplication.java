package com.example.todoapp;

import com.example.todoapp.Database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class TaskManagerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("login.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("list.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addItem.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("Task Manager");
        stage.setScene(scene);
        stage.show();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        System.out.println("Current tasks amount: " + databaseHandler.getAllTasks(1));

//        DatabaseHandler databaseHandler1 = new DatabaseHandler();
//        ResultSet rs = databaseHandler1.getTaskByUser(1);
//        while(rs.next()){
//            System.out.println("This User's tasks: " + rs.getString("task"));
//        }
    }
    public static void main(String[] args) {
        launch();
    }
}