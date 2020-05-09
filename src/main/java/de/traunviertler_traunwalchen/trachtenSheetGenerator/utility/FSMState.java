package de.traunviertler_traunwalchen.trachtenSheetGenerator.utility;

import javafx.util.Pair;

/**
 * NOTE This class is meant to be implemented by an enum class.
 */
public interface FSMState {
    boolean isFinal();
    <S extends Enum<S> & FSMState> Pair<S, Character> process(char transitionLetter);
}
