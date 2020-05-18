package de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.screens;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PDFGalleryController extends ScreenController {

    private static final Logger LOGGER = Logger.getLogger(PDFGalleryController.class.getName());
    private static final String PDF_VIEWER_PATH
            = PDFGalleryController.class.getResource("/pdfjs2.3.200/web/viewer.html").toExternalForm();
    private final ReadOnlyBooleanWrapper onFirstSlide = new ReadOnlyBooleanWrapper(this, "onFirstSlide", true);
    private final ReadOnlyBooleanWrapper onLastSlide = new ReadOnlyBooleanWrapper(this, "onLastSlide", false);
    @FXML
    private StackPane pdfSlideStack; // NOTE A StackPane is used since this allows animation in a future version
    private final ListProperty<String> pdfContentUrls = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final IntegerProperty currentPdfPos = new SimpleIntegerProperty();

    public PDFGalleryController() {
    }

    @FXML
    private void initialize() {
        onFirstSlide.bind(currentPdfPos.lessThanOrEqualTo(0));
        onLastSlide.bind(currentPdfPos.greaterThanOrEqualTo(pdfContentUrls.sizeProperty()));
        currentPdfPos.addListener((obs, oldVal, newVal) -> {
            int contentUrlIndex = newVal.intValue();
            if (contentUrlIndex >= 0 && contentUrlIndex < pdfContentUrls.size()) {
                pdfSlideStack.getChildren()
                        .clear();
                Platform.runLater(() -> {
                    WebView pdfView = new WebView();
                    pdfView.getEngine()
                            .load(pdfContentUrls.get(contentUrlIndex));
                });
            } else {
                LOGGER.log(Level.WARNING, "Index " + contentUrlIndex + " does not exist.");
            }
        });
        pdfContentUrls.addListener((obs, oldVal, newVal) -> currentPdfPos.set(0));
        pdfContentUrls.emptyProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal) {
                        // TODO Show "no PDF" view
                    } else {
                        // TODO Hide "no PDF" view
                    }
                });
    }

    @FXML
    private void switchToPreviousSlide() {
        currentPdfPos.set(currentPdfPos.get() - 1);
    }

    @FXML
    private void switchToNextSlide() {
        currentPdfPos.set(currentPdfPos.get() + 1);
    }

    public void setPdfFilePaths(@NotNull Collection<Path> pdfFilePaths) {
        pdfContentUrls.clear();
        pdfFilePaths.stream()
                .map(Path::normalize)
                .map(Path::toString)
                // FIXME Why seems encoding as not required?
                //.map(path -> "/" + URLEncoder.encode(path, StandardCharsets.UTF_8))
                .map(path -> PDF_VIEWER_PATH + "?file=file:///" + path)
                .forEach(pdfContentUrls::add);
    }

    public boolean isOnFirstSlide() {
        return onFirstSlide.get();
    }

    public ReadOnlyBooleanProperty onFirstSlideProperty() {
        return onFirstSlide.getReadOnlyProperty();
    }

    public boolean isOnLastSlide() {
        return onLastSlide.get();
    }

    public ReadOnlyBooleanProperty onLastSlideProperty() {
        return onLastSlide.getReadOnlyProperty();
    }
}
