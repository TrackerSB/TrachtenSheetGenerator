package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class Screen {

    private final URL fxmlPath;
    private final ResourceBundle bundle;
    private /*final*/ ScreenController controller;

    public Screen(URL fxmlPath, ResourceBundle bundle) {
        this.fxmlPath = fxmlPath;
        this.bundle = bundle;
    }

    public Parent create(Main mainApp) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlPath, bundle);
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        controller.setMainApp(mainApp);
        return root;
    }
}
