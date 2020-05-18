package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class TempFileGenerator {
    private static final Logger LOGGER = Logger.getLogger(TempFileGenerator.class.getName());
    private static final String DEFAULT_PREFIX = "TrachtenSheetGenerator_";

    private TempFileGenerator() {
    }

    private static void attachAutomaticDeletion(Path path) {
        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    try {
                        Files.delete(path);
                    } catch (IOException ex) {
                        LOGGER.log(Level.INFO, "Could not delete temporary file or directory", ex);
                    }
                }));
    }

    /**
     * NOTE The returned path is guaranteed to be allowed for tex files.
     */
    @NotNull
    public static Path createTempFile(@Nullable Path dir, @Nullable String suffix) throws IOException {
        Path tempFile = (dir == null) ? Files.createTempFile(DEFAULT_PREFIX, suffix)
                : Files.createTempFile(dir, DEFAULT_PREFIX, suffix);
        attachAutomaticDeletion(tempFile);
        // FIXME Return TEX compatible file
        // String texCompatiblePath = SystemCommandUtility.resolvePath(tempFile)
        //         .orElseThrow(() -> new IOException("Could not resolve TEX compatible path"));
        // return Paths.get(texCompatiblePath);
        return tempFile;
    }

    @NotNull
    public static Path createTempDir() throws IOException {
        Path tempDirPath = Files.createTempDirectory(DEFAULT_PREFIX);
        attachAutomaticDeletion(tempDirPath);
        return tempDirPath;
    }
}
