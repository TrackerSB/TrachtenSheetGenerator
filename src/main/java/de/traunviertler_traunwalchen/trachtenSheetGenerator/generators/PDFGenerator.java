package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.utility.SystemCommandFailedException;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.utility.SystemCommandUtility;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

public final class PDFGenerator {
    private PDFGenerator() {
    }

    /**
     * Generates a PDF from a TEX file. The PDF as well as all temporary files which the generation process yields
     * are placed right next to the input file and have the same name apart from the input files file extension.
     * TODO Delete all temporary files except the actual PDF.
     */
    public static Path compile(@NotNull Path texInputPath)
            throws PDFGenerationFailedException {
        if (!Files.isRegularFile(texInputPath)) {
            throw new IllegalArgumentException("The input path must be a file");
        }

        if (SystemCommandUtility.isCommandAvailable("latexmk")) {
            String jobname = texInputPath.normalize().toString();
            int fileExtensionSeparationIndex = jobname.lastIndexOf('.');
            if (fileExtensionSeparationIndex > -1) { // Strip file extension if existent
                jobname = jobname.substring(0, fileExtensionSeparationIndex);
            }

            String compileCommandParams
                    = String.format("-nobibtex -pdf -jobname=\"%s\" \"%s\"", jobname, texInputPath.toString());
            try {
                SystemCommandUtility.execute("latexmk", compileCommandParams, null);
                return Path.of(jobname + ".pdf"); // NOTE This relies on the naming as done by latexmk
            } catch (SystemCommandFailedException ex) {
                throw new PDFGenerationFailedException(
                        String.format("Could not compile \"%s\"", texInputPath.toString()), ex);
            }
        } else {
            throw new PDFGenerationFailedException("Latexmk is not found");
        }
    }
}
