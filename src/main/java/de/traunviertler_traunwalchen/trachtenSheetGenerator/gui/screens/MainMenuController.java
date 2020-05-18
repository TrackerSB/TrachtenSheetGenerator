package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.generators.GenerationFailedException;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.generators.PDFGenerator;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.ScreenSwitchFailedException;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.Configuration;
import javafx.fxml.FXML;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuController extends ScreenController {

    private static final Logger LOGGER = Logger.getLogger(MainMenuController.class.getName());

    @FXML
    private void startFreeLetterProcess() {
        getScreenManager()
                .getWizardGenerator()
                .showFreeLetterWizard()
                .map(letterPath -> {
                            try {
                                return PDFGenerator.compile(letterPath);
                            } catch (GenerationFailedException ex) {
                                LOGGER.log(Level.WARNING, null, ex);
                                return null;
                            }
                        }
                )
                .ifPresent(outputPath -> {
                    try {
                        getScreenManager()
                                .switchTo(new PDFGallery(List.of(outputPath)));
                    } catch (ScreenSwitchFailedException ex) {
                        LOGGER.log(Level.WARNING, "Could not load preview for generated PDF files.", ex);
                    }
                });
    }

    @FXML
    private void showConfigurationDialog() {
        try {
            getScreenManager()
                    .switchTo(new ConfigurationMenu(Configuration.getInstance()));
        } catch (ScreenSwitchFailedException ex) {
            LOGGER.log(Level.WARNING, "Could not switch to configuration menu", ex);
        }
    }
}
