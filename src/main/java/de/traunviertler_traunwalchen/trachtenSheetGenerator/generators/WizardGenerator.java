package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

import bayern.steinbrecher.wizard.Wizard;
import bayern.steinbrecher.wizard.WizardPage;
import bayern.steinbrecher.wizard.pages.Selection;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.gui.wizardPages.FreeLetterPage;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.LetterData;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.ReceivingAssociation;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WizardGenerator {
    private static final Logger LOGGER = Logger.getLogger(WizardGenerator.class.getName());
    private final Stage owner;

    public WizardGenerator(Stage owner) {
        this.owner = owner;
    }

    private <R> Optional<R> showWizard(ThrowingCallable<Map<String, WizardPage<?>>, IOException> pageGenerator,
                                       Function<Map<String, ?>, R> resultFunction) {
        Supplier<Optional<Map<String, WizardPage<?>>>> safePageGenerator = () -> {
            try {
                return Optional.of(pageGenerator.call());
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Could not generate pages for wizard.", ex);
                return Optional.empty();
            }
        };
        return safePageGenerator.get()
                .map(Wizard::new)
                .map(wizard -> {
                    Stage stage = new Stage();
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initStyle(StageStyle.UTILITY);
                    stage.initOwner(owner);
                    try {
                        wizard.start(stage);
                        return new Pair<>(wizard, stage);
                    } catch (IOException ex) {
                        LOGGER.log(Level.SEVERE, "Could not start wizard", ex);
                        return null;
                    }
                })
                .map(pair -> {
                    pair.getValue().showAndWait();
                    Optional<Map<String, ?>> results = pair.getKey().getResults();
                    if (pair.getKey().isFinished() && results.isPresent()) {
                        return resultFunction.apply(results.get());
                    } else {
                        return null;
                    }
                });
    }

    @NotNull
    public Optional<Path> showFreeLetterWizard() {
        ThrowingCallable<Map<String, WizardPage<?>>, IOException> pageGenerator = () -> {
            WizardPage<Optional<Set<ReceivingAssociation>>> receiversPage
                    = new Selection<>(ReceivingAssociation.RECEIVERS)
                    .getWizardPage();
            receiversPage.setNextFunction(() -> "letterDataPage");
            WizardPage<Optional<LetterData>> letterDataPage = new FreeLetterPage().getWizardPage();
            letterDataPage.setFinish(true);
            return Map.of(
                    WizardPage.FIRST_PAGE_KEY, receiversPage,
                    "letterDataPage", letterDataPage
            );
        };

        Function<Map<String, ?>, Path> resultFunction = wizardResults -> {
            Path generatedTexFilePath;
            if (wizardResults.containsKey(WizardPage.FIRST_PAGE_KEY)
                    && wizardResults.containsKey("letterDataPage")) {
                Optional<Set<ReceivingAssociation>> receivers
                        = (Optional<Set<ReceivingAssociation>>) wizardResults.get(WizardPage.FIRST_PAGE_KEY);
                Optional<LetterData> letterData = (Optional<LetterData>) wizardResults.get("letterDataPage");
                if (receivers.isPresent() && letterData.isPresent()) {
                    try {
                        generatedTexFilePath = LetterGenerator.from(receivers.get(), letterData.get());
                    } catch (GenerationFailedException ex) {
                        LOGGER.log(Level.SEVERE, "The generation of TEX files failed", ex);
                        generatedTexFilePath = null;
                    }
                } else {
                    LOGGER.log(Level.WARNING,
                            "The FreeFormWizard did not yield all required data. No letters are generated.");
                    generatedTexFilePath = null;
                }
            } else {
                LOGGER.log(Level.WARNING,
                        "The FreeFormWizard did not show all required pages. No letters are generated");
                generatedTexFilePath = null;
            }
            return generatedTexFilePath;
        };

        return showWizard(pageGenerator, resultFunction);
    }

    @FunctionalInterface
    private interface ThrowingCallable<V, E extends Throwable> {
        V call() throws E;
    }
}
