package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.wizardPages;

import bayern.steinbrecher.wizard.WizardableController;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.LetterData;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Optional;

public class FreeLetterPageController extends WizardableController<Optional<LetterData>> {
    @FXML
    private TextField subject;
    @FXML
    private TextField place;
    @FXML
    private TextField opening;
    @FXML
    private TextArea letterContent;
    @FXML
    private TextField closing;

    @Override
    protected Optional<LetterData> calculateResult() {
        return Optional.of(new LetterData(
                subject.getText(),
                place.getText(),
                opening.getText(),
                letterContent.getText(),
                closing.getText()
        ));
    }
}
