package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.wizardPages;

import bayern.steinbrecher.wizard.WizardableView;

import java.util.Optional;
import java.util.ResourceBundle;

public class FreeFormPage extends WizardableView<Optional<String>, FreeFormPageController> {

    public FreeFormPage() {
        super("FreeFormPage.fxml",
                ResourceBundle.getBundle(
                        "de.traunviertler_traunwalchen.TrachtenSheetGenerator.Gui.WizardPages.FreeFormPage"));
    }

    @Override
    protected void afterControllerInitialized() {
    }
}
