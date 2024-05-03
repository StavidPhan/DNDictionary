package com.example.dndictionary.controllers;

import com.example.dndictionary.UserDefinedWord;

import com.example.dndictionary.Utilities;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.dndictionary.Utilities.VIEWS_PATH;

public class AddNewWordSceneController extends Controller implements Initializable {
    @FXML
    private TextField userDefinedWord;
    @FXML
    private TextField userDefinedMeaning;
    @FXML
    private ListView<UserDefinedWord> list;
    @FXML
    private TextField searchBox;
    @FXML
    private Label wordLabel;
    @FXML
    private Label meaningLabel;
    @FXML
    private Button closeButton;

    private Connection connection = null;
    private UserDefinedWord currentSelectedWord;
    private ObservableList<UserDefinedWord> words;


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

        words = FXCollections.observableArrayList();
        list.setItems(words);
        list.setCellFactory(userDefinedWordListView -> new UserDefinedWordListViewCell());

        //load sidepane.
        FXMLLoader sidePaneLoader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "SidePane.fxml"));
        try {
            Parent sidePaneLoaded = sidePaneLoader.load();
            rootAnchor.getChildren().addAll(sidePaneLoaded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // connect to database.
        try {
            connection = DriverManager.getConnection(Utilities.PATH_TO_DATABASE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // get data from database.
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM userDefinedWords");

            while (resultSet.next()) {
                UserDefinedWord word = new UserDefinedWord(resultSet.getString("word"), resultSet.getString("meaning"));
                words.add(word);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // searchWord method.
    public void searchWord() {
        ObservableList<UserDefinedWord> words = FXCollections.observableArrayList();
        list.setItems(words);

        String pattern = '*' + searchBox.getText() + '*';

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM userDefinedWords WHERE (word glob ?)");
            preparedStatement.setString(1, pattern);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UserDefinedWord word = new UserDefinedWord(resultSet.getString("word"), resultSet.getString("meaning"));
                words.add(word);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        list.refresh();

    }

    //add new word.
    public void addUserDefinedWord(ActionEvent event) {
        UserDefinedWord word = new UserDefinedWord(userDefinedWord.getText(), userDefinedMeaning.getText());
        System.out.println(word.getWord());
        System.out.println(word.getMeaning());
        if (word.getWord() != null) {
            boolean success = Model.addUserDefinedWord(word);
            if (!success) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("This word already exists in your dictionary.");
                alert.show();
                return;
            }
            FXMLLoader wordSceneLoader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "AddNewWordScene.fxml"));
            Parent root = null;
            try {
                root = wordSceneLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            ApplicationColorController.setColor(scene);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        }
    }

    //voice.
    @FXML
    public void speak(ActionEvent event) {
        System.setProperty(
                "freetts.voices",
                "com.sun.speech.freetts.en.us"
                        + ".cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        Voice[] voices = VoiceManager.getInstance().getVoices();
        for (int i = 0; i < voices.length; i++) {
            System.out.println("# Voices: " + voices[i].getName());

        }
        if (voice != null)
        {
            voice.allocate();
            System.out.println("Voice volume: " + voice.getVolume());
            boolean status = voice.speak(currentSelectedWord.getWord());
            voice.deallocate();
        } else {
            System.err.println("error something");
        }
    }

    //display word.
    @FXML
    public void displayWord(MouseEvent event) throws IOException {
        currentSelectedWord = list.getFocusModel().getFocusedItem();
        System.out.println(list.getFocusModel().getFocusedIndex());
        wordLabel.setText(currentSelectedWord.getWord());
        meaningLabel.setText(currentSelectedWord.getMeaning());
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

