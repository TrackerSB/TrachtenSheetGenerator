package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.LetterData;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.ReceivingAssociation;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.SendingAssociation;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class LetterGenerator {
    private static final Configuration TEMPLATE_ENGINE_CONFIGURATION = new Configuration(Configuration.VERSION_2_3_30) {
        {
            try {
                setDirectoryForTemplateLoading(new File(LetterGenerator.class.getResource("templates").getFile()));
            } catch (IOException ex) {
                throw new ExceptionInInitializerError(ex);
            }
            setDefaultEncoding("UTF-8");
            setOutputFormat(TexOutputFormat.INSTANCE);
            setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            setLogTemplateExceptions(false);
            setWrapUncheckedExceptions(true);
            setFallbackOnNullLoopVariable(false);
            setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
            setInterpolationSyntax(Configuration.SQUARE_BRACKET_INTERPOLATION_SYNTAX);
        }
    };
    private static final Template EMPTY_LETTER;

    static { // Initialize templates
        try {
            EMPTY_LETTER = TEMPLATE_ENGINE_CONFIGURATION.getTemplate("emptyLetter.tex");
        } catch (IOException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private LetterGenerator() {
    }

    @NotNull
    public static Path from(@NotNull Iterable<ReceivingAssociation> receivers, @NotNull LetterData letterData)
            throws GenerationFailedException {
        try {
            Path tempFilePath = TempFileGenerator.createTempFile(".tex");
            EMPTY_LETTER.process(Map.of(
                    "sender", SendingAssociation.TRAUNVIERTLER,
                    "letter", letterData,
                    "receivers", receivers
            ), Files.newBufferedWriter(tempFilePath));
            return tempFilePath;
        } catch (TemplateException | IOException ex) {
            throw new GenerationFailedException("Could not generate template", ex);
        }
    }
}
