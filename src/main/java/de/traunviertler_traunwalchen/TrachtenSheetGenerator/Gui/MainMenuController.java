package de.traunviertler_traunwalchen.TrachtenSheetGenerator.Gui;

import bayern.steinbrecher.wizard.Wizard;
import bayern.steinbrecher.wizard.WizardPage;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuController {

    private static final Logger LOGGER = Logger.getLogger(MainMenuController.class.getName());

    @FXML
    private void startWizard() {
        WizardPage<Void> finalPage = new WizardPage<Void>(new GridPane(), () -> null, true, () -> null);
        Wizard wizard = new Wizard(Map.of(WizardPage.FIRST_PAGE_KEY, finalPage));
        try {
            wizard.start(new Stage());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Could not open main menu wizard", ex);
        }
    }
}
