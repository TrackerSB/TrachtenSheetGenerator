package de.traunviertler_traunwalchen.TrachtenSheetGenerator.Gui.WizardPages;

import bayern.steinbrecher.wizard.WizardableController;
import de.traunviertler_traunwalchen.TrachtenSheetGenerator.Model.Associations;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.Optional;

public class AssociationSelectorController extends WizardableController<Optional<Associations>> {

    @FXML
    private ComboBox<Associations> associationsSelector;

    public AssociationSelectorController() {
    }

    @FXML
    private void initialize(){
    }

    @Override
    protected Optional<Associations> calculateResult() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
