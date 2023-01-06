package fileio;

public final class Notifications {
    public Notifications() {

    }

    private String movieName;
    private String message;

    public Notifications(final Notifications notifications) {
        this.message = notifications.getMessage();
        this.movieName = notifications.getMovieName();
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(final String message) {
        this.message = message;
    }
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }
}
