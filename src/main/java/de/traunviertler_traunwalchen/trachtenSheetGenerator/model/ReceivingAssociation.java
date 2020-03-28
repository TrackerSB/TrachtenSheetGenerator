package de.traunviertler_traunwalchen.trachtenSheetGenerator.model;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

public class ReceivingAssociation extends Association implements Comparable<ReceivingAssociation> {
    public static final Set<ReceivingAssociation> RECEIVERS = Collections.unmodifiableSet(Set.of(
            new ReceivingAssociation("GTEV Nußdorf e.V.", "Dorfplatz 14", "83365 Nußdorf"),
            new ReceivingAssociation("GTEV \"Eschenwald\" Rettenbach e.V.", "Maieranger 11", "83278 Traunstein")
    ));

    public ReceivingAssociation(@NotNull String name, @NotNull String addressStreet, @NotNull String addressPlace) {
        super(name, addressStreet, addressPlace);
    }

    @Override
    @NotNull
    public int compareTo(ReceivingAssociation other) {
        return getName().compareTo(other.getName());
    }
}
