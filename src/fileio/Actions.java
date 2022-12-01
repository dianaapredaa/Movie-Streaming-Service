package fileio;

import java.util.ArrayList;

public class Actions {
    public Actions() {
    }
    private String type;
    private String page;
    private String futures;

    // Movie - Filters - Contains - actors/genre
    private Filters filters;

    // Upgrades - Buy Token - count
    private int count;

    // SeeDetails - Rate - rate
    private int rate;

    // Login - Login - Credentials - name/password
    // Register - Register - Credentials - name/password/accountType/country/balance
    private Credentials credentials;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFutures() {
        return futures;
    }

    public void setFutures(String futures) {
        this.futures = futures;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

}

class Filters {

    private Contains contains;

    public Contains getContains() {
        return contains;
    }

    public void setContains(Contains contains) {
        this.contains = contains;
    }

    public Filters(Contains contains) {
        this.contains = contains;
    }
}

class Contains {
    private ArrayList<String> actors;
    private ArrayList<String> genre;

    public Contains(ArrayList<String> actors, ArrayList<String> genre) {
        this.actors = actors;
        this.genre = genre;
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

class Credentials {
    private String name;
    private String password;

    private String accountType;

    private String country;

    private int balance;

    public Credentials(String name, String password, String accountType, String country, int balance) {
        this.name = name;
        this.password = password;
        this.accountType = accountType;
        this.country = country;
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

