package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.Association;
import de.traunviertler_traunwalchen.trachtenSheetGenerator.model.LetterData;

import java.util.Map;
import java.util.logging.Logger;

public class LetterGenerator {
    private static final Logger LOGGER = Logger.getLogger(LetterGenerator.class.getName());

    private LetterGenerator() {
    }

    public static Map<Association, String> from(Iterable<Association> receivers, LetterData letterData){
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
