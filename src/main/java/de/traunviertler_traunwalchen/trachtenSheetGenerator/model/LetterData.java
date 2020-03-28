package de.traunviertler_traunwalchen.trachtenSheetGenerator.model;

import org.jetbrains.annotations.NotNull;

public final class LetterData {
    private final String subject;
    private final String place;
    private final String opening;
    private final String content;
    private final String closing;

    public LetterData(@NotNull String subject, @NotNull String place, @NotNull String opening, @NotNull String content,
                      @NotNull String closing) {
        this.subject = subject;
        this.place = place;
        this.opening = opening;
        this.content = content;
        this.closing = closing;
    }

    @NotNull
    public String getSubject() {
        return subject;
    }

    @NotNull
    public String getPlace() {
        return place;
    }

    @NotNull
    public String getOpening() {
        return opening;
    }

    @NotNull
    public String getContent() {
        return content;
    }

    @NotNull
    public String getClosing() {
        return closing;
    }
}
