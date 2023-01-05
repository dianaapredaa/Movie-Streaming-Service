package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;
import main.Features;
import page_features.MoviesFeatures;
import page_features.SeeDetailsFeatures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public final class Type {
    private CurrentAuthentication currentAuth;
    public CurrentAuthentication getCurrent() {
        return currentAuth;
    }
    public void setCurrent(final CurrentAuthentication currentAuthentication) {
        this.currentAuth = currentAuthentication;
    }
    private final ObjectMapper objectMapper = new ObjectMapper();

    // create a PageType object to get different types of pages
    private static final PageType PAGE_TYPE = new PageType();

    Features commands = new Features();
//    commands.setCurrent(currentAuth);

    public void onPage(final Actions command, final LinkedList<Users> users,
                        final LinkedList<Movies> movies, final ArrayNode output) {
        // jump to features if possible
        if (command.getFeature() != null) {
            commands.features(command, users, movies, output);
        }
        return;
    }

    public void changePage(final Actions command, final LinkedList<Movies> movies,
                           final ArrayNode output) {
        String pageName = command.getPage();

        // check if is possible to change pages
        if (currentAuth.getCurrentPage().getNextPossiblePage().contains(pageName)
                || pageName.equals(currentAuth.getCurrentPage().getPageType())) {
            // change page
            currentAuth.setCurrentPage(PAGE_TYPE.type(pageName));
            currentAuth.getPageHistory().push(pageName);
        } else {
            // output message for can not change page
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        // if changed page is Logout Page
        if (pageName.equals("logout")) {
            // only on Logout Page
            currentAuth.setCurrentUser(null);
            currentAuth.setCurrentPage(PAGE_TYPE.type("HomePageNonAuthenticated"));
            currentAuth.setCurrentMoviesList(new LinkedList<>());
            currentAuth.setPageHistory(new Stack());
            return;
        }

        // if changed page is Movies
        if (pageName.equals("movies")) {
            MoviesFeatures moviesFeatures = new MoviesFeatures();
            moviesFeatures.setCurrent(currentAuth);
            moviesFeatures.onMoviesPage(movies, output);
            return;
        }

        // if changed page is SeeDetails
        if (pageName.equals("see details")) {
            SeeDetailsFeatures seeDetailsFeatures = new SeeDetailsFeatures();
            seeDetailsFeatures.setCurrent(currentAuth);
            seeDetailsFeatures.seeDetailsMovies(command, movies, output);
            return;
        }
    }

    public void back(final ArrayNode output) {
        if (currentAuth.getCurrentUser() == null) {
            // nobody is authenticated
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        if (currentAuth.getPageHistory().empty()) {
            // nowhere to go
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }
        currentAuth.getPageHistory().pop();
        if (currentAuth.getPageHistory().empty()) {
            // nowhere to go back
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }
        currentAuth.setCurrentPage(PAGE_TYPE.type((String) currentAuth.getPageHistory().peek()));
    }

    public void database() {

    }
}
