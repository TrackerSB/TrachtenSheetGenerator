package de.traunviertler_traunwalchen.trachtenSheetGenerator.model;

import org.jetbrains.annotations.NotNull;

public class SendingAssociation extends Association {
    public static final SendingAssociation TRAUNVIERTLER
            = new SendingAssociation("GTEV \"D'Traunviertler\" Traunwalchen", "Pfarrhofstraße 2", "83374 Traunwalchen",
            "gtev@traunviertler-traunwalchen.de", "+49 8669 7894915");
    private final String email;
    private final String phone;

    public SendingAssociation(@NotNull String name, @NotNull String addressStreet, @NotNull String addressPlace,
                              @NotNull String email, @NotNull String phone) {
        super(name, addressStreet, addressPlace);
        this.email = email;
        this.phone = phone;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    @NotNull
    public String getPhone() {
        return phone;
    }
}
