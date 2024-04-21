package com.example.dndictionary.controllers;

import com.example.dndictionary.game.HangMan;
import com.example.dndictionary.game.HangManWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static com.example.dndictionary.Utilities.VIEWS_PATH;

public class HangManController extends Controller {
    @FXML
    public TextArea suggestBox;
    @FXML
    public Label guessWord;
    @FXML
    public Label gameOver;
    @FXML
    public Label correctWord;
    @FXML
    public TextField guessChar;
    @FXML
    private Label resultBox;
    @FXML
    private Label scoreBox;
    @FXML
    private ImageView healthImage;

    private HangMan hangman = new HangMan();
    HangManWord word = new HangManWord(hangman.randWord());
    @FXML
    public void initializeQuestion(ActionEvent event) throws IOException {
        hangman.insertFromFile();
        setupAnswerField();
        resultBox.setText("Guess the word!");
        word = new HangManWord(hangman.randWord());
        nextWord();
        System.out.println(word.word);
    }

    private void setupAnswerField() {
        if (guessChar != null) {
            guessChar.setEditable(true);

            guessChar.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    handleAnswer();
                }
            });
        } else {
            System.out.println("guessChar is null!");
        }
    }

    private void handleAnswer() {
        String userInput = guessChar.getText().trim().toLowerCase();
        char userChar = userInput.charAt(0);
        // đoán sai
        if (!word.checkAnswers(userChar)) {
            resultBox.setText("Wrong!");
            hangman.decreaseHealth();

            if (hangman.getHealth() <= 0) {
                updateImageHealth();
                gameOver.setText("GAME OVER!");
                correctWord.setText("Correct word: " + word.word);
                guessChar.clear();
            } else {
                updateImageHealth();
            }

            guessChar.clear();
        }
        // đoán đúng
        else {
            resultBox.setText("Correct!");
            scoreBox.setText(hangman.getScore() + "");
            guessWord.setText(word.randGuessWord());
            suggestBox.setText(word.printInfoGraphic());
            hangman.increaseHighscore();
            guessChar.clear();
        }

        if (word.completedWord()) {
            correctWord.setText("Congratulations! 🎉🎉🎉");
            guessChar.clear();
        }
    }

    private void updateImageHealth() {
        String imageName = "";
        int health = hangman.getHealth();
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
    private void nextWord() {
        if (hangman != null && word != null) {
            guessWord.setText(word.randGuessWord());
            suggestBox.setText(word.printInfoGraphic());
        }
        hangman.setHealth(3);
        hangman.setScore(0);
        updateImageHealth();
        gameOver.setText("");
        correctWord.setText("");
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "HangManEnd.fxml"));
        root = loader.load();

        HangManEndController hangManEndController = loader.getController();
        hangManEndController.displayScore(hangman.getScore());

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ApplicationColorController.setColor(scene);
        stage.setScene(scene);
        stage.show();
    }
}

