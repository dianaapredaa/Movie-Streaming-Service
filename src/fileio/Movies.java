package fileio;

import java.util.ArrayList;

public final class Movies {
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
    private double rating = 0;

    public Movies(final String name, final int year, final int duration,
                  final ArrayList<String> countriesBanned, final ArrayList<String> actors,
                  final ArrayList<String> genres, final int numLikes, final int numRatings,
                  final int rating) {
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

    public Movies(final Movies movie) {
        this.name = movie.getName();
        this.year = movie.getYear();
        this.duration = movie.getDuration();
        this.countriesBanned = movie.getCountriesBanned();
        this.actors = new ArrayList<>(movie.getActors());
        this.genres = new ArrayList<>(movie.getGenres());
        this.numLikes = movie.getNumLikes();
        this.numRatings = movie.getNumRatings();
        this.rating = movie.getRating();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

}
