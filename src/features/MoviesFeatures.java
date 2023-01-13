package features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Actions;
import fileio.Movies;
import fileio.Users;
import commands.CurrentAuthentication;

import java.util.ArrayList;
import java.util.Collections;
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
     * Movies - Search
     *
     * When changing to movie page, there are different possible actions
     * When on search feature users can search for a specific movie starting with given syntax
     * @param command
     * @param output
     */
    public void search(final Actions command, final ArrayNode output) {
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
        LinkedList<Movies> currentMoviesList = new LinkedList<>();
        for (int i = 0; i < currentAuth.getCurrentMoviesList().size(); i++) {
            if (currentAuth.getCurrentMoviesList().get(i).getName().startsWith(startsWith)) {
                currentMoviesList.add(currentAuth.getCurrentMoviesList().get(i));
            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", null);

        ArrayList<Movies> currentMoviesListToPrint = new ArrayList<>();
        for (Movies value : currentMoviesList) {
            Movies newMovie = new Movies(value);
            currentMoviesListToPrint.add(newMovie);
        }

        objectNode.putPOJO("currentMoviesList", currentMoviesListToPrint);
        objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
        output.addPOJO(objectNode);

    }

    /**
     * Movies - Filter
     *
     * When changing to movie page, there are different possible actions
     * When on filter feature users can search after specified parameters and display results
     * depending on duration and rating
     * @param command
     * @param output
     */
    public void filters(final Actions command, final ArrayNode output) {
        // only on Movies Page
        if (!currentAuth.getCurrentPage().getPageType().equals("movies")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        String rating = null;
        String duration = null;
        ArrayList<String> genre = null;
        ArrayList<String> actors = null;
        LinkedList<Movies> currentMoviesList = new LinkedList<>();

        if (command.getFilters().getSort() != null) {
            if (command.getFilters().getSort().getRating() != null) {
                rating = command.getFilters().getSort().getRating();
            }
            if (command.getFilters().getSort().getDuration() != null) {
                duration = command.getFilters().getSort().getDuration();
            }
        }
        if (command.getFilters().getContains() != null) {
            if (command.getFilters().getContains().getGenre() != null) {
                genre = command.getFilters().getContains().getGenre();
            }
            if (command.getFilters().getContains().getActors() != null) {
                actors = command.getFilters().getContains().getActors();
            }
        }

        // filter only by actors
        if (actors != null && genre == null) {
            for (int i = 0; i < currentAuth.getCurrentMoviesList().size(); i++) {
                if (currentAuth.getCurrentMoviesList().get(i).getActors().containsAll(actors)) {
                    currentMoviesList.add(currentAuth.getCurrentMoviesList().get(i));
                }
            }
        } else if (actors == null && genre != null) {
            // filer only by genre
            for (int i = 0; i < currentAuth.getCurrentMoviesList().size(); i++) {
                if (currentAuth.getCurrentMoviesList().get(i).getGenres().containsAll(genre)) {
                    currentMoviesList.add(currentAuth.getCurrentMoviesList().get(i));
                }
            }
        } else if (actors == null && genre == null) {
            // filter by actors and genre
            currentMoviesList.addAll(currentAuth.getCurrentMoviesList());
        } else if (actors != null && genre != null) {
            // ignore actors and genre
            for (int i = 0; i < currentAuth.getCurrentMoviesList().size(); i++) {
                Movies movie = currentAuth.getCurrentMoviesList().get(i);
                if (movie.getActors().containsAll(actors)
                        && movie.getGenres().containsAll(genre)) {
                    currentMoviesList.add(currentAuth.getCurrentMoviesList().get(i));
                }
            }
        }

        for (int i = 0; i < currentMoviesList.size() - 1; i++) {
            for (int j = 0; j < currentMoviesList.size() - i - 1; j++) {
                boolean isEqual = false;
                if (duration != null) {
                    if (duration.equals("increasing")) {
                        if (currentMoviesList.get(j).getDuration()
                                > currentMoviesList.get(j + 1).getDuration()) {
                            Collections.swap(currentMoviesList, j, j + 1);
                        } else if (currentMoviesList.get(j).getDuration()
                                == currentMoviesList.get(j + 1).getDuration()) {
                            isEqual = true;
                        }
                    }
                    if (duration.equals("decreasing")) {
                        if (currentMoviesList.get(j).getDuration()
                                < currentMoviesList.get(j + 1).getDuration()) {
                            Collections.swap(currentMoviesList, j, j + 1);
                        } else if (currentMoviesList.get(j).getDuration()
                                == currentMoviesList.get(j + 1).getDuration()) {
                            isEqual = true;
                        }
                    }
                }

                if ((rating != null && isEqual) || (rating != null && duration == null)) {
                    if (rating.equals("increasing")) {
                        if (currentMoviesList.get(j).getRating()
                                > currentMoviesList.get(j + 1).getRating()) {
                            Collections.swap(currentMoviesList, j, j + 1);
                        }
                    }
                    if (rating.equals("decreasing")) {
                        if (currentMoviesList.get(j).getRating()
                                < currentMoviesList.get(j + 1).getRating()) {
                            Collections.swap(currentMoviesList, j, j + 1);
                        }
                    }
                }
            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", null);

        LinkedList<Movies> listToPrint = new LinkedList<>();
        for (Movies movie : currentMoviesList) {
            listToPrint.add(new Movies(movie));
        }

        objectNode.putPOJO("currentMoviesList", listToPrint);
        objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
        output.addPOJO(objectNode);

        currentAuth.setCurrentMoviesList(currentMoviesList);
    }

    /**
     * Movies
     *
     * When changing to movie page, there are different possible actions
     * First of all, displaying current user's movie list
     * @param movies
     * @param output
     */
    public void onMoviesPage(final LinkedList<Movies> movies, final ArrayNode output) {
        currentAuth.setCurrentMoviesList(new LinkedList<>());

        for (Movies movie : movies) {
            // current User's Country
            String userCountry = currentAuth.getCurrentUser().getCredentials().getCountry();

            // populate current User's MovieList with non-banned movies
            if (!movie.getCountriesBanned().contains(userCountry)) {
                currentAuth.getCurrentMoviesList().add(movie);
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
