package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

public class GenerationFailedException extends Exception {
    public GenerationFailedException() {
    }

    public GenerationFailedException(String message) {
        super(message);
    }

    public GenerationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenerationFailedException(Throwable cause) {
        super(cause);
    }

    public GenerationFailedException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
