package features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Actions;
import fileio.Movies;
import fileio.Users;
import main.CurrentAuthentication;

import java.util.ArrayList;
import java.util.LinkedList;

public final class MoviesFeatures {
    private CurrentAuthentication currentAuth;
    public CurrentAuthentication getCurrent() {
        return currentAuth;
    }
    public void setCurrent(final CurrentAuthentication currentAuthentication) {
        this.currentAuth = currentAuthentication;
    }
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @param command
     * @param users
     * @param movies
     * @param output
     */
    public void search(final Actions command, final LinkedList<Users> users,
                       final LinkedList<Movies> movies, final ArrayNode output) {
        // only on Movies Page
        if (!currentAuth.getCurrentPage().getPageType().equals("movies")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        // startsWith
        String startsWith = command.getStartsWith();

        // find startsWith movies
        ArrayList<Movies> currentMoviesList1 = new ArrayList<>();
        for (int i = 0; i < currentAuth.getCurrentMoviesList().size(); i++) {
            if (currentAuth.getCurrentMoviesList().get(i).getName().startsWith(startsWith)) {
                currentMoviesList1.add(new Movies(currentAuth.getCurrentMoviesList().get(i)));
            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", null);
        objectNode.putPOJO("currentMoviesList", currentMoviesList1);
        objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
        output.addPOJO(objectNode);

    }

    /**
     *
     * @param command
     * @param users
     * @param movies
     * @param output
     */
    public void filters(final Actions command, final LinkedList<Users> users,
                        final LinkedList<Movies> movies, final ArrayNode output) {
        // only on Movies Page
        if (!currentAuth.getCurrentPage().getPageType().equals("movies")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        String rating = "increasing";
        String duration = "increasing";
        ArrayList<String> genre = null;
        ArrayList<String> actors = null;

        if (command.getFilters().getSort() != null) {
            rating = command.getFilters().getSort().getRating();
            duration = command.getFilters().getSort().getDuration();
        }
        if (command.getFilters().getContains() != null) {
            genre = command.getFilters().getContains().getGenre();
            actors = command.getFilters().getContains().getActors();
        }

        ArrayList<Movies> currentMoviesList = new ArrayList<>();

        for (int i = 0; i < currentAuth.getCurrentMoviesList().size() && actors != null; i++) {
            for (int j = 0; j < actors.size(); j++) {
                if (currentAuth.getCurrentMoviesList().get(i).getActors().contains(actors.get(j))) {
                    currentMoviesList.add(currentAuth.getCurrentMoviesList().get(i));
                }
            }
        }

        for (int j = 0; j < currentAuth.getCurrentMoviesList().size() && actors == null; j++) {
            Movies newMovie = new Movies(currentAuth.getCurrentMoviesList().get(j));
            currentMoviesList.add(newMovie);
        }

        for (int i = 0; i < currentMoviesList.size() - 1; i++) {
            for (int j = 0; j < currentMoviesList.size() - i - 1; j++) {
                if (rating.equals("increasing")) {
                    if (currentMoviesList.get(j).getRating()
                            > currentMoviesList.get(j + 1).getRating()) {
                        Movies auxMovie = currentMoviesList.get(j);
                        currentMoviesList.remove(j);
                        currentMoviesList.add(j + 1, auxMovie);
                    }
                }

                if (rating.equals("decreasing")) {
                    if (currentMoviesList.get(j).getRating()
                            < currentMoviesList.get(j + 1).getRating()) {
                        Movies auxMovie = currentMoviesList.get(j);
                        currentMoviesList.remove(j);
                        currentMoviesList.add(j + 1, auxMovie);
                    }
                }
                if (currentMoviesList.get(j).getRating()
                        == currentMoviesList.get(j + 1).getRating()) {
                    if (duration.equals("increasing")) {
                        if (currentMoviesList.get(j).getDuration()
                                > currentMoviesList.get(j + 1).getDuration()) {
                            Movies auxMovie = currentMoviesList.get(j);
                            currentMoviesList.remove(j);
                            currentMoviesList.add(j + 1, auxMovie);
                        }
                    }

                    if (duration.equals("decreasing")) {
                        if (currentMoviesList.get(j).getDuration()
                                < currentMoviesList.get(j + 1).getDuration()) {
                            Movies auxMovie = currentMoviesList.get(j);
                            currentMoviesList.remove(j);
                            currentMoviesList.add(j + 1, auxMovie);
                        }
                    }
                }
            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", null);
        objectNode.putPOJO("currentMoviesList", currentMoviesList);
        objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
        output.addPOJO(objectNode);
    }

    /**
     *
     * @param movies
     * @param output
     */
    public void onMoviesPage(final LinkedList<Movies> movies, final ArrayNode output) {
        currentAuth.setCurrentMoviesList(new LinkedList<>());

        for (int j = 0; j < movies.size(); j++) {
            // current User's Country
            String userCountry = currentAuth.getCurrentUser().getCredentials().getCountry();

            // populate current User's MovieList with non-banned movies
            if (!movies.get(j).getCountriesBanned().contains(userCountry)) {
                currentAuth.getCurrentMoviesList().add(movies.get(j));
            }
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", null);

        ArrayList<Movies> currentMoviesList = new ArrayList<>();
        for (int j = 0; j < currentAuth.getCurrentMoviesList().size(); j++) {
            Movies newMovie = new Movies(currentAuth.getCurrentMoviesList().get(j));
            currentMoviesList.add(newMovie);
        }
        objectNode.putPOJO("currentMoviesList", currentMoviesList);
        objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
        output.addPOJO(objectNode);
    }
}
