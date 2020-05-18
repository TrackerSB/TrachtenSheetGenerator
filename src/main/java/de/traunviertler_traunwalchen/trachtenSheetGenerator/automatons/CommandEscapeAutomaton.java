package de.traunviertler_traunwalchen.trachtenSheetGenerator.automatons;

import javafx.util.Pair;

/**
 * This automaton is able to:
 * <ul>
 *     <li>Escape arguments in double quotes upt to two levels</li>
 *     <li>Convert paths with backslashes to paths with slashes</li>
 * </ul>
 * FIXME Verify the promise
 * TODO Think about: This automaton is more like a Meale automaton
 */
public enum CommandEscapeAutomaton implements FSMState<CommandEscapeAutomaton> {
    OUTSIDE_ARGUMENT(true) {
        @Override
        public Pair<CommandEscapeAutomaton, String> process(char transitionLetter) {
            switch (transitionLetter) {
            case '"':
                return new Pair<>(INSIDE_ARGUMENT, "");
            default:
                return new Pair<>(OUTSIDE_ARGUMENT, String.valueOf(transitionLetter));
            }
        }
    },
    INSIDE_ARGUMENT(false) {
        @Override
        public Pair<CommandEscapeAutomaton, String> process(char transitionLetter) {
            switch (transitionLetter) {
            case '"':
                return new Pair<>(OUTSIDE_ARGUMENT, String.valueOf(transitionLetter));
            case '\\':
                return new Pair<>(ESCAPE_START, "");
            default:
                return new Pair<>(INSIDE_ARGUMENT, String.valueOf(transitionLetter));
            }
        }
    },
    ESCAPE_START(false) {
        @Override
        public Pair<CommandEscapeAutomaton, String> process(char transitionLetter) {
            switch (transitionLetter){
            case '\\':
                return new Pair<>(INSIDE_ARGUMENT, "\\\\\\\\");
            case '\"':
                return new Pair<>(INSIDE_ARGUMENT, "\\\\\\\"");
            default:
                return new Pair<>(INSIDE_ARGUMENT, "/" + transitionLetter);
            }
        }
    };
    // Nodes with only one epsilon branch are not modelled explicitly
    // ESCAPE_BACKSLASH(false),
    // ESCAPE_QUOTES(false),
    // ESCAPE_PATH(false);

    private final boolean isFinal;

    CommandEscapeAutomaton(boolean isFinal) {
        this.isFinal = isFinal;
    }

    @Override
    public boolean isFinal() {
        return isFinal;
    }
}
