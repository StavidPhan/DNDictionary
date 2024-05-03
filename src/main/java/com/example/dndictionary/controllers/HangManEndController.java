package com.example.dndictionary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.dndictionary.Utilities.VIEWS_PATH;

public class HangManEndController extends Controller {
    @FXML
    private Label scoreBox;
    @FXML
    private Label emotionBox;

    @FXML
    public void switchToGameScene(@SuppressWarnings("exports") ActionEvent event) throws IOException {
        FXMLLoader gameScene = new FXMLLoader(getClass().getResource(VIEWS_PATH + "gameScene.fxml"));
        root = gameScene.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ApplicationColorController.setColor(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void displayScore(int score) {
        scoreBox.setText("Your Score: " + score);
        if (score == 0) {
            emotionBox.setText("Seriously?");
        } else if (score >= 50) {
            emotionBox.setText("Good job baby!");
        } else {
            emotionBox.setText("Try harder baby!");
        }
    }

    @FXML
    public void replay(@SuppressWarnings("exports") ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "HangMan.fxml"));
        root = loader.load();

        HangManController hangManController = loader.getController();
        hangManController.initializeQuestion(event);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ApplicationColorController.setColor(scene);
        stage.setScene(scene);
        stage.show();
    }
}
