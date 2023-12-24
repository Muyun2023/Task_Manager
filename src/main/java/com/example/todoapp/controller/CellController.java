package com.example.todoapp.controller;

import com.example.todoapp.Database.DatabaseHandler;
import com.example.todoapp.model.Task;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class CellController extends JFXListCell<Task> {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label dataLabel;
    @FXML
    private ImageView deleteButton;  // this is not a normal button but an image
    @FXML
    private ImageView refreshButton;
    @FXML
    private Label descriptionLabel;
    @FXML
    private ImageView iconImageView;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label taskLabel;
    private FXMLLoader fxmlLoader;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

    }
    @Override
    public void updateItem(Task myTask,boolean empty){
        databaseHandler = new DatabaseHandler();
        super.updateItem(myTask,empty);
        if (empty || myTask == null) {
            setText(null);
            setGraphic(null);
        }else {
            if (fxmlLoader == null ) {
                fxmlLoader = new FXMLLoader(getClass()
                        .getResource("/com/example/todoapp/cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            taskLabel.setText(myTask.getTask());
            dataLabel.setText(myTask.getDatecreated().toString());
            descriptionLabel.setText(myTask.getDescription());

            //System.out.println(" User Id From Cell controller: " + AddItemController.userId);
            int taskId = myTask.getTaskId();

            refreshButton.setOnMouseClicked(event -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/example/todoapp/updateTask.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                UpdateTaskController updateTaskController = loader.getController();
                updateTaskController.setTaskField(myTask.getTask());
                updateTaskController.setUpdateDescriptionField(myTask.getDescription());

                updateTaskController.updateButton.setOnAction(event1 -> {
                    Calendar calendar = Calendar.getInstance();
                    java.sql.Timestamp timestamp =
                            new java.sql.Timestamp(calendar.getTimeInMillis());
                    try {
                        System.out.println("taskid " + myTask.getTaskId());
                        databaseHandler.updateTask(timestamp, updateTaskController.getDescription(),
                                updateTaskController.getTask(), myTask.getTaskId());

                        //update our listController
                        //updateTaskController.refreshList();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                stage.show();
            });






            deleteButton.setOnMouseClicked(event -> {
                databaseHandler = new DatabaseHandler();
                try {
                    databaseHandler.deleteTask(AddItemController.userId,taskId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                getListView().getItems().remove(getItem());
            });

            setText(null);
            setGraphic(rootPane);
        }
    }


}
