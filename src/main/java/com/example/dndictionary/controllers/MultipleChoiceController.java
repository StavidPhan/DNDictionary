package com.example.dndictionary.controllers;

import com.example.dndictionary.game.MultipleChoice;
import com.example.dndictionary.game.MultipleChoiceQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.dndictionary.Utilities.VIEWS_PATH;

public class MultipleChoiceController extends Controller {
    private int numberOfQuestionsUsed = 0;
    private final MultipleChoice multipleChoice = MultipleChoice.getMultipleChoice();
    private String correctAnswer;

    @FXML
    private Label questionBox;
    @FXML
    private Label resultBox;
    @FXML
    private Button AButton;
    @FXML
    private Button BButton;
    @FXML
    private Button CButton;
    @FXML
    private Button DButton;
    @FXML
    private Label scoreBox;

    public void nextQuestion() {
        MultipleChoiceQuestion currentQuestion = multipleChoice.returnRandomQuestion();

        questionBox.setText(currentQuestion.getQuestion());
        AButton.setText(currentQuestion.getAnswerA());
        BButton.setText(currentQuestion.getAnswerB());
        CButton.setText(currentQuestion.getAnswerC());
        DButton.setText(currentQuestion.getAnswerD());
        correctAnswer = currentQuestion.getCorrectAnswer();

        resultBox.setText("Choose your answer!");
        numberOfQuestionsUsed++;
    }
    @FXML
    public void setQuestion(ActionEvent event) throws IOException {
        if (numberOfQuestionsUsed == multipleChoice.getNumberOfQuestions()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "MultipleChoiceEnd.fxml"));
            root = loader.load();

            MultipleChoiceEndController multipleChoiceEndController = loader.getController();
            multipleChoiceEndController.displayScore(multipleChoice.getScore(), multipleChoice.getNumberOfQuestions());

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            ApplicationColorController.setColor(scene);
            stage.setScene(scene);
            stage.show();
            return;
        }

        MultipleChoiceQuestion currentQuestion = multipleChoice.returnRandomQuestion();

        questionBox.setText(currentQuestion.getQuestion());
        AButton.setText(currentQuestion.getAnswerA());
        BButton.setText(currentQuestion.getAnswerB());
        CButton.setText(currentQuestion.getAnswerC());
        DButton.setText(currentQuestion.getAnswerD());
        correctAnswer = currentQuestion.getCorrectAnswer();

        resultBox.setText("Choose your answer!");
        numberOfQuestionsUsed++;
    }

    public void correct() {
        resultBox.setText("Correct!");
        multipleChoice.increaseHighscore();
        scoreBox.setText(multipleChoice.getScore() + "");
    }

    @FXML
    public void choseA() {
        if (AButton.getText().equals(correctAnswer)) {
            correct();
            nextQuestion();
        } else {
            resultBox.setText("Wrong!");
        }
    }

    @FXML
    public void choseB() {
        if (BButton.getText().equals(correctAnswer)) {
            correct();
            nextQuestion();
        } else {
            resultBox.setText("Wrong!");
        }
    }

    @FXML
    public void choseC() {
        if (CButton.getText().equals(correctAnswer)) {
            correct();
            nextQuestion();
        } else {
            resultBox.setText("Wrong!");
        }
    }

    @FXML
    public void choseD() {
        if (DButton.getText().equals(correctAnswer)) {
            correct();
            nextQuestion();
        } else {
            resultBox.setText("Wrong!");
        }
    }

    @FXML
    public void switchBackToGameScene(ActionEvent event) throws IOException {
        FXMLLoader gameScene = new FXMLLoader(getClass().getResource(VIEWS_PATH + "gameScene.fxml"));
        root = gameScene.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ApplicationColorController.setColor(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void end(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "MultipleChoiceEnd.fxml"));
        root = loader.load();

        MultipleChoiceEndController multipleChoiceEndController = loader.getController();
        multipleChoiceEndController.displayScore(multipleChoice.getScore(), multipleChoice.getNumberOfQuestions());

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ApplicationColorController.setColor(scene);
        stage.setScene(scene);
        stage.show();
    }
}