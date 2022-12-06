package fileio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Users {
    public Users() {

    }
    private Credentials credentials;
    private int tokensCount = 0;
    private int numFreePremiumMovies = 15;
    private ArrayList<Movies> purchasedMovies = new ArrayList<>();
    private ArrayList<Movies> watchedMovies = new ArrayList<>();
    private ArrayList<Movies> likedMovies = new ArrayList<>();
    private ArrayList<Movies> ratedMovies = new ArrayList<>();

    @JsonIgnore
    private int premiumAccount = 15;

    public ArrayList<Movies> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(ArrayList<Movies> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movies> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(ArrayList<Movies> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movies> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<Movies> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movies> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(ArrayList<Movies> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }


    public int getPremiumAccount() {
        return premiumAccount;
    }

    public void setPremiumAccount(int premiumAccount) {
        this.premiumAccount = premiumAccount;
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
}
