package com.example.todoapp.controller;

import com.example.todoapp.Database.DatabaseHandler;
import com.example.todoapp.model.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton signUpButton;

    @FXML
    private JFXCheckBox signUpFemale;

    @FXML
    private TextField signUpFirstName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpLocation;

    @FXML
    private JFXCheckBox signUpMale;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private TextField signUpUserName;
    private FXMLLoader loader;

    @FXML
    void initialize() {
        signUpButton.setOnAction(actionEvent -> {
            try {
                createUser();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            //signUpButton.getScene().getWindow().hide();
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/todoapp/login.fxml"));
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
    }
    private void createUser() throws ClassNotFoundException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        String firstName = signUpFirstName.getText();
        String lastName = signUpLastName.getText();
        String userName = signUpUserName.getText();
        String password = signUpPassword.getText();
        String location = signUpLocation.getText();
        String gender;
        if (signUpFemale.isSelected()) {
            gender = "Female";
        }else gender = "Male";
        User user = new User(firstName, lastName, userName, password, location, gender);
        databaseHandler.signUpUser(user);
    }
}

