package it.mollik.amuse.amusers.model;

public enum EMusicGenre {
    ROCK("Rock"),
    POP("Pop"),
    JAZZ("Jazz"),
    BLUES("Blues"),
    WORLD("World");
    
    public final String genre;

    private EMusicGenre(String genre) {
        this.genre = genre;
    }

    @Override 
    public String toString() { 
        return this.genre; 
    }
}
