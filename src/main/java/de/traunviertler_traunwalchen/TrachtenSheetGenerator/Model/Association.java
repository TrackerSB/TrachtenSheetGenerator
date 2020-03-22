package de.traunviertler_traunwalchen.TrachtenSheetGenerator.Model;

import java.util.Collections;
import java.util.Set;

public final class Association implements Comparable<Association> {
    public static final Set<Association> ASSOCIATIONS = Collections.unmodifiableSet(Set.of(
            new Association("GTEV Nußdorf"),
            new Association("GTEV Rettenbach")
    ));

    private String name;

    private Association(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Association other) {
        return getName().compareTo(other.getName());
    }

    @Override
    public String toString() {
        return getName();
    }
}
