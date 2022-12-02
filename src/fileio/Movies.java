package fileio;

import java.util.ArrayList;

public class Movies {
    public Movies() {
    }
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> countriesBanned;
    private ArrayList<String> actors;
    private ArrayList<String> genres;

    public Movies(String name, int year, int duration, ArrayList<String> countriesBanned,
                 ArrayList<String> actors, ArrayList<String> genres) {
        this.name = name;
        this.year = year;
        this.duration = duration;
        this.countriesBanned = countriesBanned;
        this.actors = actors;
        this.genres = genres;
    }

    public Movies(Movies movie) {
        this.name = movie.getName();
        this.year = movie.getYear();
        this.duration = movie.getDuration();
        this.countriesBanned = movie.getCountriesBanned();
        this.actors = movie.getActors();
        this.genres = movie.getGenres();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Movies{"
                + "name='" + name + '\''
                + ", year=" + year
                + ", duration=" + duration
                + ", countriesBanned=" + countriesBanned
                + ", actors=" + actors
                + ", genres=" + genres
                + '}';
    }
}
