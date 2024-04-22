package com.example.dndictionary.controllers;

import com.example.dndictionary.game.HangMan;
import com.example.dndictionary.game.HangManWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    public Label gameStatus;
    @FXML
    public TextField guessChar;
    @FXML
    private Label resultBox;
    @FXML
    private Label scoreBox;
    @FXML
    private ImageView healthImage;
    @FXML
    private Button replayButton;

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
                replayButton.setVisible(true);
                gameStatus.setText("GAME OVER!");
                guessWord.setText(word.word);
                guessWord.setStyle(guessWord.getStyle() + "-fx-text-fill: #00ff00;");
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
            gameStatus.setText("Congratulations! 🎉🎉🎉");
            guessChar.clear();
        }
    }

    private void updateImageHealth() {
        String imageName = "";
        String pictures_path = "/com/example/dndictionary/pictures/";
        int health = hangman.getHealth();
        switch (health) {
            case 10:
                imageName = "hangman0.png";
                break;
            case 9:
                imageName = "hangman1.png";
                break;
            case 8:
                imageName = "hangman2.png";
                break;
            case 7:
                imageName = "hangman3.png";
                break;
            case 6:
                imageName = "hangman4.png";
                break;
            case 5:
                imageName = "hangman5.png";
                break;
            case 4:
                imageName = "hangman6.png";
                break;
            case 3:
                imageName = "hangman7.png";
                break;
            case 2:
                imageName = "hangman8.png";
                break;
            case 1:
                imageName = "hangman9.png";
                break;
            case 0:
                imageName = "hangman10.png";
                break;
        }

        URL imageUrl = getClass().getResource(pictures_path + imageName);
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
        hangman.setHealth(10);
        hangman.setScore(0);
        guessWord.setStyle(guessWord.getStyle().replace("-fx-text-fill: #00ff00;", ""));
        updateImageHealth();
        gameStatus.setText("");
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

