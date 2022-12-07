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
    private final ObjectMapper objectMapper = new ObjectMapper();

    // create a PageType object to get different types of pages
    private static final PageType page = new PageType();

    public void purchase(Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output) {
        // only on SeeDetails page
        if (!currentAuth.getCurrentPage().getPageType().equals("see details")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        String objectType = command.getObjectType();
        String movie = command.getMovie();

        for (int i = 0; i < currentAuth.getCurrentMoviesList().size(); i++) {
            if (currentAuth.getCurrentMoviesList().get(i).getName().equals(movie)) {
                if (currentAuth.getCurrentUser().getCredentials().getAccountType().equals("premium")) {
                    currentAuth.getCurrentUser().setNumFreePremiumMovies(currentAuth.getCurrentUser().getNumFreePremiumMovies() - 1);
                } else {
                    currentAuth.getCurrentUser().setTokensCount(currentAuth.getCurrentUser().getTokensCount() - 2);
                }
                // update current user purchased list
                currentAuth.getCurrentUser().getPurchasedMovies().add(new Movies(currentAuth.getCurrentMoviesList().get(i)));

                // update current movie list
                currentAuth.setCurrentMoviesList(new LinkedList<>());
                currentAuth.getCurrentMoviesList().add(new Movies(currentAuth.getCurrentUser().getPurchasedMovies().get(0)));

                // output message
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

    public void watch(Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output) {
        // only on SeeDetails page
        if (!currentAuth.getCurrentPage().getPageType().equals("see details")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        // check if you purchased the movie
        for (int i = 0; i < currentAuth.getCurrentUser().getPurchasedMovies().size(); i++) {
            if (currentAuth.getCurrentUser().getPurchasedMovies().get(i).getName().equals(command.getMovie())) {

                // update current user watched list
                currentAuth.getCurrentUser().getWatchedMovies().add(new Movies(currentAuth.getCurrentUser().getPurchasedMovies().get(i)));

                // update current movie list
                currentAuth.setCurrentMoviesList(new LinkedList<>());
                currentAuth.getCurrentMoviesList().add(new Movies(currentAuth.getCurrentUser().getWatchedMovies().get(0)));

                // output message
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

        String movie = command.getMovie();
        int rating = command.getRate();
        // check if you watched the movie
        for (int i = 0; i < currentAuth.getCurrentUser().getWatchedMovies().size(); i++) {
            if (currentAuth.getCurrentUser().getWatchedMovies().get(i).getName().equals(movie)) {
                // increase number of likes
                int numRatings = currentAuth.getCurrentUser().getWatchedMovies().get(i).getNumRatings() + 1;
                currentAuth.getCurrentUser().getWatchedMovies().get(i).setNumRatings(numRatings);
                currentAuth.getCurrentUser().getPurchasedMovies().get(i).setNumRatings(numRatings);
                // calculate rate
                int avgRating = (currentAuth.getCurrentUser().getWatchedMovies().get(i).getNumRatings() + rating) / numRatings;
                currentAuth.getCurrentUser().getWatchedMovies().get(i).setRating(avgRating);
                currentAuth.getCurrentUser().getPurchasedMovies().get(i).setNumRatings(avgRating);

                // update current user rated list
                currentAuth.getCurrentUser().getRatedMovies().add(new Movies(currentAuth.getCurrentUser().getWatchedMovies().get(i)));

                // update current movie list
                currentAuth.setCurrentMoviesList(new LinkedList<>());
                currentAuth.getCurrentMoviesList().add(new Movies(currentAuth.getCurrentUser().getRatedMovies().get(0)));

                // output message
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

        String movie = command.getMovie();

        System.out.println(currentAuth.getCurrentUser().getWatchedMovies());
        // check if you watched the movie
        for (int i = 0; i < currentAuth.getCurrentUser().getWatchedMovies().size(); i++) {
            if (currentAuth.getCurrentUser().getWatchedMovies().get(i).getName().equals(movie)) {
                // increase number of likes
                currentAuth.getCurrentUser().getWatchedMovies().get(i).setNumLikes(currentAuth.getCurrentUser().getWatchedMovies().get(i).getNumLikes() + 1);
                currentAuth.getCurrentUser().getPurchasedMovies().get(i).setNumLikes(currentAuth.getCurrentUser().getPurchasedMovies().get(i).getNumLikes() + 1);

                // update current user likes list
                currentAuth.getCurrentUser().getLikedMovies().add(new Movies(currentAuth.getCurrentUser().getWatchedMovies().get(i)));

                // update current movie list
                currentAuth.setCurrentMoviesList(new LinkedList<>());
                currentAuth.getCurrentMoviesList().add(new Movies(currentAuth.getCurrentUser().getLikedMovies().get(0)));

                // output message
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
                return;
            }
        }

        // output message for Error
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", "Error");
        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
        objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
        output.addPOJO(objectNode);

    }

    public void seeDetailsMovies (Actions command, ArrayNode output) {
        String movie = command.getMovie();

        for (int j = 0; j < currentAuth.getCurrentMoviesList().size(); j++) {
            if (currentAuth.getCurrentMoviesList().get(j).getName().equals(movie)) {

                LinkedList<Movies> currentMoviesList = new LinkedList<>();
                Movies newMovie = new Movies(currentAuth.getCurrentMoviesList().get(j));
                currentMoviesList.add(newMovie);

                // output message
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);
                objectNode.putPOJO("currentMoviesList", currentMoviesList);
                objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
                output.addPOJO(objectNode);

                currentAuth.setCurrentMoviesList(new LinkedList<>());
                currentAuth.getCurrentMoviesList().add(currentMoviesList.get(0));
                return;
            }
        }

        // output message for Error
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", "Error");
        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
        objectNode.putPOJO("currentUser", null);
        output.addPOJO(objectNode);
        currentAuth.setCurrentPage(page.type("movies"));
    }
}
