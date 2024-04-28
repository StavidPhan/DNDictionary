package com.example.dndictionary.controllers;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingSceneController extends Controller implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private Button themeButton;
    @FXML
    private ChoiceBox<String> accentChooser;
    @FXML
    private Button closeButton;
    @FXML
    private Rating rating;
    @FXML
    private Label ratingLabel;

    public static boolean premium = false;

    public static String theme = "LIGHT";
    public static String accentColor = "ORANGE";
    public static String cssAccent = COLOR.ORANGE.toString();
    private final static String VIEWS_PATH = "/com/example/dndictionary/views/";

    public enum COLOR {
        BLUE {
            public String toString() {
                return "#3C91E6";
            }
        },
        ORANGE {
            public String toString() {
                return "#df8c52";
            }
        },
        GREEN {
            public String toString() {
                return "#26A65B";
            }
        }
    }

    static ObservableList<String> accentColorChoices = FXCollections.observableArrayList(
            "BLUE",
            "ORANGE",
            "GREEN"
    );


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

        accentChooser.setItems(accentColorChoices);
        accentChooser.setValue(accentColor);


        accentChooser.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accentColor = accentChooser.getValue();
                if (accentColor.equals("ORANGE")) {
                    cssAccent = COLOR.ORANGE.toString();

                } else if (accentColor.equals("BLUE")) {
                    cssAccent = COLOR.BLUE.toString();

                } else {
                    cssAccent = COLOR.GREEN.toString();

                }
                FXMLLoader settingSceneLoader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "SettingScene.fxml"));
                try {
                    root = settingSceneLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                ApplicationColorController.setColor(scene);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }
        });
        if (theme.equals("LIGHT")) {
            themeButton.setText("LIGHT");
        } else if (theme.equals("DARK")) {
            themeButton.setText("DARK");
        }

        FXMLLoader sidePaneLoader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "SidePane.fxml"));
        try {
            Parent sidePaneLoaded = sidePaneLoader.load();
            rootAnchor.getChildren().addAll(sidePaneLoaded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void changeTheme(@SuppressWarnings("exports") ActionEvent event) throws IOException {
        // switch to dark theme
        if (theme.equals("LIGHT")) {
            theme = "DARK";
            themeButton.setText("LIGHT");

            FXMLLoader settingSceneLoader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "SettingScene.fxml"));
            root = settingSceneLoader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            ApplicationColorController.setColor(scene);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } else {
            theme = "LIGHT";
            themeButton.setText("DARK");

            FXMLLoader settingSceneLoader = new FXMLLoader(getClass().getResource(VIEWS_PATH + "SettingScene.fxml"));
            root = settingSceneLoader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            ApplicationColorController.setColor(scene);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void handleRating(@SuppressWarnings("exports") MouseEvent event) {
        System.out.println(rating.getRating());
        String labelString = "User Rating: " + 5.0;
        if (rating.getRating() == 5) labelString += " thank you";
        else labelString += "   nope, cant be anything but 5";
        if (rating.getRating() != 5) rating.setRating(5);
        ratingLabel.setText(labelString);
    }

    @FXML
    public void handleCloseButtonAction(@SuppressWarnings("exports") ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
