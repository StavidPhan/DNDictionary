package com.example.dndictionary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SidePaneController extends Controller implements Initializable {
    public enum SCENE {
        WELCOME_SCENE,
        ADD_NEW_WORD_SCENE,
        GAME_SCENE,
        TRANSLATION_SCENE,
        SETTING_SCENE
    }
    private final static String VIEWS_PATH = "/com/example/dndictionary/views/";
    public static SCENE currentScene = SCENE.WELCOME_SCENE;
    @FXML
    private Button homeButton;
    @FXML
    private Button userWordButton;
    @FXML
    private Button gameButton;
    @FXML
    private Button translateButton;
    @FXML
    private Button settingButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (currentScene.equals(SCENE.WELCOME_SCENE)) {
            homeButton.setStyle("-fx-background-color: " + SettingSceneController.cssAccent);
        } else if (currentScene.equals(SCENE.ADD_NEW_WORD_SCENE)) {
            userWordButton.setStyle("-fx-background-color: " + SettingSceneController.cssAccent);
        } else if (currentScene.equals(SCENE.GAME_SCENE)) {
            gameButton.setStyle("-fx-background-color: " + SettingSceneController.cssAccent);
        } else if (currentScene.equals(SCENE.TRANSLATION_SCENE)) {
            translateButton.setStyle("-fx-background-color: " + SettingSceneController.cssAccent);;
        } else if (currentScene.equals(SCENE.SETTING_SCENE)) {
            settingButton.setStyle("-fx-background-color: " + SettingSceneController.cssAccent);
        }
    }

    @FXML
    public void switchToMainScene(@SuppressWarnings("exports") ActionEvent event) throws IOException {
        currentScene = SCENE.WELCOME_SCENE;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "WelcomeScene.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ApplicationColorController.setColor(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToAddWordScene(@SuppressWarnings("exports") ActionEvent event) throws IOException {
        currentScene = SCENE.ADD_NEW_WORD_SCENE;
        FXMLLoader wordSceneLoader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "AddNewWordScene.fxml"));
        root = wordSceneLoader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ApplicationColorController.setColor(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Function to switch to game scene.
     * ATTENTION: this function is also used in class MultipleChoiceEndController.
     */
    @FXML
    public void switchToGameScene(@SuppressWarnings("exports") ActionEvent event) throws IOException {
        currentScene = SCENE.GAME_SCENE;
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
    public void switchToSettingScene(@SuppressWarnings("exports") ActionEvent event) throws IOException {
        currentScene = SCENE.SETTING_SCENE;
        FXMLLoader settingSceneLoader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "SettingScene.fxml"));
        root = settingSceneLoader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ApplicationColorController.setColor(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToTranslationScene(@SuppressWarnings("exports") ActionEvent event) throws IOException {
        currentScene = SCENE.TRANSLATION_SCENE;
        FXMLLoader settingSceneLoader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "TranslationScene.fxml"));
        root = settingSceneLoader.load();

        TranslationController controller = settingSceneLoader.getController();
        controller.init();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        ApplicationColorController.setColor(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

}