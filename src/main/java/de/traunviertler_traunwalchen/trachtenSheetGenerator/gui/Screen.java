package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class Screen<C extends ScreenController> {

    private final URL fxmlPath;
    private final ResourceBundle bundle;

    public Screen(@NotNull URL fxmlPath, @NotNull ResourceBundle bundle) {
        this.fxmlPath = fxmlPath;
        this.bundle = bundle;
    }

    @NotNull
    public Parent create(@NotNull Main mainApp) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlPath, bundle);
        Parent root = fxmlLoader.load();
        root.getStyleClass()
                .add("screen");
        C controller = fxmlLoader.getController();
        controller.setMainApp(mainApp);
        afterControllerIsInitialized(controller);
        return root;
    }

    protected void afterControllerIsInitialized(C controller) {
    }
}
