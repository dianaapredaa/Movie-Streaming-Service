package fileio;

public final class Actions {
    public Actions() {
    }
    private String type;
    private String page;
    private String feature;
    private String startsWith;     // Movies - Search - StartsWith
    private String movie;       // SeeDetails
    private int count;     // Upgrades - Buy Token - count
    private int rate;           // SeeDetails - Rate - rate
    private Filters filters;     // Movie - Filters - Sort - rating/duration
    private Credentials credentials;
    private String subscribedGenre;
    private Movies addedMovie;
    private String deletedMovie;

    public String getDeletedMovie() {
        return deletedMovie;
    }

    public void setDeletedMovie(final String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }

    public Movies getAddedMovie() {
        return addedMovie;
    }

    public void setAddedMovie(final Movies addedMovie) {
        this.addedMovie = addedMovie;
    }

    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    public void setSubscribedGenre(final String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }

    public Actions(final Actions actions) {
        this.filters = new Filters(actions.getFilters());
    }
    public Actions(final Filters filters) {
        this.filters = new Filters(filters);
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(final String movie) {
        this.movie = movie;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String futures) {
        this.feature = futures;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(final Filters filters) {
        this.filters = filters;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(final int rate) {
        this.rate = rate;
    }

}

