package de.traunviertler_traunwalchen.trachtenSheetGenerator.model;

import java.util.Collections;
import java.util.Set;

public final class Association implements Comparable<Association> {
    public static final Set<Association> ASSOCIATIONS = Collections.unmodifiableSet(Set.of(
            new Association("GTEV \"D'Traunviertler\" Traunwalchen", "Pfarrhofstraße 2", "83374 Traunwalchen",
                    "gtev@traunviertler-traunwalchen.de", "+49 8669 7894915"),
            new Association("GTEV Nußdorf e.V.", "Dorfplatz 14", "83365 Nußdorf"),
            new Association("GTEV \"Eschenwald\" Rettenbach e.V.", "Maieranger 11", "83278 Traunstein")
    ));

    private final String name;
    private final String addressStreet;
    private final String addressPlace;
    private final String email;
    private final String phone;

    private Association(String name, String addressStreet, String addressPlace){
        this(name, addressStreet, addressPlace, "<no email>", "<no phone>");
    }

    private Association(String name, String addressStreet, String addressPlace, String email, String phone) {
        this.name = name;
        this.addressStreet = addressStreet;
        this.addressPlace = addressPlace;
        this.email = email;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
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
