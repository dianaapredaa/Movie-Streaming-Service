package features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Actions;
import fileio.Movies;
import fileio.Notifications;
import fileio.Users;
import observer.GenreObservable;

import java.util.ArrayList;
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
    public void add(final Actions command, final LinkedList<Movies> movies,
                    final ArrayNode output) {
        Movies addedMovie = command.getAddedMovie();

        // check if movie already exists
        if (!movies.contains(addedMovie)) {
            // add movie to movie's database
            Movies movie = new Movies(addedMovie);
            movies.addLast(movie);

            // notify subscribers
            Notifications notification = new Notifications(addedMovie.getName(),
                    Notifications.Message.ADD, addedMovie.getGenres());
            GenreObservable.getInstance().notifyObservers(notification);
        } else {
            // output message for already existent movie
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }
    }

    /**
     * Delete movies
     *
     * In order to have our database up to customers standards, admins can also
     * delete movies
     * @param command
     * @param movies
     */
    public void delete(final Actions command, final LinkedList<Users> users,
                       final LinkedList<Movies> movies, final ArrayNode output) {
        String deletedMovie = command.getDeletedMovie();

        // search for indicated movie
        for (Movies movie : movies) {
            if (movie.getName().equals(deletedMovie)) {
                // if found, notify subscribers
                Notifications notification = new Notifications(deletedMovie,
                        Notifications.Message.DELETE, movie.getGenres());
                GenreObservable.getInstance().notifyObservers(notification);

                // delete movie from subscriber's lists
                for (Users user : users) {
                    if (user.getPurchasedMovies().contains(movie)) {
                        user.getPurchasedMovies().remove(movie);
                        user.getWatchedMovies().remove(movie);
                        user.getRatedMovies().remove(movie);
                        user.getLikedMovies().remove(movie);

                        // refund purchase costs
                        if (user.getCredentials().getAccountType().equals("premium")) {
                            user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() + 1);
                        } else {
                            user.setTokensCount(user.getTokensCount() + 2);
                        }
                    }
                }
                return;
            }
        }

        // output message if movie doesn't exists
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", "Error");
        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
        objectNode.putPOJO("currentUser", null);
        output.addPOJO(objectNode);
        return;
    }

}
