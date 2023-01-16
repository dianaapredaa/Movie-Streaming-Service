package fileio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public final class Users implements Observer {
    public Users() {

    }

    public static final int NUM_FREE_PREMIUM_MOVIES = 15;
    private Credentials credentials;
    private int tokensCount = 0;
    private int numFreePremiumMovies = NUM_FREE_PREMIUM_MOVIES;
    private ArrayList<Movies> purchasedMovies = new ArrayList<>();
    private ArrayList<Movies> watchedMovies = new ArrayList<>();
    private ArrayList<Movies> likedMovies = new ArrayList<>();
    private ArrayList<Movies> ratedMovies = new ArrayList<>();
    private ArrayList<Notifications> notifications = new ArrayList<>();
    @JsonIgnore
    private HashMap<String, Double> rating = new HashMap<>();

    public HashMap<String, Double> getRating() {
        return rating;
    }

    public void setRating(final HashMap<String, Double> rating) {
        this.rating = rating;
    }

    public Users(final Users users) {
        this.credentials = new Credentials(users.getCredentials());
        this.tokensCount = users.getTokensCount();
        this.watchedMovies = new ArrayList<>();
        this.likedMovies = new ArrayList<>();
        this.ratedMovies = new ArrayList<>();
        this.purchasedMovies = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.numFreePremiumMovies = users.getNumFreePremiumMovies();

        for (Notifications notification : users.getNotifications()) {
            this.notifications.add(new Notifications(notification));
        }
        for (Movies movie : users.getPurchasedMovies()) {
            this.purchasedMovies.add(new Movies(movie));
        }
        for (Movies movie : users.getWatchedMovies()) {
            this.watchedMovies.add(new Movies(movie));
        }
        for (Movies movie : users.getLikedMovies()) {
            this.likedMovies.add(new Movies(movie));
        }
        for (Movies movie : users.getRatedMovies()) {
            this.ratedMovies.add(new Movies(movie));
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Users users = (Users) o;
        return Objects.equals(credentials, users.credentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credentials);
    }

    public ArrayList<Notifications> getNotifications() {
        return notifications;
    }
    public void setNotifications(final ArrayList<Notifications> notifications) {
        this.notifications = notifications;
    }
    public ArrayList<Movies> getPurchasedMovies() {
        return purchasedMovies;
    }
    public void setPurchasedMovies(final ArrayList<Movies> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }
    public ArrayList<Movies> getWatchedMovies() {
        return watchedMovies;
    }
    public void setWatchedMovies(final ArrayList<Movies> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }
    public ArrayList<Movies> getLikedMovies() {
        return likedMovies;
    }
    public void setLikedMovies(final ArrayList<Movies> likedMovies) {
        this.likedMovies = likedMovies;
    }
    public ArrayList<Movies> getRatedMovies() {
        return ratedMovies;
    }
    public void setRatedMovies(final ArrayList<Movies> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }
    public Users(final Credentials credentials) {
        this.credentials = new Credentials(credentials);
    }
    public Credentials getCredentials() {
        return credentials;
    }
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }
    public int getTokensCount() {
        return tokensCount;
    }
    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }
    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }
    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    @Override
    public void update(final Notifications notification) {
        notifications.add(notification);
    }
}
