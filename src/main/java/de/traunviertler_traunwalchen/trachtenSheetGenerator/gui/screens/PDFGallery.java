package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.ResourceBundle;

public class PDFGallery extends Screen<PDFGalleryController> {
    private final Collection<Path> pdfFilePaths;

    public PDFGallery(@NotNull Collection<Path> pdfFilePaths) {
        super(PDFGallery.class.getResource("PDFGallery.fxml"),
                ResourceBundle.getBundle(
                        "de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens.PDFGallery"));
        this.pdfFilePaths = pdfFilePaths;
    }

    @Override
    protected void afterControllerIsInitialized(@NotNull PDFGalleryController controller) {
        super.afterControllerIsInitialized(controller);
        controller.setPdfFilePaths(pdfFilePaths);
    }
}
