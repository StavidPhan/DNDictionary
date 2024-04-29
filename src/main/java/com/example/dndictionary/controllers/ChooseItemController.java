package com.example.dndictionary.controllers;

import com.example.dndictionary.game.ChooseItem;
import com.example.dndictionary.game.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javafx.scene.control.Label;

import static com.example.dndictionary.Utilities.VIEWS_PATH;

public class ChooseItemController extends Controller {
    private ChooseItem chooseItem;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView healthImage;
    @FXML
    private Label questionBox;
    private Item correctItem;
    private Item[] items;
    @FXML
    private Label correctLabel = new Label();
    private int numberOfQuestions = 0;
    @FXML
    private Label scoreBox;

    public ChooseItemController() throws FileNotFoundException {
        items = new Item[4];
        chooseItem = ChooseItem.getChooseItem();
    }

    public void showEndScene() throws IOException {
        if (chooseItem.getHealth() <= 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "ChooseItemEnd.fxml"));
            root = loader.load();

            ChooseItemEndController chooseItemEndController = loader.getController();
            chooseItemEndController.displayScore(chooseItem.getScore(), numberOfQuestions);

            Stage currentStage = (Stage) questionBox.getScene().getWindow();
            Scene scene = new Scene(root);
            ApplicationColorController.setColor(scene);
            scene.setFill(Color.TRANSPARENT);
            currentStage.setScene(scene);
            currentStage.show();
        }
    }

    public void correct() {
        correctLabel.setText("Correct!");
        chooseItem.increaseHighscore();
        scoreBox.setText(chooseItem.getScore() + "");
    }

    public void incorrect() throws IOException {
        correctLabel.setText("Wrong!");
        chooseItem.decreaseHealth();
        int health = chooseItem.getHealth();
        System.out.println(chooseItem.getHealth());

        if (health <= 0) {
            showEndScene();
        } else {
            updateImageHealth();
        }
    }

    private void updateImageHealth() {
        String imageName = "";
        int health = chooseItem.getHealth();
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

    private boolean checkIfArrayIsNotFull(Item[] items) {
        for (int i = 0; i < 4; i++) {
            if (items[i] == null) {
                return true;
            }
        }
        return false;
    }

    @FXML
    public void initializeQuestion(@SuppressWarnings("exports") ActionEvent event) {
        correctLabel.setText("Choose the correct item!");
        correctItem = chooseItem.returnRandomItem();
        correctItem.setChosen(true);

        int randomIndex = (int) (Math.random() * 4);
        items[randomIndex] = correctItem;

        while (checkIfArrayIsNotFull(items)) {
            randomIndex = (int) (Math.random() * 4);
            Item temp = chooseItem.returnRandomItem();
            if (items[randomIndex] == null && (!temp.isChosen())) {
                items[randomIndex] = temp;
                temp.setChosen(true);
            }
        }

        imageView1.setImage(items[0].getImage());
        imageView2.setImage(items[1].getImage());
        imageView3.setImage(items[2].getImage());
        imageView4.setImage(items[3].getImage());

        questionBox.setText(correctItem.getQuestion());
    }


    // when press button next question
    @FXML
    public void setQuestionByAction() {
        numberOfQuestions++;
        for (int i = 0; i < 4; ++i) {
            items[i].setChosen(false);
            items[i] = null;
        }

        correctItem = chooseItem.returnRandomItem();
        correctItem.setChosen(true);

        int randomIndex = (int) (Math.random() * 4);
        items[randomIndex] = correctItem;

        while (checkIfArrayIsNotFull(items)) {
            randomIndex = (int) (Math.random() * 4);
            Item temp = chooseItem.returnRandomItem();

            if (items[randomIndex] == null && (!temp.isChosen())) {
                items[randomIndex] = temp;
                temp.setChosen(true);
            }
        }

        imageView1.setImage(items[0].getImage());
        imageView2.setImage(items[1].getImage());
        imageView3.setImage(items[2].getImage());
        imageView4.setImage(items[3].getImage());

        questionBox.setText(correctItem.getQuestion());
    }


    public void clickImageView1(@SuppressWarnings("exports") MouseEvent event) throws IOException {
        if (items[0] == correctItem) {
            correct();
            setQuestionByAction();
        } else {
            incorrect();
        }
    }

    public void clickImageView2(@SuppressWarnings("exports") MouseEvent event) throws IOException {
        if (items[1] == correctItem) {
            correct();
            setQuestionByAction();
        } else {
            incorrect();
        }
    }

    public void clickImageView3(@SuppressWarnings("exports") MouseEvent event) throws IOException {
        if (items[2] == correctItem) {
            correct();
            setQuestionByAction();
        } else {
            incorrect();
        }
    }

    public void clickImageView4(@SuppressWarnings("exports") MouseEvent event) throws IOException {
        if (items[3] == correctItem) {
            correct();
            setQuestionByAction();
        } else {
            incorrect();
        }
    }

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

    @FXML
    public void end(@SuppressWarnings("exports") ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "ChooseItemEnd.fxml"));
        root = loader.load();

        ChooseItemEndController chooseItemEndController = loader.getController();
        chooseItemEndController.displayScore(chooseItem.getScore(), numberOfQuestions);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ApplicationColorController.setColor(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
}