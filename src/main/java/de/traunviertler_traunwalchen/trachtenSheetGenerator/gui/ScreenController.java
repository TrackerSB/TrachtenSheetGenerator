package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

import org.jetbrains.annotations.NotNull;

public abstract class ScreenController {
    private Main mainApp;

    @NotNull
    protected Main getMainApp() {
        if(mainApp == null){
            throw new IllegalStateException("The main app reference was not set yet.");
        } else {
            return mainApp;
        }
    }

    public void setMainApp(@NotNull Main mainApp) {
        this.mainApp = mainApp;
    }
}
