package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

public abstract class ScreenController {
    private Main mainApp;

    protected Main getMainApp() {
        return mainApp;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    protected void switchTo(Screen nextScreen) throws ScreenSwitchFailedException {
        if (mainApp == null) {
            throw new IllegalStateException(
                    "Can not switch screen since there is no reference to the main application");
        } else {
            mainApp.switchTo(nextScreen);
        }
    }
}
