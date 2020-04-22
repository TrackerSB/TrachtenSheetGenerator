package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.utility.ResourceUtility;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.utility.SystemCommandFailedException;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.utility.SystemCommandUtility;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PDFGenerator {
    private static final Logger LOGGER = Logger.getLogger(PDFGenerator.class.getName());

    private PDFGenerator() {
    }

    /**
     * Generates a PDF from a TEX file. The PDF as well as all temporary files which the generation process yields
     * are placed right next to the input file and have the same name apart from the input files file extension.
     * TODO Delete all temporary files except the actual PDF.
     */
    public static Path compile(@NotNull Path texInputPath)
            throws GenerationFailedException {
        if (!Files.isRegularFile(texInputPath)) {
            throw new IllegalArgumentException("The input path must be a file");
        }

        if (SystemCommandUtility.isCommandAvailable("latexmk")) {
            String normalizedTexInputPath = texInputPath.normalize().toString();
            int fileExtensionSeparationIndex = normalizedTexInputPath.lastIndexOf('.');
            String jobname;
            if (fileExtensionSeparationIndex > -1) { // Strip file extension if existent
                jobname = normalizedTexInputPath.substring(0, fileExtensionSeparationIndex);
            } else {
                jobname = normalizedTexInputPath;
            }

            List<String> command = List.of("latexmk", "-nobibtex", "-norc", "-pdf",
                    String.format("-jobname=%s", jobname), texInputPath.toString()); // FIXME Use LuaTex
            Path compileScript;
            try {
                compileScript = ResourceUtility.getResource("scripts/compileTex.ps1");
            } catch (FileNotFoundException ex) {
                throw new GenerationFailedException(ex);
            }

            try {
                Pair<Boolean, String> result = SystemCommandUtility.executePowerShellScript(
                        // NOTE The PDF path relies on the naming as done by latexmk
                        stdOut -> new Pair<>(true, jobname + ".pdf"),
                        (stdOut, stdErr) -> new Pair<>(false, String.format("Latexmk failed (%s)", stdErr)),
                        () -> new Pair<>(false, "Latexmk timed out"), compileScript,
                        List.of(texInputPath.getParent().toString(), texInputPath.getFileName().toString()));
                if (result.getKey()) {
                    return Path.of(result.getValue());
                } else {
                    LOGGER.log(Level.WARNING, String.format(
                            "The generation of a PDF failed. Its error output is:\n%s", result.getValue()));
                    throw new GenerationFailedException();
                }
            } catch (SystemCommandFailedException ex) {
                throw new GenerationFailedException(
                        String.format("Could not compile \"%s\"", texInputPath.toString()), ex);
            }
        } else {
            throw new GenerationFailedException("Latexmk is not found");
        }
    }
}
