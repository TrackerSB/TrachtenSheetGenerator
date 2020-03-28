package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new MainMenu().create());
        stage.setFullScreen(true);
        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("icons/logo.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
