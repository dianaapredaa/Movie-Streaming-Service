package features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Actions;
import fileio.Movies;
import fileio.Users;
import main.CurrentAuthentication;
import main.PageType;

import java.util.ArrayList;
import java.util.LinkedList;

public class MoviesFeatures {
    private CurrentAuthentication currentAuth;
    public CurrentAuthentication getCurrent() {
        return currentAuth;
    }
    public void setCurrent(CurrentAuthentication currentAuth) {
        this.currentAuth = currentAuth;
    }
    private ObjectMapper objectMapper = new ObjectMapper();

    // create a PageType object to get different types of pages
    private static PageType page = new PageType();

    public void search (Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output) {
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
            if (currentAuth.getCurrentMoviesList().get(i).getName().contains(startsWith)) {
                currentMoviesList1.add(new Movies(currentAuth.getCurrentMoviesList().get(i)));
            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", null);
        objectNode.putPOJO("currentMoviesList", currentMoviesList1);
        objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
        output.addPOJO(objectNode);

    }

    public void filters (Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output) {
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
        ArrayList<String> genre = new ArrayList<>();
        ArrayList<String> actors = new ArrayList<>();

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
            for (int j = 0; j < actors.size(); j ++) {
                if (currentAuth.getCurrentMoviesList().get(i).getActors().contains(actors.get(j))) {
                    currentMoviesList.add(currentAuth.getCurrentMoviesList().get(i));
                }
            }
        }

        for (int i = 0; i < currentMoviesList.size() - 1; i++) {
            for (int j = 0; j < currentMoviesList.size() - i - 1; j ++) {
                if (rating.equals("increasing")) {
                    if (currentMoviesList.get(j).getRating() > currentMoviesList.get(j + 1).getRating()) {
                        Movies auxMovie = currentMoviesList.get(j);
                        currentMoviesList.remove(j);
                        currentMoviesList.add(j + 1, auxMovie);
                    }
                }

                if (rating.equals("decreasing")) {
                    if (currentMoviesList.get(j).getRating() < currentMoviesList.get(j + 1).getRating()) {
                        Movies auxMovie = currentMoviesList.get(j);
                        currentMoviesList.remove(j);
                        currentMoviesList.add(j + 1, auxMovie);
                    }
                }
                if (currentMoviesList.get(j).getRating() == currentMoviesList.get(j + 1).getRating()) {
                    if (duration.equals("increasing")) {
                        if (currentMoviesList.get(j).getDuration() > currentMoviesList.get(j + 1).getDuration()) {
                            Movies auxMovie = currentMoviesList.get(j);
                            currentMoviesList.remove(j);
                            currentMoviesList.add(j + 1, auxMovie);
                        }
                    }

                    if (duration.equals("decreasing")) {
                        if (currentMoviesList.get(j).getDuration() < currentMoviesList.get(j + 1).getDuration()) {
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
        output.addPOJO(objectNode);    }
}
