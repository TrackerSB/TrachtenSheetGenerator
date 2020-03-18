package de.traunviertler_traunwalchen.TrachtenSheetGenerator.Model;

public enum Associations {
    NUSSDORF("GTEV Nußdorf"),
    RETTENBACH("GTEV Rettenbach");

    private String name;

    Associations(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
