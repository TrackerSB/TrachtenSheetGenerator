package de.traunviertler_traunwalchen.trachtenSheetGenerator.model;

import org.jetbrains.annotations.NotNull;

public class Association {

    private final String name;
    private final String addressStreet;
    private final String addressPlace;

    public Association(@NotNull String name, @NotNull String addressStreet, @NotNull String addressPlace) {
        this.name = name;
        this.addressStreet = addressStreet;
        this.addressPlace = addressPlace;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getAddressStreet() {
        return addressStreet;
    }

    @NotNull
    public String getAddressPlace() {
        return addressPlace;
    }

    @Override
    @NotNull
    public String toString() {
        return getName();
    }
}
