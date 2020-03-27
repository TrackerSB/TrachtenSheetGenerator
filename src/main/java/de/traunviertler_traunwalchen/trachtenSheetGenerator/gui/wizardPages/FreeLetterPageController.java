package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.wizardPages;

import bayern.steinbrecher.wizard.WizardableController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.Optional;

public class FreeFormPageController extends WizardableController<Optional<String>> {
    @FXML
    private TextArea freeFormContent;

    @Override
    protected Optional<String> calculateResult() {
        return Optional.of(freeFormContent.getText());
    }
}
