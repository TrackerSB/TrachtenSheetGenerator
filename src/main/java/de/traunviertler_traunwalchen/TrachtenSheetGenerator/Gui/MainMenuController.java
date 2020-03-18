package de.traunviertler_traunwalchen.TrachtenSheetGenerator.Gui;

import bayern.steinbrecher.wizard.Wizard;
import bayern.steinbrecher.wizard.WizardPage;
import de.traunviertler_traunwalchen.TrachtenSheetGenerator.Gui.WizardPages.AssociationSelector;
import de.traunviertler_traunwalchen.TrachtenSheetGenerator.Gui.WizardPages.FreeFormPage;
import de.traunviertler_traunwalchen.TrachtenSheetGenerator.Model.Associations;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuController {

    private static final Logger LOGGER = Logger.getLogger(MainMenuController.class.getName());

    @FXML
    private void startFreeFormWizard() {
        try {
            WizardPage<Optional<Associations>> associationPage = new AssociationSelector().getWizardPage();
            WizardPage<Optional<String>> freeFormPage = new FreeFormPage().getWizardPage();
            Stage wizardStage = new Stage();
            new Wizard(Map.of(
                    WizardPage.FIRST_PAGE_KEY, associationPage,
                    "freeFormPage", freeFormPage
            ))
                    .start(wizardStage);
            wizardStage.show();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Could not load free form wizard", ex);
        }
    }

    @FXML
    private void startWizard() {
        WizardPage<Void> finalPage = new WizardPage<Void>(new GridPane(), null, true, () -> null);
        Wizard wizard = new Wizard(Map.of(WizardPage.FIRST_PAGE_KEY, finalPage));
        Stage wizardStage = new Stage();
        try {
            wizard.start(wizardStage);
            wizardStage.show();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Could not open main menu wizard", ex);
        }
    }
}
