package com.example.todoapp.controller;

import com.example.todoapp.Database.DatabaseHandler;
import com.example.todoapp.model.Task;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddItemFormController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField descriptionField;
    @FXML
    private Label promptLabel;
    @FXML
    private JFXButton saveTaskButton;
    @FXML
    private TextField taskField;
    @FXML
    private JFXButton tasksButton;
    private static int userId;
    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();
        saveTaskButton.setOnAction(event -> {
            Task task = new Task();
            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp =
                    new java.sql.Timestamp(calendar.getTimeInMillis());

           String taskText = taskField.getText().trim();
           String taskDescription = descriptionField.getText().trim();

          if (!taskText.equals("") || !taskDescription.equals("")) {
              System.out.println("User Id is: " + getUserId());
              task.setUserId(getUserId());
              task.setDatecreated(timestamp);
              task.setDescription(taskDescription);
              task.setTask(taskText);

              databaseHandler.addTask(task);
              taskField.setText("");
              descriptionField.setText("");
              promptLabel.setVisible(true);
              tasksButton.setVisible(true);

              int taskNumber = 0;
              try {
                  taskNumber = databaseHandler.getAllTasks(getUserId());
              } catch (SQLException e) {
                  throw new RuntimeException(e);
              } catch (ClassNotFoundException e) {
                  throw new RuntimeException(e);
              }
              tasksButton.setText("Tasks Total" + "( " + taskNumber + " )");
              tasksButton.setOnAction(event1 ->{
                  FXMLLoader loader = new FXMLLoader();
                  loader.setLocation(getClass().getResource("/com/example/todoapp/list.fxml"));
                  try {
                      loader.load();
                  } catch (IOException e) {
                      throw new RuntimeException(e);
                  }
                  Parent root = loader.getRoot();
                  Stage stage = new Stage();
                  stage.setScene(new Scene(root));
                  stage.showAndWait();
              });
              System.out.println(task.getUserId());
              System.out.println("Successfully add!");
            }else{
                System.out.println("Nothing added!");
            }
        });
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println(this.userId);
    }
}
