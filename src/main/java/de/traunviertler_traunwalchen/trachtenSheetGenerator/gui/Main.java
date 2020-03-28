package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"),
                ResourceBundle.getBundle("de.traunviertler_traunwalchen.TrachtenSheetGenerator.gui.MainMenu"));
        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("icons/logo.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
