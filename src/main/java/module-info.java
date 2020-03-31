module de.traunviertler_traunwalchen.TrachtenSheetGenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.prefs;
    requires bayern.steinbrecher.GenericWizard;
    requires freemarker;
    requires org.jetbrains.annotations;

    exports de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

    opens de.traunviertler_traunwalchen.trachtenSheetGenerator.gui to javafx.fxml;
    opens de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens to javafx.fxml;
    opens de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.wizardPages to javafx.fxml, bayern.steinbrecher.GenericWizard;
    opens de.traunviertler_traunwalchen.trachtenSheetGenerator.model to freemarker;
}