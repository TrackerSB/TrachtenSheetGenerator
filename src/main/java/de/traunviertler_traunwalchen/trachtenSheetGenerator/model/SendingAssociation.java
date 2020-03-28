package de.traunviertler_traunwalchen.trachtenSheetGenerator.model;

public class SendingAssociation extends Association {
    public static final SendingAssociation TRAUNVIERTLER
            = new SendingAssociation("GTEV \"D'Traunviertler\" Traunwalchen", "Pfarrhofstraße 2", "83374 Traunwalchen",
            "gtev@traunviertler-traunwalchen.de", "+49 8669 7894915");
    private final String email;
    private final String phone;

    public SendingAssociation(String name, String addressStreet, String addressPlace, String email, String phone) {
        super(name, addressStreet, addressPlace);
        this.email = email;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
