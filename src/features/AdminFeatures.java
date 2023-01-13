package features;

import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.Actions;
import fileio.Movies;

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
    }

    /**
     * Delete movies
     *
     * In order to have our database up to customers standards, admins can also
     * delete movies
     * @param command
     * @param movies
     */
    public void delete(final Actions command, final LinkedList<Movies> movies) {
        String deletedMovie = command.getDeletedMovie();

        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getName().equals(deletedMovie)) {
                movies.remove(i);
                i--;
            }
        }

    }

}
