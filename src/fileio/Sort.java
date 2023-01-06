package fileio;

public final class Sort {
    public Sort() {

    }

    private String rating;
    private String duration;

    public Sort(final Sort sort) {
        this.duration = sort.getDuration();
        this.rating = sort.getRating();
    }

    public String getRating() {
        return rating;
    }
    public void setRating(final String rating) {
        this.rating = rating;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(final String duration) {
        this.duration = duration;
    }
}
