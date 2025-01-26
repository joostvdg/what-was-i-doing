package net.joostvdg.wwid.main;

import java.time.Instant;

public class Note {
    private String name;
    private Instant created;
    private Instant lastEdited;
    private String filename;
    private String text;

    public Note(String name, Instant created, Instant lastEdited, String filename, String text) {
        this.name = name;
        this.created = created;
        this.lastEdited = lastEdited;
        this.filename = filename;
        this.text = text;
    }

    // Getters and setters
}