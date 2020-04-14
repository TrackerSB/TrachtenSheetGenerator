package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

import freemarker.core.CommonMarkupOutputFormat;
import freemarker.template.TemplateModelException;
import javafx.util.Pair;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.regex.Pattern;

public class TexOutputFormat extends CommonMarkupOutputFormat<TemplateTexOutputModel> {
    public static final TexOutputFormat INSTANCE = new TexOutputFormat();
    private static final List<Pair<Pattern, String>> ESCAPE_PATTERNS = List.of(
            new Pair<>(Pattern.compile("\"(.*)\""), "\\\\textquote{$1}") // Escape quotations // Requires "csquotes"
    );

    private TexOutputFormat() {
    }

    @Override
    public void output(String textToEsc, Writer out) throws IOException, TemplateModelException {
        out.append(escapePlainText(textToEsc));
    }

    @Override
    public String escapePlainText(String plainTextContent) throws TemplateModelException {
        // Escape single characters
        StringBuilder escapedTextContent = new StringBuilder();
        for (int i = 0; i < plainTextContent.length(); i++) {
            char unescaped = plainTextContent.charAt(i);
            // FIXME Define a union type String|char? Converting each char to a string might be inefficient.
            Object escaped;
            switch (unescaped) {
            case '\\':
                escaped = "\\\\";
                break;
            case '$':
                escaped = "\\$";
                break;
            default:
                escaped = unescaped;
            }
            escapedTextContent.append(escaped);
        }
        String escapedText = escapedTextContent.toString();

        // Escape/Replace patterns
        for (Pair<Pattern, String> escapePattern : ESCAPE_PATTERNS) {
            escapedText = escapePattern.getKey()
                    .matcher(escapedText)
                    .replaceAll(escapePattern.getValue());
        }

        return escapedText;
    }

    @Override
    public boolean isLegacyBuiltInBypassed(String builtInName) {
        return false;
    }

    @Override
    protected TemplateTexOutputModel newTemplateMarkupOutputModel(String plainTextContent, String markupContent) {
        return new TemplateTexOutputModel(plainTextContent, markupContent);
    }

    @Override
    public String getName() {
        return "TEX";
    }

    @Override
    public String getMimeType() {
        return "application/x-tex";
    }
}
