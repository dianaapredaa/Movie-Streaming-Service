package fileio;

import java.util.ArrayList;

public final class Users {
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

    public Users(final Users users) {
        this.credentials = new Credentials(users.getCredentials());
        this.tokensCount = users.getTokensCount();
        this.numFreePremiumMovies = users.getNumFreePremiumMovies();
//        this.purchasedMovies.addAll(users.getPurchasedMovies());
//        this.watchedMovies.addAll(users.getWatchedMovies());
//        this.likedMovies.addAll(users.getLikedMovies());
//        this.ratedMovies.addAll(users.getRatedMovies());
        this.purchasedMovies = new ArrayList<>(users.getPurchasedMovies());
        this.watchedMovies = new ArrayList<>(users.getWatchedMovies());
        this.likedMovies = new ArrayList<>(users.getLikedMovies());
        this.ratedMovies = new ArrayList<>(users.getRatedMovies());
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
}
