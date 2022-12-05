package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Actions;
import fileio.Movies;
import fileio.Users;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.LinkedList;

public class Commands {
    private ObjectMapper objectMapper = new ObjectMapper();
    private CurrentAuthentication currentAuth;
    public CurrentAuthentication getCurrent() {
        return currentAuth;
    }
    public void setCurrent(CurrentAuthentication currentAuth) {
        this.currentAuth = currentAuth;
    }

    PageType page = new PageType();

    public void features(Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output){
        switch (command.getFeature()) {
            case ("login"):
                // only on Login page
                if (!currentAuth.currentPage.pageType.equals("login")) {
                    break;
                }
                String username = command.getCredentials().getName();
                String password = command.getCredentials().getPassword();
                boolean loginSuccessfully = false;

                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getCredentials().getName().equals(username)) {
                        if (users.get(i).getCredentials().getPassword().equals(password)) {
                            // login successfully
                            loginSuccessfully = true;
                            currentAuth.currentUser = users.get(i);

                            // move to HomePageAuthenticated
                            currentAuth.currentPage = page.type("HomePageAuthenticated");

                            // output message
                            ObjectNode objectNode = objectMapper.createObjectNode();
                            objectNode.putPOJO("error", "null");
                            objectNode.putPOJO("currentMoviesList", currentAuth.currentMoviesList);
                            objectNode.putPOJO("currentUser", new Users(currentAuth.currentUser));
                            output.addPOJO(objectNode);
                            break;
                        }
                    }
                }
                // login failed
                if (!loginSuccessfully) {
                    // output message
                    ObjectNode objectNode = objectMapper.createObjectNode();
                    objectNode.putPOJO("error", "Error");
                    objectNode.putPOJO("currentMoviesList", currentAuth.currentMoviesList);
                    objectNode.putPOJO("currentUser", null);
                    output.addPOJO(objectNode);
                    // move back to HomePageNonAuthenticated
                    currentAuth.currentPage = page.type("HomePageNonAuthenticated");
                }
                break;
            case ("register"):
                // only on Register page
                if (!currentAuth.currentPage.pageType.equals("register")) {
                    break;
                }
                username = command.getCredentials().getName();
                password = command.getCredentials().getPassword();
                boolean alreadyRegistered = false;

                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getCredentials().getName().equals(username)) {
                        if (users.get(i).getCredentials().getPassword().equals(password)) {
                            // already Registered
                            currentAuth.currentPage = page.type("HomePageNonAuthenticated");
                            // output message
                            ObjectNode objectNode = objectMapper.createObjectNode();
                            objectNode.putPOJO("error", "Error");
                            objectNode.putPOJO("currentMoviesList", currentAuth.currentMoviesList);
                            objectNode.putPOJO("currentUser", null);
                            output.addPOJO(objectNode);
                            break;
                        }
                    }
                }
                if (!alreadyRegistered) {
                    // register using given credentials
                    Users newUser = new Users(command.getCredentials());
                    users.add(newUser);
                    currentAuth.currentUser = newUser;

                    // move to HomePageAuthenticated
                    currentAuth.currentPage = page.type("HomePageAuthenticated");

                    // output message
                    ObjectNode objectNode = objectMapper.createObjectNode();
                    objectNode.putPOJO("error", "null");
                    objectNode.putPOJO("currentMoviesList", currentAuth.currentMoviesList);
                    objectNode.putPOJO("currentUser", new Users(currentAuth.currentUser));
                    output.addPOJO(objectNode);
                }
                break;
            case ("search"):
                // only on Movies Page
//                if (!currentAuth.currentPage.pageType.equals("movies")) {
//                    System.out.println(currentAuth.currentPage.pageType);
//                    break;
//                }

                // startsWith
                String startsWith = command.getStartsWith();

                // find startsWith movies
                for (int i = 0; i < movies.size(); i++) {
                    if (movies.get(i).getName().contains(startsWith)) {
                        Movies newMovie = new Movies(movies.get(i));
                        currentAuth.currentMoviesList.add(newMovie);
                    }
                }

                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", "null");
                objectNode.putPOJO("currentMoviesList", currentAuth.currentMoviesList);
                objectNode.putPOJO("currentUser", new Users(currentAuth.currentUser));
                output.addPOJO(objectNode);

                // empty currentMoviesList
                currentAuth.currentMoviesList = new LinkedList<>();

                break;
            case ("filters"):
                // only on Movies Page
//                if (!currentAuth.currentPage.pageType.equals("movies")) {
//                    System.out.println(currentAuth.currentPage.pageType);
//                    break;
//                }

                // sort
                    // rating
                    // duration
                // filters
                    // contains
                        // genre
                        // actors
                break;

            case ("purchase"):
                // only on SeeDetails

                // objectType
                    // movie
                    // rate
                break;
            case ("watch"):
                // check if you purchased the movie

                break;
            case ("rate"):
                // rate
                break;
            case ("buy tokens"):
                // count
                break;
            case ("buy premium account"):
                break;
            case ("like"):

                // check if you watched the movie
                break;
        }

    }
}
