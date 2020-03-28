package de.traunviertler_traunwalchen.trachtenSheetGenerator.model;

public class Association {

    private final String name;
    private final String addressStreet;
    private final String addressPlace;

    public Association(String name, String addressStreet, String addressPlace) {
        this.name = name;
        this.addressStreet = addressStreet;
        this.addressPlace = addressPlace;
    }

    public String getName() {
        return name;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public String getAddressPlace() {
        return addressPlace;
    }

    @Override
    public String toString() {
        return getName();
    }
}
