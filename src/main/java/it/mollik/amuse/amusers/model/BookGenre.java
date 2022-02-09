package it.mollik.amuse.amusers.model;

public enum BookGenre {
    
    THRILLER("Thriller"),
    NOIR("Noir"),
    COMEDY("Comedy"),
    TRAGEDY("Tragedy"),
    NOVEL("Novel"),
    ROMANCE("Romance"),
    COMICS("Comics"),
    ESSAY("Essay");

    public final String genre;

    private BookGenre(String genre) {
        this.genre = genre;
    }

    @Override 
    public String toString() { 
        return this.genre; 
    }
}
