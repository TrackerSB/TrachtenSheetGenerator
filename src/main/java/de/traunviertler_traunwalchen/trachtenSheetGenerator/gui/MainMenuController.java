package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.generators.WizardGenerator;
import javafx.fxml.FXML;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuController {

    private static final Logger LOGGER = Logger.getLogger(MainMenuController.class.getName());

    @FXML
    private void startFreeFormWizard() {
        WizardGenerator.showFreeFormWizard(null) // FIXME Set owning stage
                .ifPresentOrElse(
                        letters -> LOGGER.log(Level.INFO, "The generated letters are not getting processed yet."),
                        () -> LOGGER.log(Level.WARNING, "The wizard did not yield any letters.")
                );
    }
}
