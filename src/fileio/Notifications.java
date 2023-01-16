package fileio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public final class Notifications {
    public Notifications() {

    }

    // builder pattern implementation
    public static final class NotificationBuilder {
        private String movieName;
        private Notifications.Message message;
        @JsonIgnore
        private ArrayList<String> movieGenre;

        /**
         * Message Type
         *
         * Builder method that sets message type
         * @param message
         * @return
         */
        public NotificationBuilder messageType(final Message message) {
            this.message = message;
            return this;
        }

        /**
         * Movie Name
         *
         * Builder method that sets movie name
         * @param movieName
         * @return
         */
        public NotificationBuilder movieName(final String movieName) {
            this.movieName = movieName;
            return this;
        }

        /**
         * Movie Genre
         *
         * Builder method that sets movie genres
         * @param movieGenre
         * @return
         */
        public NotificationBuilder movieGenre(final ArrayList<String> movieGenre) {
            this.movieGenre = movieGenre;
            return this;
        }

        /**
         * Build
         *
         * Assemble notifications
         * @return
         */
        public Notifications build() {
            Notifications notifications = new Notifications(this);
            return notifications;
        }
    }

    public Notifications(final NotificationBuilder notificationBuilder) {
        this.movieName = notificationBuilder.movieName;
        this.message = notificationBuilder.message;
        this.movieGenre = notificationBuilder.movieGenre;
    }

    private String movieName;
    public enum Message {
        ADD,
        DELETE,
        Recommendation;
    }
    private Message message;
    @JsonIgnore
    private ArrayList<String> movieGenre;

    public Notifications(final String movieName, final Message message,
                         final ArrayList<String> movieGenre) {
        this.movieName = movieName;
        this.message = message;
        this.movieGenre = movieGenre;
    }

    public Notifications(final String movieName, final Message message) {
        this.movieName = movieName;
        this.message = message;
    }

    public Notifications(final Notifications notifications) {
        this.message = notifications.getMessage();
        this.movieName = notifications.getMovieName();
        this.movieGenre = notifications.getMovieGenre();
    }

    public Notifications.Message getMessage() {
        return message;
    }
    public void setMessage(final Notifications.Message message) {
        this.message = message;
    }
    public ArrayList<String> getMovieGenre() {
        return movieGenre;
    }
    public void setMovieGenre(final ArrayList<String> movieGenre) {
        this.movieGenre = movieGenre;
    }
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }
}
