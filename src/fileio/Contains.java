package fileio;

import java.util.ArrayList;

public final class Contains {
    public Contains() {

    }
    private ArrayList<String> actors;
    private ArrayList<String> genre;
    public Contains(final Contains contains) {
        this.actors = contains.getActors();
        this.genre = contains.getGenre();
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(final ArrayList<String> genre) {
        this.genre = genre;
    }
}
