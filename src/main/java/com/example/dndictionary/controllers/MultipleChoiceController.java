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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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
    @FXML
    private ImageView healthImage;

    public void autoNextQuestion() throws IOException {
        if (numberOfQuestionsUsed == multipleChoice.getNumberOfQuestions() || multipleChoice.getHealth() <= 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "MultipleChoiceEnd.fxml"));
            root = loader.load();

            MultipleChoiceEndController multipleChoiceEndController = loader.getController();
            multipleChoiceEndController.displayScore(multipleChoice.getScore(), multipleChoice.getNumberOfQuestions());

            Stage currentStage = (Stage) questionBox.getScene().getWindow();
            Scene scene = new Scene(root);
            ApplicationColorController.setColor(scene);
            currentStage.setScene(scene);
            currentStage.show();
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

    // when press button next question
    @FXML
    public void setQuestion(ActionEvent event) throws IOException {
        if (numberOfQuestionsUsed == multipleChoice.getNumberOfQuestions() || multipleChoice.getHealth() <= 0) {
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

    public void incorrect() throws IOException {
        resultBox.setText("Wrong!");
        multipleChoice.decreaseHealth();
        int health = multipleChoice.getHealth();
        System.out.println(multipleChoice.getHealth());

        if (health <= 0) {
            autoNextQuestion();
        } else {
            updateImageHealth();
        }
    }

    private void updateImageHealth() {
        String imageName = "";
        int health = multipleChoice.getHealth();
        if (health == 3) {
            imageName = "/com/example/dndictionary/pictures/threeHeart.png";
        } else if (health == 2) {
            imageName = "/com/example/dndictionary/pictures/twoHeart.png";
        } else if (health == 1) {
            imageName = "/com/example/dndictionary/pictures/oneHeart.png";
        }
        URL imageUrl = getClass().getResource(imageName);
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toExternalForm());
            healthImage.setImage(image);
            healthImage.setPreserveRatio(true); // Giữ nguyên tỷ lệ ảnh
        } else {
            System.err.println("Could not find image: " + imageName);
        }
    }

    @FXML
    public void choseA() throws IOException {
        if (AButton.getText().equals(correctAnswer)) {
            correct();
            autoNextQuestion();
        } else {
            incorrect();
        }
    }

    @FXML
    public void choseB() throws IOException {
        if (BButton.getText().equals(correctAnswer)) {
            correct();
            autoNextQuestion();
        } else {
            incorrect();
        }
    }

    @FXML
    public void choseC() throws IOException {
        if (CButton.getText().equals(correctAnswer)) {
            correct();
            autoNextQuestion();
        } else {
            incorrect();
        }
    }

    @FXML
    public void choseD() throws IOException {
        if (DButton.getText().equals(correctAnswer)) {
            correct();
            autoNextQuestion();
        } else {
            incorrect();
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