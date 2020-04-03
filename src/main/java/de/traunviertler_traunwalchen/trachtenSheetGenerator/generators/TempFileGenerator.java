package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class TempFileGenerator {
    private static final Logger LOGGER = Logger.getLogger(TempFileGenerator.class.getName());

    private TempFileGenerator() {
    }

    public static Path createTempFile(@Nullable String suffix) throws IOException {
        Path tempFilePath = Files
                .createTempFile("TrachtenSheetGenerator_", suffix);
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
