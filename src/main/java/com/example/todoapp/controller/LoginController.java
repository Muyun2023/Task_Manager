package com.example.todoapp.controller;

import com.example.todoapp.Database.DatabaseHandler;
import com.example.todoapp.animations.Prompt;
import com.example.todoapp.model.User;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private JFXButton loginButton;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private JFXButton loginSignupButton;
    @FXML
    private TextField loginUsername;
    private DatabaseHandler databaseHandler;
    private int userId;
    @FXML
    void initialize() {
        databaseHandler = new DatabaseHandler();

        loginButton.setOnAction(actionEvent -> {
            String loginName = loginUsername.getText().trim();
            String loginPwd = loginPassword.getText().trim();

            if(loginName.equals("") && loginPwd.equals("")){
                Prompt prompt = new Prompt(loginUsername); // here should be node type parameter
                prompt.show();
            }

            User user = new User();
            user.setUserName(loginName);
            user.setPassword(loginPwd);
            ResultSet userInfo = null;

            try {
                userInfo = databaseHandler.checkUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            int counter = 0;
            try{
                while(userInfo.next()){
                    counter++;
                    // String userName = userInfo.getString("userName");
                    // System.out.println("welcome "+userName);
                    userId = userInfo.getInt("userid");

                }
                if(counter == 1){
                    showAddItemPage();
                }else{
                    Prompt prompt1 = new Prompt(loginUsername); // here should be node type parameter
                    prompt1.show();
                    Prompt prompt2 = new Prompt(loginPassword);
                    prompt2.show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        loginSignupButton.setOnAction(event -> {
            loginButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/todoapp/signUp.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }

    private void showAddItemPage() {
        loginSignupButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/todoapp/addItem.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        AddItemController addItemController = loader.getController();
        addItemController.setUserId(userId);

        stage.showAndWait();
    }


}
