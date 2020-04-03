package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

final class TempFileUtility {
    private static final Logger LOGGER = Logger.getLogger(TempFileUtility.class.getName());
    private TempFileUtility(){}

    public static Path createTempFile() throws IOException {
        Path tempFilePath = Files.createTempFile("TrachtenSheetGenerator", ".tex");
        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    try {
                        Files.delete(tempFilePath);
                    } catch (IOException ex) {
                        LOGGER.log(Level.INFO, "Could not delete temporary file", ex);
                    }
                }));
        return tempFilePath;
    }
}
