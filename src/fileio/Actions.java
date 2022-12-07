package fileio;

import java.util.ArrayList;

public class Actions {
    public Actions() {
    }
    private String type;
    private String page;
    private String feature;
    private String objectType;
    private String startsWith;     // Movies - Search - StartsWith
    private String movie;       // SeeDetails
    private int count;     // Upgrades - Buy Token - count
    private int rate;           // SeeDetails - Rate - rate
    private Filters filters;     // Movie - Filters - Sort - rating/duration
    private Credentials credentials;     // Login - Login - Credentials - name/password
    // Register - Register - Credentials - name/password/accountType/country/balance

    public Actions(Actions actions) {
        this.filters = new Filters(actions.getFilters());
    }
    public Actions(Filters filters) {
        this.filters = new Filters(filters);
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

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

    public String getFeature() {
        return feature;
    }

    public void setFeature(String futures) {
        this.feature = futures;
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

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
}

