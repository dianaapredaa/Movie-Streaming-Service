package features;

import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.Actions;
import fileio.Movies;
import fileio.Notifications;
import fileio.Users;
import observer.GenreObservable;

import java.util.LinkedList;

public class AdminFeatures {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Add movies
     *
     * In order to expend our movie's database, admins can add new movies
     * @param command
     * @param movies
     */
    public void add(final Actions command, final LinkedList<Movies> movies) {
        Movies addedMovie = command.getAddedMovie();

        Movies movie = new Movies(addedMovie);
        movies.addLast(movie);

        Notifications notification = new Notifications(addedMovie.getName(),
                Notifications.Message.ADD, addedMovie.getGenres());
        GenreObservable.getInstance().notifyObservers(notification);
    }

    /**
     * Delete movies
     *
     * In order to have our database up to customers standards, admins can also
     * delete movies
     * @param command
     * @param movies
     */
    public void delete(final Actions command, final LinkedList<Movies> movies,
                       final LinkedList<Users> users) {
        String deletedMovie = command.getDeletedMovie();

        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getName().equals(deletedMovie)) {
                Notifications notification = new Notifications(deletedMovie,
                        Notifications.Message.DELETE, movies.get(i).getGenres());
                GenreObservable.getInstance().notifyObservers(notification);

                for (Users user : users) {
                    if (user.getPurchasedMovies().contains(movies.get(i))) {
                        user.getPurchasedMovies().remove(movies.get(i));
                        user.getWatchedMovies().remove(movies.get(i));
                        user.getRatedMovies().remove(movies.get(i));
                        user.getLikedMovies().remove(movies.get(i));

                        if (user.getCredentials().getAccountType().equals("premium")) {
                            user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() + 1);
                        } else {
                            user.setTokensCount(user.getTokensCount() + 2);
                        }
                    }
                }

                movies.remove(i);
                i--;
            }
        }
    }

}
