module de.traunviertler_traunwalchen.TrachtenSheetGenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires bayern.steinbrecher.Wizard;

    exports de.traunviertler_traunwalchen.TrachtenSheetGenerator.Gui;

    opens de.traunviertler_traunwalchen.TrachtenSheetGenerator.Gui to javafx.fxml;
}