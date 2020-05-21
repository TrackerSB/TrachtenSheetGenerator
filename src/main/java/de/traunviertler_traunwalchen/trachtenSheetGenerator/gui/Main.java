package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

import bayern.steinbrecher.utility.DialogCreationException;
import bayern.steinbrecher.utility.DialogUtility;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens.MainMenu;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens.ScreenManager;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public void start(@NotNull Stage stage) throws ScreenSwitchFailedException {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            LOGGER.log(Level.SEVERE, "Encountered uncaught exception on " + thread, throwable);
            try {
                Alert alert = DialogUtility.createStacktraceAlert(throwable);
                alert.initOwner(stage);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initStyle(StageStyle.UTILITY);
                alert.showAndWait();
            } catch (DialogCreationException ex) {
                LOGGER.log(Level.WARNING, "Could not show the exception to the user.", ex);
            }
        });
        stage.maximizedProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal) {
                        stage.setFullScreen(true);
                    }
                });
        stage.fullScreenProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (!newVal) {
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
