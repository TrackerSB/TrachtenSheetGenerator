package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.generators.PDFGenerationFailedException;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.generators.PDFGenerator;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.generators.TempFileGenerator;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.ScreenSwitchFailedException;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.Configuration;
import javafx.fxml.FXML;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuController extends ScreenController {

    private static final Logger LOGGER = Logger.getLogger(MainMenuController.class.getName());

    @FXML
    private void startFreeLetterWizard() {
        getScreenManager()
                .getWizardGenerator()
                .showFreeLetterWizard()
                .map(
                        letters -> letters.values()
                                .stream()
                                .map(inputPath -> {
                                    try {
                                        return PDFGenerator.compile(inputPath);
                                    } catch (PDFGenerationFailedException ex) {
                                        LOGGER.log(Level.WARNING, null, ex);
                                        return null;
                                    }
                                })
                                .filter(Objects::nonNull)
                )
                .ifPresent(outputPaths -> {
                    System.out.println("Generated PDF files:");
                    outputPaths.forEach(path -> System.out.println(path.normalize().toString()));
                });
    }

    @FXML
    private void showSettingsDialog() {
        try {
            getScreenManager().switchTo(new ConfigurationMenu(Configuration.getInstance()));
        } catch (ScreenSwitchFailedException ex) {
            LOGGER.log(Level.WARNING, "Could not switch to configuration menu", ex);
        }
    }
}
