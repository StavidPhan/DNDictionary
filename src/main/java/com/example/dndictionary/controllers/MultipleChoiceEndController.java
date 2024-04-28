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

public class MultipleChoiceEndController extends Controller {
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

    public void displayScore(int score, int numberOfQuestions) {
        scoreBox.setText("Your Score: " + score);
        if (score == 0) {
            emotionBox.setText("Seriously?");
        } else if (score == numberOfQuestions) {
            emotionBox.setText("Good job baby!");
        }
    }

    @FXML
    public void replay(@SuppressWarnings("exports") ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "MultipleChoiceScene.fxml"));
        root = loader.load();

        MultipleChoiceController multipleChoiceController = loader.getController();
        multipleChoiceController.setQuestion(event);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ApplicationColorController.setColor(scene);
        stage.setScene(scene);
        stage.show();
    }
}