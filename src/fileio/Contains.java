package fileio;

import java.util.ArrayList;

public class Contains {
    public Contains() {

    }

    private ArrayList<String> actors;
    private ArrayList<String> genre;

    public Contains(ArrayList<String> actors, ArrayList<String> genre) {
        this.actors = actors;
        this.genre = genre;
    }

    public Contains(Contains contains) {
        this.actors = contains.getActors();
        this.genre = contains.getGenre();
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }
}
