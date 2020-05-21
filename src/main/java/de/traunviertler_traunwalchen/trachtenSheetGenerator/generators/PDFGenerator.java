package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.utility.Convenience;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.utility.SystemCommandExecutor;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.utility.SystemCommandFailedException;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

public final class PDFGenerator {

    private PDFGenerator() {
    }

    /**
     * Generates a PDF from a TEX file. The PDF as well as all temporary files which the generation process yields
     * are placed right next to the input file and have the same name apart from the input files file extension.
     */
    public static Path compile(@NotNull Path texInputPath)
            throws GenerationFailedException {
        if (!Files.isRegularFile(texInputPath)) {
            throw new IllegalArgumentException("The input path must be a file");
        }

        String jobname = Convenience.invoke(() -> {
            String filename = texInputPath.getFileName().toString();
            int fileExtensionSeparationIndex = filename.lastIndexOf('.');
            if (fileExtensionSeparationIndex > -1) { // Strip file extension if existent
                return filename.substring(0, fileExtensionSeparationIndex);
            } else {
                return filename;
            }
        });
        String resolvedTexInputPath;
        try {
            resolvedTexInputPath = SystemCommandExecutor.resolveSystemPath(texInputPath.toString());
        } catch (SystemCommandFailedException ex) {
            throw new GenerationFailedException("Could not convert the input path to a latexmk compatible path", ex);
        }
        String compileCommand = String.format(
                "latexmk -nobibtex -norc -pdf -jobname='%s' '%s'", jobname, resolvedTexInputPath);
        Path workingDir = texInputPath.getParent();
        Pair<Boolean, String> result;
        try {
            result = SystemCommandExecutor.executeSystemCommand(compileCommand, workingDir,
                    stdOut -> new Pair<>(true, jobname + ".pdf"),
                    (stdOut, stdErr) -> new Pair<>(false, String.format("Latexmk failed (%s)", stdErr)),
                    () -> new Pair<>(false, "Latexmk timed out"));
        } catch (SystemCommandFailedException ex) {
            throw new GenerationFailedException(ex);
        }
        if (result.getKey()) {
            return Path.of(result.getValue());
        } else {
            throw new GenerationFailedException(result.getValue());
        }
    }
}
