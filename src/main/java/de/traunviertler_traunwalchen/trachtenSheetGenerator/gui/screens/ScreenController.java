package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens.ScreenManager;
import javafx.fxml.FXML;
import org.jetbrains.annotations.NotNull;

public abstract class ScreenController {
    private ScreenManager screenManager;

    @NotNull
    protected ScreenManager getScreenManager() {
        if(screenManager == null){
            throw new IllegalStateException("The main app reference was not set yet.");
        } else {
            return screenManager;
        }
    }

    public void setScreenManager(@NotNull ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @FXML
    private void switchBack(){
        getScreenManager().switchBack();
    }
}
