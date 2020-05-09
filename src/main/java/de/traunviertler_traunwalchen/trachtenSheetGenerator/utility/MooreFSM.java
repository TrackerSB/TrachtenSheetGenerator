package de.traunviertler_traunwalchen.trachtenSheetGenerator.utility;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Moore final state machine. It accepts a {@link String} that represents the movement through the FSM
 * and outputs the state in which the {@link String} ends as well as the generated output.
 */
public class MooreFSM<S extends Enum<S> & FSMState> {
    private final S startState;

    public MooreFSM(@NotNull S startState) {
        this.startState = startState;
    }

    @NotNull
    public Pair<S, String> process(@NotNull String input){
        S currentState = startState;
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()){
            Pair<S, Character> processResult = currentState.process(c);
            currentState = processResult.getKey();
            output.append(processResult.getValue());
        }
        return new Pair<>(currentState, output.toString());
    }
}
