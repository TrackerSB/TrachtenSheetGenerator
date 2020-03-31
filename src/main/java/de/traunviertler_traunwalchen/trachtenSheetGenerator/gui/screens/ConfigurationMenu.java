package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.Configuration;

import java.util.ResourceBundle;

public class ConfigurationMenu extends Screen<ConfigurationMenuController> {
    private Configuration initialConfiguration;

    public ConfigurationMenu(Configuration initialConfiguration) {
        super(ConfigurationMenu.class.getResource("ConfigurationMenu.fxml"),
                ResourceBundle.getBundle(
                        "de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens.ConfigurationMenu"));
        this.initialConfiguration = initialConfiguration;
    }

    @Override
    protected void afterControllerIsInitialized(ConfigurationMenuController controller) {
        super.afterControllerIsInitialized(controller);
        controller.setConfiguration(initialConfiguration);
    }
}
