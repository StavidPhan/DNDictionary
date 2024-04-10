package com.example.dndictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * Class chạy bằng giao diện.
 */
public class DNDictionary extends Application {
    public Stage stage;

    public Scene scene;
    private Parent root;

    /**
     * The start function to run application with FXML files
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages.
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            String viewsPath = "/com/example/dndictionary/views/";
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(viewsPath + "WelcomeScene.fxml")));

            Scene scene = new Scene(root);
            stage.setTitle(Utilities.APP_TITLE);
            stage.setWidth(Utilities.APP_WIDTH);
            stage.setHeight(Utilities.APP_HEIGHT);
            stage.setResizable(false);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);

            String css = this.getClass().getResource(viewsPath + "lightOrange.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setScene(scene);

            Image icon = new Image("file:" + Utilities.PATH_TO_ICON);
            stage.getIcons().add(icon);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
