package de.traunviertler_traunwalchen.trachtenSheetGenerator.utility;

public class SystemCommandFailedException extends Exception {
    public SystemCommandFailedException() {
    }

    public SystemCommandFailedException(String message) {
        super(message);
    }

    public SystemCommandFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemCommandFailedException(Throwable cause) {
        super(cause);
    }

    public SystemCommandFailedException(String message, Throwable cause, boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
