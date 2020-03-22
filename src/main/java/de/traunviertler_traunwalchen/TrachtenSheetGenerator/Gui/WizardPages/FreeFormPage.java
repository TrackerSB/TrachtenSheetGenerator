package de.traunviertler_traunwalchen.TrachtenSheetGenerator.Gui.WizardPages;

import bayern.steinbrecher.wizard.WizardableView;

import java.util.Optional;
import java.util.ResourceBundle;

public class FreeFormPage extends WizardableView<Optional<String>, FreeFormPageController> {

    public FreeFormPage() {
    }

    @Override
    protected void afterControllerInitialized() {
    }

    @Override
    protected Optional<ResourceBundle> getResourceBundle(){
        return Optional.empty();
    }

    @Override
    protected String getWizardFxmlPath() {
        return "FreeFormPage.fxml";
    }
}
