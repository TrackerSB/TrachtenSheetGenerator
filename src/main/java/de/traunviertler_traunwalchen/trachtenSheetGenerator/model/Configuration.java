package de.traunviertler_traunwalchen.trachtenSheetGenerator.model;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class Configuration {
    private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());
    private static final Configuration instance = new Configuration();
    private final Preferences userPreferences = Preferences.userNodeForPackage(Configuration.class);

    private Configuration() {
    }

    @NotNull
    public static Configuration getInstance(){
        return instance;
    }

    @NotNull
    public String getSenderName() {
        return userPreferences.get("senderName", "");
    }

    public void setSenderName(@NotNull String senderName) {
        userPreferences.put("senderName", senderName);
    }

    @NotNull
    public String getSenderAddressStreet() {
        return userPreferences.get("senderAddressStreet", "");
    }

    public void setSenderAddressStreet(@NotNull String senderAddressStreet) {
        userPreferences.put("senderAddressStreet", senderAddressStreet);
    }

    @NotNull
    public String getSenderAddressPlace() {
        return userPreferences.get("senderAddressPlace", "");
    }

    public void setSenderAddressPlace(@NotNull String senderAddressPlace) {
        userPreferences.put("senderAddressPlace", senderAddressPlace);
    }

    @NotNull
    public String getSenderEmail() {
        return userPreferences.get("senderEmail", "");
    }

    public void setSenderEmail(@NotNull String senderEmail) {
        userPreferences.put("senderEmail", senderEmail);
    }

    @NotNull
    public String getSenderPhone() {
        return userPreferences.get("senderPhone", "");
    }

    public void setSenderPhone(@NotNull String senderPhone) {
        userPreferences.put("senderPhone", senderPhone);
    }

    @NotNull
    public SendingAssociation getSender() {
        return new SendingAssociation(getSenderName(), getSenderAddressStreet(), getSenderAddressPlace(),
                getSenderEmail(), getSenderPhone());
    }
}
