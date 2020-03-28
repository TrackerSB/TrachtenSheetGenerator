package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ScreenController {
    private Main mainApp;

    @Nullable
    protected Main getMainApp() {
        return mainApp;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @NotNull
    protected void switchTo(Screen nextScreen) throws ScreenSwitchFailedException {
        if (getMainApp() == null) {
            throw new IllegalStateException(
                    "Can not switch screen since there is no reference to the main application");
        } else {
            getMainApp().switchTo(nextScreen);
        }
    }
}
