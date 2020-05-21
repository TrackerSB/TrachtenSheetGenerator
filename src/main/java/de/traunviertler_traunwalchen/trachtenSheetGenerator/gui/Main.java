package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens.MainMenu;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens.ScreenManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public void start(@NotNull Stage stage) throws ScreenSwitchFailedException {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            LOGGER.log(Level.SEVERE, "Encountered uncaught exception on " + thread, throwable);
            // FIXME Show exception dialog
        });
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
