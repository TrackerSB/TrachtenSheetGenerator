package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.generators.WizardGenerator;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens.MainMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private Stage primaryStage;
    private final StackPane screenStack = new StackPane();
    private final Scene primaryScene = new Scene(screenStack);
    private WizardGenerator wizardGenerator;

    @Override
    public void start(Stage stage) throws ScreenSwitchFailedException {
        primaryStage = stage;
        primaryStage.setFullScreen(true);
        primaryStage.getIcons().addAll(new Image(getClass().getResourceAsStream("icons/logo.png")));
        primaryStage.setScene(primaryScene);
        switchTo(new MainMenu());
        primaryStage.show();
    }

    public void switchTo(Screen<?> nextScreen) throws ScreenSwitchFailedException {
        try {
            screenStack.getChildren().add(nextScreen.create(this));
        } catch (IOException ex) {
            throw new ScreenSwitchFailedException(ex);
        }
    }

    public void switchBack() {
        int numberOfScreens = screenStack.getChildren().size();
        if (numberOfScreens < 2) {
            LOGGER.log(Level.WARNING, "There is no previous screen to switch to");
        } else {
            screenStack.getChildren().remove(numberOfScreens - 1);
        }
    }

    @NotNull
    public WizardGenerator getWizardGenerator() {
        if (wizardGenerator == null) {
            if (primaryStage == null) {
                throw new IllegalStateException("start(...) must be called before this method.");
            } else {
                wizardGenerator = new WizardGenerator(primaryStage);
            }
        }
        return wizardGenerator;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
