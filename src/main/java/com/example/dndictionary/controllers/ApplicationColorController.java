package com.example.dndictionary.controllers;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class ApplicationColorController extends Controller {
    public static void setColor(Scene scene) {
        String VIEWS_PATH = "/com/example/dndictionary/views/";

        if (SettingSceneController.theme.equals("LIGHT")) {
            String css = null;
            if (SettingSceneController.accentColor.equals("BLUE")) {
                css = ApplicationColorController.class.getResource(VIEWS_PATH + "lightBlue.css").toExternalForm();
                scene.setFill(Color.TRANSPARENT);
            } else if (SettingSceneController.accentColor.equals("ORANGE")) {
                css = ApplicationColorController.class.getResource(VIEWS_PATH + "lightOrange.css").toExternalForm();
                scene.setFill(Color.TRANSPARENT);
            } else {
                css = ApplicationColorController.class.getResource(VIEWS_PATH + "lightGreen.css").toExternalForm();
                scene.setFill(Color.TRANSPARENT);
            }
            System.out.println("using light theme");
            scene.getStylesheets().add(css);
        } else if (SettingSceneController.theme.equals("DARK")) {
            String css = null;
            if (SettingSceneController.accentColor.equals("BLUE")) {
                css = ApplicationColorController.class.getResource(VIEWS_PATH + "darkBlue.css").toExternalForm();
                scene.setFill(Color.TRANSPARENT);
            } else if (SettingSceneController.accentColor.equals("ORANGE")) {
                css = ApplicationColorController.class.getResource(VIEWS_PATH + "darkOrange.css").toExternalForm();
                scene.setFill(Color.TRANSPARENT);
            } else {
                css = ApplicationColorController.class.getResource(VIEWS_PATH + "darkGreen.css").toExternalForm();
                scene.setFill(Color.TRANSPARENT);
            }
            System.out.println("using dark theme");
            scene.getStylesheets().add(css);
        }
        scene.setFill(Color.TRANSPARENT);
    }
}
