package com.example.todoapp.controller;

import com.example.todoapp.animations.Prompt;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ImageView addButton;
    @FXML
    private Label noTaskLabel;
    @FXML
    private AnchorPane rootPane;
    //private int userId;
    public static int userId;

    @FXML
    void initialize() {

        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Prompt prompt = new Prompt(addButton);
            prompt.show();

            System.out.println("Added Clicked!");

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), addButton);
            FadeTransition labelTransition = new FadeTransition(Duration.millis(3000), noTaskLabel);

            addButton.relocate(20, 20);
            noTaskLabel.relocate(0, 85);

            addButton.setOpacity(0);
            noTaskLabel.setOpacity(0);

            fadeTransition.setFromValue(1f);
            fadeTransition.setToValue(0f);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);
            fadeTransition.play();

            labelTransition.setFromValue(1f);
            labelTransition.setToValue(0f);
            labelTransition.setCycleCount(1);
            labelTransition.setAutoReverse(false);
            labelTransition.play();

            try {
                AnchorPane formPane =
                        FXMLLoader.load(getClass().getResource("/com/example/todoapp/addItemForm.fxml"));

                AddItemFormController addItemFormController = new AddItemFormController();
                addItemFormController.setUserId(getUserId());
                //AddItemController.userId = getUserId();

                rootPane.getChildren().setAll(formPane);

                FadeTransition rootTransition = new FadeTransition(Duration.millis(2000), formPane);
                rootTransition.setFromValue(0f);
                rootTransition.setToValue(1f);
                rootTransition.setCycleCount(1);
                rootTransition.setAutoReverse(false);
                rootTransition.play();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("User id is " + this.userId);
    }

    public int getUserId(){
        return this.userId;
    }
}
