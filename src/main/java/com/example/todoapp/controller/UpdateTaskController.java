package com.example.todoapp.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class UpdateTaskController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField textField;

    @FXML
    public JFXButton updateButton;

    @FXML
    void initialize() {

    }

    public void setTaskField(String task) {
        this.textField.setText(task);
    }

    public String getTask() {
        return this.textField.getText().trim();
    }

    public void setUpdateDescriptionField(String description) {
        this.descriptionField.setText(description);
    }

    public String getDescription() {
        return this.descriptionField.getText().trim();
    }
}
