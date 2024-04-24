package com.example.dndictionary.controllers;
import com.example.dndictionary.apiservice.TranslateAPI;

import com.example.dndictionary.Utilities;
import com.example.dndictionary.Word;
import com.example.dndictionary.UserDefinedWord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.io.BufferedReader;



import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import static com.example.dndictionary.Utilities.VIEWS_PATH;

public class TranslationController extends Controller implements Initializable {
    @FXML
    private TextArea inputArea;
    @FXML
    private TextArea outputArea;
    @FXML
    private Label sourceLabel;
    @FXML
    private Label targetLabel;
    @FXML
    private Label numOfCharLabel;
    private TranslateAPI translateAPI;
    @FXML
    private TableView<Record> records;
    @FXML
    private TableColumn<Record, String> sourceLanguage;
    @FXML
    private TableColumn<Record, String> targetLanguage;
    @FXML
    private TableColumn<Record, String> sourceText;
    @FXML
    private TableColumn<Record, String> targetText;
    @FXML
    private Button closeButton;
    @FXML
    private Button sound1;
    @FXML
    private Button sound2;

    private ObservableList<Record> history = FXCollections.observableArrayList();

    public void init() {
        this.sourceLabel.setText("English");
        this.targetLabel.setText("Vietnamese");
        this.numOfCharLabel.setText("0/5000");

    }

    @FXML
    public void translate() throws InterruptedException {
        if (inputArea.getText().isEmpty()) {
            outputArea.setText("Please enter text to translate");
            return;
        }

        outputArea.setText("Translating...");
        translateAPI = new TranslateAPI(inputArea.getText(), sourceLabel.getText(), targetLabel.getText());
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                outputArea.setText(translateAPI.getData());
                Model.addTranslationHistory(sourceLabel.getText(), targetLabel.getText(), inputArea.getText(), outputArea.getText());
                history.add(new Record(sourceLabel.getText(), targetLabel.getText(), inputArea.getText(), outputArea.getText()));
                records.setItems(history);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    @FXML
    public void swapLanguage() {
        String temp = sourceLabel.getText();
        sourceLabel.setText(targetLabel.getText());
        targetLabel.setText(temp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader sidePaneLoader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "SidePane.fxml"));
        try {
            Parent sidePaneLoaded = sidePaneLoader.load();
            rootAnchor.getChildren().addAll(sidePaneLoaded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sourceLanguage.setCellValueFactory(new PropertyValueFactory<Record, String>("sourceLanguage"));
        targetLanguage.setCellValueFactory(new PropertyValueFactory<Record, String>("targetLanguage"));
        sourceText.setCellValueFactory(new PropertyValueFactory<Record, String>("sourceText"));
        targetText.setCellValueFactory(new PropertyValueFactory<Record, String>("targetText"));

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = DriverManager.getConnection(Utilities.PATH_TO_DATABASE);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from translationHistory");

            while (resultSet.next()) {
                String sourceLanguage = resultSet.getString("sourceLanguage");
                String targetLanguage = resultSet.getString("targetLanguage");
                String sourceText = resultSet.getString("sourceText");
                String targetText = resultSet.getString("targetText");
                history.add(new Record(sourceLanguage, targetLanguage, sourceText, targetText));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sound1.setOnAction(event -> speakText(inputArea.getText()));
        sound2.setOnAction(event -> speakText(outputArea.getText()));

        records.setItems(history);
    }

    @FXML
    public void countChar() {
        numOfCharLabel.setText(inputArea.getText().length() + "/5000");
    }

    @FXML
    public void deleteHistory() {
        Statement statement = null;
        try {
            Connection connection = DriverManager.getConnection(Utilities.PATH_TO_DATABASE);
            statement = connection.createStatement();
            statement.executeUpdate("delete from translationHistory");
            history.clear();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }



    @FXML

    private void speakText(String text) {
        // Khởi tạo FreeTTS
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");

        // Kiểm tra nếu có giọng nói
        if (voice != null) {
            voice.allocate();
            voice.speak(text); // Phát âm văn bản
        } else {
            System.err.println("Cannot find voice: kevin16");
        }
    }



}
