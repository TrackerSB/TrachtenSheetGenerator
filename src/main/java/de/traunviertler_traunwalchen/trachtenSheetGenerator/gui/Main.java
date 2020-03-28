package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens.MainMenu;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class Main extends Application {
    private Stage primaryStage;
    private Scene primaryScene;
    private final Stack<Screen> screenHistory = new Stack<>();

    @Override
    public void start(Stage stage) throws ScreenSwitchFailedException {
        primaryStage = stage;
        switchTo(new MainMenu());
    }

    public void switchTo(Screen nextScreen) throws ScreenSwitchFailedException {
        Parent nextParent;
        try {
            nextParent = nextScreen.create(this);
        } catch (IOException ex) {
            throw new ScreenSwitchFailedException(ex);
        }
        if (primaryScene == null) {
            // Initialize stage
            primaryScene = new Scene(nextParent);
            primaryStage.setFullScreen(true);
            primaryStage.getIcons().addAll(new Image(getClass().getResourceAsStream("icons/logo.png")));
            primaryStage.setScene(primaryScene);
            primaryStage.show();
        } else {
            primaryScene.setRoot(nextParent);
        }
        screenHistory.push(nextScreen);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
