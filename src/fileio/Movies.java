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

    private int numLikes = 0;

    private int numRatings = 0;

    private int rating = 0;

    public Movies(String name, int year, int duration, ArrayList<String> countriesBanned,
                 ArrayList<String> actors, ArrayList<String> genres, int numLikes, int numRatings, int rating) {
        this.name = name;
        this.year = year;
        this.duration = duration;
        this.countriesBanned = countriesBanned;
        this.actors = actors;
        this.genres = genres;
        this.numLikes = numLikes;
        this.numRatings = numRatings;
        this.rating = rating;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Movies(Movies movie) {
        this.name = movie.getName();
        this.year = movie.getYear();
        this.duration = movie.getDuration();
        this.countriesBanned = movie.getCountriesBanned();
        this.actors = movie.getActors();
        this.genres = movie.getGenres();
        this.numLikes = movie.getNumLikes();
        this.numRatings = movie.getNumRatings();
        this.rating = movie.getRating();
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
