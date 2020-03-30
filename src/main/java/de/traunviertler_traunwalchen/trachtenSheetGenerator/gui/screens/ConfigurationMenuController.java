package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.ScreenController;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.Configuration;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ConfigurationMenuController extends ScreenController {
    private Configuration configuration;
    @FXML
    private TextField senderName;
    @FXML
    private TextField senderAddressStreet;
    @FXML
    private TextField senderAddressPlace;
    @FXML
    private TextField senderEmail;
    @FXML
    private TextField senderPhone;

    public ConfigurationMenuController() {
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        senderName.setText(configuration.getSenderName());
        senderAddressStreet.setText(configuration.getSenderAddressStreet());
        senderAddressPlace.setText(configuration.getSenderAddressPlace());
        senderEmail.setText(configuration.getSenderEmail());
        senderPhone.setText(configuration.getSenderPhone());
    }

    @FXML
    private void saveAndSwitchBack(){
        configuration.setSenderName(senderName.getText());
        configuration.setSenderAddressStreet(senderAddressStreet.getText());
        configuration.setSenderAddressPlace(senderAddressPlace.getText());
        configuration.setSenderEmail(senderEmail.getText());
        configuration.setSenderPhone(senderPhone.getText());
        getMainApp().switchBack();
    }
}
