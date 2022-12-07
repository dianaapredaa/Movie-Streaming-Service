package fileio;

public class Sort {

    public Sort() {

    }

    private String rating;
    private String duration;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Sort(String rating, String duration) {
        this.rating = rating;
        this.duration = duration;
    }

    public Sort(Sort sort) {
        this.duration = sort.getDuration();
        this.rating = sort.getRating();
    }
}
