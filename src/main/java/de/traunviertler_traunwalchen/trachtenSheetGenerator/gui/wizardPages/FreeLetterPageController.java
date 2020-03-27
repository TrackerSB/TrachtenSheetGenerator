package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.wizardPages;

import bayern.steinbrecher.wizard.WizardableController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.Optional;

public class FreeLetterPageController extends WizardableController<Optional<String>> {
    @FXML
    private TextArea letterContent;

    @Override
    protected Optional<String> calculateResult() {
        return Optional.of(freeFormContent.getText());
    }
}
