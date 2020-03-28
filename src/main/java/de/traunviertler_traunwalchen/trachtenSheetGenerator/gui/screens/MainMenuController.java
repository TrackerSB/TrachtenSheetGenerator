package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.ScreenController;
import javafx.fxml.FXML;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuController extends ScreenController {

    private static final Logger LOGGER = Logger.getLogger(MainMenuController.class.getName());

    @FXML
    private void startFreeLetterWizard() {
        getMainApp()
                .getWizardGenerator()
                .showFreeLetterWizard()
                .ifPresentOrElse(
                        letters -> LOGGER.log(Level.INFO, "The generated letters are not getting processed yet."),
                        () -> LOGGER.log(Level.INFO, "The wizard did not yield any letters.")
                );
    }
}