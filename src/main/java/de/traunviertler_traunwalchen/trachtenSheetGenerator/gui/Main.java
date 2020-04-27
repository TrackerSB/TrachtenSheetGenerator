package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens.MainMenu;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens.ScreenManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws ScreenSwitchFailedException {
        stage.maximizedProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if(newVal){
                        stage.setFullScreen(true);
                    }
                });
        stage.fullScreenProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if(!newVal){
                        stage.setMaximized(false);
                    }
                });
        stage.setFullScreen(true);
        stage.getIcons().addAll(new Image(getClass().getResourceAsStream("icons/logo.png")));
        ScreenManager screenManager = new ScreenManager(stage);
        screenManager.switchTo(new MainMenu());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
