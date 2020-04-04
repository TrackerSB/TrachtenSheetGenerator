package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

public class PDFGenerationFailedException extends Exception {
    public PDFGenerationFailedException() {
    }

    public PDFGenerationFailedException(String message) {
        super(message);
    }

    public PDFGenerationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PDFGenerationFailedException(Throwable cause) {
        super(cause);
    }

    public PDFGenerationFailedException(String message, Throwable cause, boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
