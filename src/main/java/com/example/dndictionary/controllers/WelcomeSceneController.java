package com.example.dndictionary.controllers;

import com.example.dndictionary.models.Model;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import com.example.dndictionary.Word;
import com.example.dndictionary.Utilities;

public class WelcomeSceneController extends Controller implements Initializable {
    @FXML
    private ListView<Word> list;
    @FXML
    private ListView<Word> bookmarkList;
    @FXML
    private TextField searchBox;
    @FXML
    private Label wordLabel;
    @FXML
    private Label pronunciation;
    @FXML
    private Label description;
    @FXML
    private Button closeButton;

    private Word currentSelectedWord;
    private Connection connection = null;
    private double xOffset;
    private double yOffset;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rootAnchor.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });

        rootAnchor.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });

        rootAnchor.setOnMouseMoved(event -> {
            double x = event.getX();
            double y = event.getY();
            rootAnchor.setStyle("-fx-background-color: radial-gradient(center " + 100*x/Utilities.APP_WIDTH + "% " + 100*y/Utilities.APP_HEIGHT + "%, radius 25%, #d4d4d4,  #e8e8e8);");
            System.out.println("x = " + x + ", y = " + y);
            System.out.println("x/Utilities.APP_WIDTH = " + x/Utilities.APP_WIDTH + ", y/Utilities.APP_HEIGHT = " + y/Utilities.APP_HEIGHT);
        });
//        searchBox.setPromptText("Search");

        ObservableList<Word> words = FXCollections.observableArrayList();
        ObservableList<Word> favorites = FXCollections.observableArrayList();

        list.setItems(words);
        bookmarkList.setItems(favorites);

        String viewsPath = "/com/example/dndictionary/views/";
        FXMLLoader sidePaneLoader = new FXMLLoader(getClass().getResource(viewsPath + "SidePane.fxml"));
        try {
            Parent sidePaneLoaded = sidePaneLoader.load();
            rootAnchor.getChildren().addAll(sidePaneLoaded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(Utilities.PATH_TO_DATABASE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from words");

            while (resultSet.next()) {
                Word word = new Word(resultSet.getString("word"), resultSet.getString("pronunciation"), resultSet.getString("description"), resultSet.getInt("isBookmarked"));
                words.add(word);
            }

            ResultSet favoriteSet = statement.executeQuery("SELECT * FROM words WHERE isBookmarked = 1");
            while (favoriteSet.next()) {
                Word word = new Word(favoriteSet.getString("word"), favoriteSet.getString("pronunciation"), favoriteSet.getString("description"), favoriteSet.getInt("isBookmarked"));
                favorites.add(word);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchWord() {
        ObservableList<Word> words = FXCollections.observableArrayList();
        list.setItems(words);

        String pattern = '*' + searchBox.getText() + '*';

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("select * from words where (word glob ?)");
            preparedStatement.setString(1, pattern);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Word word = new Word(resultSet.getString("word"), resultSet.getString("pronunciation"),
                        resultSet.getString("description"), resultSet.getInt("isBookmarked"));
                words.add(word);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void bookmark() {
        if (currentSelectedWord == null || currentSelectedWord.getWord().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a word.");
            alert.show();
            return;
        }

        if (Model.isBookmarked(currentSelectedWord) == 1) {
            currentSelectedWord.setBookmarked(false);
            Model.unbookmarkWord(currentSelectedWord);

        } else {
            currentSelectedWord.setBookmarked(true);
            Model.bookmarkWord(currentSelectedWord);
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM words WHERE isBookmarked = 1");
            ObservableList<Word> favorites = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Word word = new Word(resultSet.getString("word"), resultSet.getString("pronunciation"), resultSet.getString("description"), resultSet.getInt("isBookmarked"));
                favorites.add(word);
            }
            bookmarkList.setItems(favorites);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void speak(ActionEvent event) {
        if (currentSelectedWord == null || currentSelectedWord.getWord().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a word.");
            alert.show();
            return;
        }
        System.setProperty(
                "freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        Voice[] voices = VoiceManager.getInstance().getVoices();
        for (Voice value : voices) {
            System.out.println("# Voices: " + value.getName());
        }
        if (voice != null)
        {
            voice.allocate();
            System.out.println("Voice rate: " + voice.getRate());
            System.out.println("Voice pitch: " + voice.getPitch());
            System.out.println("Voice volume: " + voice.getVolume());
            boolean status = voice.speak(currentSelectedWord.getWord());
            System.out.println("Status: " + status);
            voice.deallocate();
        } else {
            System.err.println("error something");
        }
    }

    @FXML
    public void displayWordInSearchList(MouseEvent event) throws IOException {
        currentSelectedWord = list.getFocusModel().getFocusedItem();
        wordLabel.setText(currentSelectedWord.getWord());
        pronunciation.setText(currentSelectedWord.getPronunciation());
        description.setText(currentSelectedWord.getDescription());
    }

    @FXML
    public void displayWordInBookmarkList(MouseEvent event) throws IOException {
        currentSelectedWord = bookmarkList.getFocusModel().getFocusedItem();
        wordLabel.setText(currentSelectedWord.getWord());
        pronunciation.setText(currentSelectedWord.getPronunciation());
        description.setText(currentSelectedWord.getDescription());
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}