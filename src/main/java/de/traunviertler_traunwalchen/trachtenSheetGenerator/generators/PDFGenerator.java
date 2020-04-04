package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.utility.SystemCommandFailedException;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.utility.SystemCommandUtility;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

public final class PDFGenerator {
    private PDFGenerator() {
    }

    public static void compile(@NotNull Path texInputPath, @NotNull Path pdfOutputDir)
            throws PDFGenerationFailedException {
        if (!Files.isDirectory(pdfOutputDir)) {
            throw new IllegalArgumentException("The output path must be a directory");
        }
        if (!Files.isRegularFile(texInputPath)) {
            throw new IllegalArgumentException("The input path must be a file");
        }

        if (SystemCommandUtility.isCommandAvailable("latexmk")) {
            String compileCommandParams = "-nobibtex -pdf -jobname=\"" + pdfOutputDir.normalize().toString()
                    + "/Test\" " + texInputPath.toString();
            try {
                SystemCommandUtility.execute("latexmk", compileCommandParams, null);
            } catch (SystemCommandFailedException ex) {
                throw new PDFGenerationFailedException("Could not compile \"" + texInputPath.toString() + "\"", ex);
            }
        } else {
            throw new PDFGenerationFailedException("Latexmk is not found");
        }
    }
}
