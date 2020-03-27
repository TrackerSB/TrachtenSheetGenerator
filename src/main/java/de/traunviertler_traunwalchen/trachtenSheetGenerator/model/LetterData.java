package de.traunviertler_traunwalchen.trachtenSheetGenerator.model;

public final class LetterData {
    private String subject;
    private String place;
    private String opening;
    private String content;
    private String closing;

    public LetterData(String subject, String place, String opening, String content, String closing) {
        this.subject = subject;
        this.place = place;
        this.opening = opening;
        this.content = content;
        this.closing = closing;
    }

    public String getSubject() {
        return subject;
    }

    public String getPlace() {
        return place;
    }

    public String getOpening() {
        return opening;
    }

    public String getContent() {
        return content;
    }

    public String getClosing() {
        return closing;
    }
}
