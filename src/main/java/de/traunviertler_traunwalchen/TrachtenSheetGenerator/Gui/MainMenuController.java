package de.traunviertler_traunwalchen.TrachtenSheetGenerator.Gui;

import bayern.steinbrecher.wizard.Wizard;
import bayern.steinbrecher.wizard.WizardPage;
import bayern.steinbrecher.wizard.pages.Selection;
import de.traunviertler_traunwalchen.TrachtenSheetGenerator.Gui.WizardPages.FreeFormPage;
import de.traunviertler_traunwalchen.TrachtenSheetGenerator.Model.Association;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuController {

    private static final Logger LOGGER = Logger.getLogger(MainMenuController.class.getName());

    @FXML
    private void startFreeFormWizard() {
        try {
            WizardPage<Optional<Set<Association>>> associationPage
                    = new Selection<>(Association.ASSOCIATIONS)
                    .getWizardPage();
            associationPage.setNextFunction(() -> "freeFormPage");
            WizardPage<Optional<String>> freeFormPage = new FreeFormPage().getWizardPage();
            freeFormPage.setFinish(true);
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
