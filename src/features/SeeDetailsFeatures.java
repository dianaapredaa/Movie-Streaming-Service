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

public class SeeDetailsFeatures {
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

    public void purchase(Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output) {
        // only on SeeDetails page
        if (!currentAuth.getCurrentPage().getPageType().equals("see details")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error9");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        String objectType = command.getObjectType();
        String movie = command.getMovie();

        for (int i = 0; i < currentAuth.getCurrentMoviesList().size(); i++) {
            if (currentAuth.getCurrentMoviesList().get(i).getName().equals(movie)) {
                currentAuth.getPurchasedMovies().add(currentAuth.getCurrentMoviesList().get(i));

                if (currentAuth.getCurrentUser().getCredentials().getAccountType().equals("premium")) {
                    currentAuth.getCurrentUser().setNumFreePremiumMovies(currentAuth.getCurrentUser().getNumFreePremiumMovies() - 1);
                } else {
                    currentAuth.getCurrentUser().setTokensCount(currentAuth.getCurrentUser().getTokensCount() - 2);
                }

                // output message
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", "null");

                ArrayList<Movies> currentMoviesList = new ArrayList<>();
                for (int j = 0; j < currentAuth.getCurrentMoviesList().size(); j++) {
                    Movies newMovie = new Movies(currentAuth.getCurrentMoviesList().get(j));
                    currentMoviesList.add(newMovie);
                }
                objectNode.putPOJO("currentMoviesList", currentMoviesList);
                objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
                output.addPOJO(objectNode);
                return;
            }
        }
        // output message for Error
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", "Error10");
        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
        objectNode.putPOJO("currentUser", null);
        output.addPOJO(objectNode);
    }

    public void watch(Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output) {
        // only on SeeDetails page
        if (!currentAuth.getCurrentPage().getPageType().equals("see details")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error1");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        // check if you purchased the movie
        for (int i = 0; i < currentAuth.getPurchasedMovies().size(); i++) {
            if (currentAuth.getPurchasedMovies().get(i).getName().equals(command.getMovie())) {
                currentAuth.getCurrentUser().getWatchedMovies().add(currentAuth.getPurchasedMovies().get(i));

                // output message
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);
                objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
                output.addPOJO(objectNode);
                return;
            }
        }
            // output message for Error
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
    }

    public void rate(Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output){
        // only on SeeDetails page
        if (!currentAuth.getCurrentPage().getPageType().equals("see details")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }
        // check if you watched the movie
        ArrayList<Movies> watchedMovies = currentAuth.getCurrentUser().getWatchedMovies();

        for (int i = 0; i < watchedMovies.size(); i++) {
            if (watchedMovies.get(i).getName().equals(command.getMovie())) {
                currentAuth.getCurrentUser().getRatedMovies().add(currentAuth.getCurrentUser().getWatchedMovies().get(i));

                // output message
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);
                objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
                output.addPOJO(objectNode);
                return;
            }
        }

        // output message
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", "Error");
        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
        objectNode.putPOJO("currentUser", null);
        output.addPOJO(objectNode);
        return;

    }

    public void like(Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output) {
        // only on SeeDetails page
        if (!currentAuth.getCurrentPage().getPageType().equals("see details")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }
        // check if you watched the movie
        ArrayList<Movies> watchedMovies = currentAuth.getCurrentUser().getWatchedMovies();

        for (int i = 0; i < watchedMovies.size(); i++) {
            if (watchedMovies.get(i).getName().equals(command.getMovie())) {
                currentAuth.getCurrentUser().getLikedMovies().add(currentAuth.getCurrentUser().getWatchedMovies().get(i));

                // output message
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);
                objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
                output.addPOJO(objectNode);
                return;
            }
        }

        // output message for Error
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", "Error6");
        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
        objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
        output.addPOJO(objectNode);

    }

}
