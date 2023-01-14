package fileio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public final class Notifications {
    public Notifications() {

    }

    private String movieName;
    public enum Message {
        ADD,
        DELETE,
        Recommendation;
    }
    private Message message;
    @JsonIgnore
    private ArrayList<String> movieGenre;

    public Notifications(final String movieName, final Message message,
                         final ArrayList<String> movieGenre) {
        this.movieName = movieName;
        this.message = message;
        this.movieGenre = movieGenre;
    }

    public Notifications(final String movieName, final Message message) {
        this.movieName = movieName;
        this.message = message;
    }

    public Notifications(final Notifications notifications) {
        this.message = notifications.getMessage();
        this.movieName = notifications.getMovieName();
        this.movieGenre = notifications.getMovieGenre();
    }

    public Notifications.Message getMessage() {
        return message;
    }
    public void setMessage(final Notifications.Message message) {
        this.message = message;
    }
    public ArrayList<String> getMovieGenre() {
        return movieGenre;
    }
    public void setMovieGenre(final ArrayList<String> movieGenre) {
        this.movieGenre = movieGenre;
    }
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }
}
