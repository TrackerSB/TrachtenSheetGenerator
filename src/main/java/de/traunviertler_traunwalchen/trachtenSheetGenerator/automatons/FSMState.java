package de.traunviertler_traunwalchen.trachtenSheetGenerator.automatons;

import javafx.util.Pair;

/**
 * NOTE This class is meant to be implemented by an enum class.
 */
public interface FSMState<S extends Enum<S>> {
    boolean isFinal();
    Pair<S, String> process(char transitionLetter);
}
