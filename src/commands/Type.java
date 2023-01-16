package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import features.MoviesFeatures;
import features.SeeDetailsFeatures;
import fileio.Actions;
import fileio.Movies;
import fileio.Users;
import pages.PageType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public final class Type {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // create a PageType object to get different types of pages
    private static final PageType PAGE_TYPE = new PageType();

    /**
     * On Page
     *
     * When having to deal with on page actions we have to see if the specified feature
     * matches one's page possible actions.
     * @param command
     * @param users
     * @param movies
     * @param output
     */
    public void onPage(final Actions command, final LinkedList<Users> users,
                       final LinkedList<Movies> movies, final ArrayNode output) {
        // jump to features
        Features commands = new Features();
        commands.features(command, output, users, movies);
    }

    /**
     * Change Page
     *
     * When changing pages we have to make sure that the change is possible
     * according to our web structure.
     * We also have to take action depending on the page we are moving to.
     * If the new page is logout, we have to disconnect the current user.
     * If we move to Movies page, current user's movie list will be displayed.
     * If we move to See Details page, of a specified movie, if the movie exist
     * we have to display its properties
     * @param command
     * @param movies
     * @param output
     */
    public void changePage(final Actions command, final ArrayNode output,
                           final LinkedList<Movies> movies) {
        String pageName = command.getPage();

        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();

        // check if is possible to change pages
        if (currentAuth.getCurrentPage().getNextPossiblePage().contains(pageName)
                || pageName.equals(currentAuth.getCurrentPage().getPageType())) {
            // add last page to the history stack
            currentAuth.getPageHistory().push(currentAuth.getCurrentPage().getPageType());
            // if possible change page
            currentAuth.setCurrentPage(PAGE_TYPE.type(pageName));


        } else {
            // output message if you can not change page
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        // if the new page is Logout Page
        if (pageName.equals("logout")) {
            currentAuth.setCurrentUser(null);
            currentAuth.setCurrentPage(PAGE_TYPE.type("HomePageNonAuthenticated"));
            currentAuth.setCurrentMoviesList(new LinkedList<>());
            currentAuth.setPageHistory(new Stack());
            return;
        }

        // if the new page is Movies
        if (pageName.equals("movies")) {
            MoviesFeatures moviesFeatures = new MoviesFeatures();
            moviesFeatures.onMoviesPage(movies, output);
            return;
        }

        // if the new page is SeeDetails
        if (pageName.equals("see details")) {
            SeeDetailsFeatures seeDetailsFeatures = new SeeDetailsFeatures();
            seeDetailsFeatures.seeDetailsMovies(command, movies, output);
            return;
        }
    }

    /**
     * Back
     *
     * Here we navigate back to the last page we were on.
     * @param output
     * @param movies
     */
    public void back(final ArrayNode output, final LinkedList<Movies> movies) {
        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();

        // check if the previews page is login/register or if anybody is authenticated
        if (currentAuth.getCurrentUser() == null
            || currentAuth.getPageHistory().isEmpty()) {
            // nobody is authenticated
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        // check if previews page is login/register
        if (currentAuth.getPageHistory().peek().equals("login")
            || currentAuth.getPageHistory().peek().equals("register")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        // update current page
        currentAuth.setCurrentPage(PAGE_TYPE.type((String) currentAuth.getPageHistory().pop()));

        // if the new page is Movies
        if (currentAuth.getCurrentPage().getPageType().equals("movies")) {
            MoviesFeatures moviesFeatures = new MoviesFeatures();
            moviesFeatures.onMoviesPage(movies, output);
            return;
        }

    }

    /**
     * Database
     *
     * We can all add and delete movies as we want.
     * @param command
     * @param output
     * @param users
     * @param movies
     */
    public void database(final Actions command, final ArrayNode output,
                         final LinkedList<Users> users, final LinkedList<Movies> movies) {
        Features commands = new Features();
        commands.features(command, output, users, movies);
    }
}
