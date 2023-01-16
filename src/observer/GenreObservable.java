package observer;

import fileio.Notifications;
import fileio.Users;

import java.util.HashMap;
import java.util.LinkedList;

public final class GenreObservable implements Observable {
    private GenreObservable() {
        genres = new HashMap<>();
    }

    private static GenreObservable instance = new GenreObservable();
    public static GenreObservable getInstance() {
        return instance;
    }
    private HashMap<String, LinkedList<Users>> genres;

    @Override
    public void attach(final Observer o, final String genre) {
        // add genre if it does not exist
        if (!genres.containsKey(genre)) {
            genres.put(genre, new LinkedList<>());
        }

        genres.get(genre).add((Users) o);
    }

    @Override
    public void detach(final Observer o, final String property) {

    }

    @Override
    public void notifyObservers(final Notifications notifications) {
        LinkedList<Users> notifiedUsers = new LinkedList<>();

        for (String genre: notifications.getMovieGenre()) {
            if (genres.containsKey(genre)) {
                for (Users user : genres.get(genre)) {
                    if (!notifiedUsers.contains(user)) {
                        user.update(notifications);
                        notifiedUsers.add(user);
                    }
                }
            }
        }
    }

    public HashMap<String, LinkedList<Users>> getGenres() {
        return genres;
    }
    public void setGenres(final HashMap<String, LinkedList<Users>> genres) {
        this.genres = genres;
    }
}
