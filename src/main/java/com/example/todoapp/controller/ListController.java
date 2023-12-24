package com.example.todoapp.controller;

import com.example.todoapp.Database.DatabaseHandler;
import com.example.todoapp.model.Task;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;


public class ListController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField descriptionField;

    @FXML
    private JFXListView<Task> list;

    @FXML
    private ImageView refreshButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private TextField taskField;
    private ObservableList<Task> tasks;

    private ObservableList<Task> refreshedTasks;
    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() throws SQLException {
        tasks = FXCollections.observableArrayList();
        databaseHandler = new DatabaseHandler();
        ResultSet rs = databaseHandler.getTaskByUser(AddItemController.userId);
        while (rs.next()) {
            Task task = new Task();
            task.setTaskId(rs.getInt("taskid"));
            task.setTask(rs.getString("task"));
            task.setDatecreated(rs.getTimestamp("datecreated"));
            task.setDescription(rs.getString("description"));
            System.out.println("This User's tasks: " + rs.getString("task"));
            tasks.addAll(task);
        }

        list.setItems(tasks);
        list.setCellFactory(CellController -> new CellController());

        saveButton.setOnAction(event ->{
            addNewTask();
        });

        refreshButton.setOnMouseClicked(mouseEvent -> {
            try {
                refreshList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

//      Task myTask = new Task();
//      myTask.setTask("Meeting");
//      myTask.setDescription("INFO5100");
//      myTask.setDatecreated(Timestamp.valueOf(LocalDateTime.now()));

//      Task newTask = new Task();
//      newTask.setTask("Shopping");
//      newTask.setDescription("Mall");
//      newTask.setDatecreated(Timestamp.valueOf(LocalDateTime.now()));

//      tasks.addAll(myTask,newTask);
//      tasks.add(myTask);


    }

    public void addNewTask() {
        if (!taskField.getText().equals("")
                || !descriptionField.getText().equals("")) {
            Task myNewTask = new Task();
            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp =
                    new java.sql.Timestamp(calendar.getTimeInMillis());

            myNewTask.setUserId(AddItemController.userId);
            myNewTask.setTask(taskField.getText().trim());
            myNewTask.setDescription(descriptionField.getText().trim());
            myNewTask.setDatecreated(timestamp);

            databaseHandler.addTask(myNewTask);

            taskField.setText("");
            descriptionField.setText("");

            try {
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void refreshList() throws SQLException {
        System.out.println("refreshList in ListCont called");
        refreshedTasks = FXCollections.observableArrayList();
        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTaskByUser(AddItemController.userId);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            refreshedTasks.addAll(task);
        }

        list.setItems(refreshedTasks);
        list.setCellFactory(CellController -> new CellController());

    }
}


