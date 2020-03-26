module de.traunviertler_traunwalchen.TrachtenSheetGenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires bayern.steinbrecher.GenericWizard;

    exports de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

    opens de.traunviertler_traunwalchen.trachtenSheetGenerator.gui to javafx.fxml;
    opens de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.wizardPages to javafx.fxml, bayern.steinbrecher.GenericWizard;
}