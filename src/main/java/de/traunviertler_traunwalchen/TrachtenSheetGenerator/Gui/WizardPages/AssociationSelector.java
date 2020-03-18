package de.traunviertler_traunwalchen.TrachtenSheetGenerator.Gui.WizardPages;

import bayern.steinbrecher.wizard.WizardableView;
import de.traunviertler_traunwalchen.TrachtenSheetGenerator.Model.Associations;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.List;
import java.util.Optional;

public class AssociationSelector extends WizardableView<Optional<Associations>, AssociationSelectorController> {

    private static final ReadOnlyListProperty<Associations> associations =
            new SimpleListProperty<>(
                    FXCollections.observableList(List.of(Associations.values()))
            );

    public AssociationSelector() {
    }

    @Override
    protected String getWizardFxmlPath() {
        return "AssociationsSelector.fxml";
    }
}
