package de.traunviertler_traunwalchen.trachtenSheetGenerator.utility;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

// FIXME Find a better name for a collection of functions which shorten
public final class Convenience {
    private Convenience() {
    }

    /**
     * Basically implements self-invoking lambdas.
     */
    @Nullable
    public static <T> T invoke(@NotNull Supplier<T> lambda) {
        return lambda.get();
    }
}
