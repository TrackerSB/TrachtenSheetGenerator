package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui;

public class ScreenSwitchFailedException extends Exception {
    public ScreenSwitchFailedException() {
    }

    public ScreenSwitchFailedException(String message) {
        super(message);
    }

    public ScreenSwitchFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScreenSwitchFailedException(Throwable cause) {
        super(cause);
    }

    public ScreenSwitchFailedException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
