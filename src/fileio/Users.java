package fileio;

import java.util.ArrayList;

public class Users {
    public Users() {

    }
    public Users(Users users) {
        this.credentials = new Credentials(users.getCredentials());
    }

    public Users(Credentials credentials) {
        this.credentials = new Credentials(credentials);
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    private Credentials credentials;

    private int tokensCount = 0;
    private int numFreePremiumMovies = 15;
    private ArrayList<String> purchasedMovies = new ArrayList<>();
    private ArrayList<String> watchedMovies = new ArrayList<>();
    private ArrayList<String> likedMovies = new ArrayList<>();

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<String> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(ArrayList<String> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<String> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(ArrayList<String> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<String> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<String> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<String> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(ArrayList<String> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    private ArrayList<String> ratedMovies = new ArrayList<>();
}
