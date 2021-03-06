package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.wizardPages;

import bayern.steinbrecher.wizard.WizardableView;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.LetterData;

import java.util.Optional;
import java.util.ResourceBundle;

public class FreeLetterPage extends WizardableView<Optional<LetterData>, FreeLetterPageController> {

    public FreeLetterPage() {
        super("FreeLetterPage.fxml",
                ResourceBundle.getBundle(
                        "de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.wizardPages.FreeLetterPage"));
    }

    @Override
    protected void afterControllerInitialized() {
    }
}
