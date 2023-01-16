package features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Actions;
import fileio.Movies;
import fileio.Users;
import commands.CurrentAuthentication;
import observer.GenreObservable;
import pages.PageType;

import java.util.ArrayList;
import java.util.LinkedList;

public final class SeeDetailsFeatures {
    private static final int MAX_RATING = 5;
    private final ObjectMapper objectMapper = new ObjectMapper();
    // create a PageType object to get different types of pages
    private static final PageType PAGE_TYPE = new PageType();

    /**
     * See Details - Purchase
     *
     * When accessing the See Details page of a specified movie users can purchase the indicated
     * movie
     * @param command
     * @param output
     */
    public void purchase(final Actions command, final ArrayNode output) {
        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();

        // only on SeeDetails page
        if (!currentAuth.getCurrentPage().getPageType().equals("see details")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        String movieName;

        if (command.getMovie() != null) {
            movieName = command.getMovie();
        } else {
            movieName = currentAuth.getCurrentMoviesList().get(0).getName();
        }

        Movies movie = currentAuth.getCurrentMoviesList().get(0);

        if (movie.getName().equals(movieName)) {
            // check if the movie hasn't already been purchased
            if (!currentAuth.getCurrentUser().getPurchasedMovies().contains(movie)) {
                Users currentUser = currentAuth.getCurrentUser();
                String accType = currentUser.getCredentials().getAccountType();

                // charge for purchasing a movie
                if (accType.equals("standard") || currentUser.getNumFreePremiumMovies() == 0) {
                    int numTokens = currentUser.getTokensCount();
                    currentUser.setTokensCount(numTokens - 2);
                } else {
                    int numFreePremiumMovies;
                    numFreePremiumMovies = currentUser.getNumFreePremiumMovies();
                    currentUser.setNumFreePremiumMovies(numFreePremiumMovies - 1);
                }

                // update current user purchased list
                currentAuth.getCurrentUser().getPurchasedMovies().add(movie);

                // output message for successful purchase
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);

                ArrayList<Movies> currentMoviesList = new ArrayList<>();
                for (int j = 0; j < currentAuth.getCurrentMoviesList().size(); j++) {
                    movie = new Movies(currentAuth.getCurrentMoviesList().get(j));
                    currentMoviesList.add(movie);
                }
                objectNode.putPOJO("currentMoviesList", currentMoviesList);
                objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
                output.addPOJO(objectNode);
                return;
            } else {
                // output message for Error
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", "Error");
                objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                objectNode.putPOJO("currentUser", null);
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

        currentAuth.setCurrentPage(PAGE_TYPE.type("movies"));
        currentAuth.getPageHistory().pop();
    }

    /**
     * See Details - Watch
     *
     * When accessing the See Details page of a specified movie users can watch the indicated
     * movie only if they have already purchased it
     * @param command
     * @param output
     */
    public void watch(final Actions command, final ArrayNode output) {
        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();

        // only on SeeDetails page
        if (!currentAuth.getCurrentPage().getPageType().equals("see details")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        String movieName;

        if (command.getMovie() != null) {
            movieName = command.getMovie();
        } else {
            movieName = currentAuth.getCurrentMoviesList().get(0).getName();
        }

        // check if you purchased the movie
        for (int i = 0; i < currentAuth.getCurrentUser().getPurchasedMovies().size(); i++) {
            Movies purchasedMovie = currentAuth.getCurrentUser().getPurchasedMovies().get(i);
            String purchasedMovieName = purchasedMovie.getName();

            if (purchasedMovieName.equals(movieName)) {
                // check if you have already seen the movie
                if (!currentAuth.getCurrentUser().getWatchedMovies().contains(purchasedMovie)) {
                    // update current user watched list
                    if (!currentAuth.getCurrentUser().getWatchedMovies().contains(purchasedMovie)) {
                        currentAuth.getCurrentUser().getWatchedMovies().add(purchasedMovie);
                    }
                }

                // output message for watching the movie
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);
                ArrayList<Movies> currentMoviesList = new ArrayList<>();
                for (int j = 0; j < currentAuth.getCurrentMoviesList().size(); j++) {
                    Movies movie = new Movies(currentAuth.getCurrentMoviesList().get(j));
                    currentMoviesList.add(movie);
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
        currentAuth.setCurrentPage(PAGE_TYPE.type("movies"));
        currentAuth.getPageHistory().pop();
    }

    /**
     * See Details - Rate
     *
     * When accessing the See Details page of a specified movie users can rate the indicated
     * movie only if they have already watched it
     * @param command
     * @param output
     */
    public void rate(final Actions command, final ArrayNode output) {
        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();

        // only on SeeDetails page
        if (!currentAuth.getCurrentPage().getPageType().equals("see details")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        String movieName;

        if (command.getMovie() != null) {
            movieName = command.getMovie();
        } else {
            movieName = currentAuth.getCurrentMoviesList().get(0).getName();
        }

        double rating = command.getRate();

        if (rating > MAX_RATING) {
            // output message
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        // check if you watched the movie
        for (int i = 0; i < currentAuth.getCurrentUser().getWatchedMovies().size(); i++) {
            Movies movie = currentAuth.getCurrentUser().getWatchedMovies().get(i);

            if (movie.getName().equals(movieName)) {
                // check if you have already rated the movie
                if (!currentAuth.getCurrentUser().getRatedMovies().contains(movie)) {
                    // increase number of likes
                    int numRatings = movie.getNumRatings() + 1;
                    movie.setNumRatings(numRatings);

                    // calculate new rating
                    double avgRating = (movie.getRating() * (numRatings - 1) + rating) / numRatings;
                    movie.setRating(avgRating);

                    // update current user rated list
                    if (!currentAuth.getCurrentUser().getRatedMovies().contains(movie)) {
                        currentAuth.getCurrentUser().getRatedMovies().add(movie);
                    }

                    // save movie's rating
                    currentAuth.getCurrentUser().getRating().put(movieName, rating);
                } else {
                    // old rating
                    double oldRating = currentAuth.getCurrentUser().getRating().get(movieName);
                    // calculate new rating
                    double avgRating = (movie.getRating() * (movie.getNumRatings()) - oldRating
                            + rating) / movie.getNumRatings();
                    movie.setRating(avgRating);

                    // update current user rated list
                    if (!currentAuth.getCurrentUser().getRatedMovies().contains(movie)) {
                        currentAuth.getCurrentUser().getRatedMovies().add(movie);
                    }
                }

                // output message for rating the movie
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);
                ArrayList<Movies> currentMoviesList = new ArrayList<>();
                for (int j = 0; j < currentAuth.getCurrentMoviesList().size(); j++) {
                    movie = new Movies(currentAuth.getCurrentMoviesList().get(j));
                    currentMoviesList.add(movie);
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
        currentAuth.setCurrentPage(PAGE_TYPE.type("movies"));
        currentAuth.getPageHistory().pop();
    }

    /**
     * See Details - Like
     *
     * When accessing the See Details page of a specified movie users can like the indicated
     * movie only if they have already watched it
     * @param command
     * @param output
     */
    public void like(final Actions command, final ArrayNode output) {
        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();

        // only on SeeDetails page
        if (!currentAuth.getCurrentPage().getPageType().equals("see details")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        String movieName;

        if (command.getMovie() != null) {
            movieName = command.getMovie();
        } else {
            movieName = currentAuth.getCurrentMoviesList().get(0).getName();
        }

        // check if you watched the movie
        for (int i = 0; i < currentAuth.getCurrentUser().getWatchedMovies().size(); i++) {
            Movies movie = currentAuth.getCurrentUser().getWatchedMovies().get(i);

            if (movie.getName().equals(movieName)) {
                // increase number of likes
                int numLikes;
                numLikes = currentAuth.getCurrentUser().getPurchasedMovies().get(i).getNumLikes();
                currentAuth.getCurrentUser().getWatchedMovies().get(i).setNumLikes(numLikes + 1);

                // update current user likes list
                if (!currentAuth.getCurrentUser().getLikedMovies().contains(movie)) {
                    currentAuth.getCurrentUser().getLikedMovies().add(movie);
                }

                // output message for liking the movie
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);

                ArrayList<Movies> currentMoviesList = new ArrayList<>();
                for (int j = 0; j < currentAuth.getCurrentMoviesList().size(); j++) {
                    movie = new Movies(currentAuth.getCurrentMoviesList().get(j));
                    currentMoviesList.add(movie);
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
        currentAuth.setCurrentPage(PAGE_TYPE.type("movies"));
        currentAuth.getPageHistory().pop();
    }

    /**
     * See Details
     *
     * When accessing the See Details page of a specified movie, first we display movies
     * specifications
     * @param command
     * @param output
     */
    public void seeDetailsMovies(final Actions command, final LinkedList<Movies> movies,
                                 final ArrayNode output) {
        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();

        String movie = command.getMovie();

        for (int j = 0; j < currentAuth.getCurrentMoviesList().size(); j++) {
            if (currentAuth.getCurrentMoviesList().get(j).getName().equals(movie)) {

                LinkedList<Movies> currentMoviesList = new LinkedList<>();
                Movies newMovie = currentAuth.getCurrentMoviesList().get(j);
                currentMoviesList.add(newMovie);
                currentAuth.setCurrentMoviesList(currentMoviesList);

                // output message
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);

                LinkedList<Movies> moviesListToPrint = new LinkedList<>();
                moviesListToPrint.add(new Movies(newMovie));

                objectNode.putPOJO("currentMoviesList", moviesListToPrint);
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

        // go back to Movies page
        currentAuth.setCurrentPage(PAGE_TYPE.type("movies"));
        currentAuth.getPageHistory().pop();

        currentAuth.setCurrentMoviesList(new LinkedList<>());
        for (Movies value : movies) {
            // current User's Country
            String userCountry = currentAuth.getCurrentUser().getCredentials().getCountry();

            // populate current User's MovieList with non-banned movies
            if (!value.getCountriesBanned().contains(userCountry)) {
                currentAuth.getCurrentMoviesList().add(value);
            }
        }
    }

    /**
     * See Details - Like
     *
     * When accessing the See Details page of a specified movie users can also subscribe to
     * current movie's genres
     * @param command
     * @param output
     */
    public void seeDetailsSubscribe(final Actions command, final ArrayNode output) {
        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();

        // only on SeeDetails page
        if (!currentAuth.getCurrentPage().getPageType().equals("see details")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        String subscribedGenre = command.getSubscribedGenre();
        GenreObservable genreObservable = GenreObservable.getInstance();

        // check if you are already subscribed to the indicated genre
        if (genreObservable.getGenres().containsKey(subscribedGenre)) {
            if (genreObservable.getGenres().get(subscribedGenre).
                    contains(currentAuth.getCurrentUser())) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", "Error");
                objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                objectNode.putPOJO("currentUser", null);
                output.addPOJO(objectNode);
                return;
            }
        }

        // check if the indicated genre is part of current movie's genres
        if (currentAuth.getCurrentMoviesList().get(0).getGenres().contains(subscribedGenre)) {
            GenreObservable.getInstance().attach(currentAuth.getCurrentUser(), subscribedGenre);
        } else {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }
    }
}
