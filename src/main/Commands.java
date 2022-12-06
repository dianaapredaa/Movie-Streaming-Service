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
    ObjectNode objectNode;

    public void features(Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output) {
        switch (command.getFeature()) {
            case ("login"):
                // only on Login page
                if (!currentAuth.currentPage.pageType.equals("login")) {
                    objectNode = objectMapper.createObjectNode();
                    objectNode.putPOJO("error", "Error");
                    objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                    objectNode.putPOJO("currentUser", null);
                    output.addPOJO(objectNode);
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
                            objectNode = objectMapper.createObjectNode();
                            objectNode.putPOJO("error", null);
                            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                            objectNode.putPOJO("currentUser", new Users(currentAuth.currentUser));
                            output.addPOJO(objectNode);
                            break;
                        }
                    }
                }
                // login failed
                if (!loginSuccessfully) {
                    // output message
                    objectNode = objectMapper.createObjectNode();
                    objectNode.putPOJO("error", "Error");
                    objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                    objectNode.putPOJO("currentUser", null);
                    output.addPOJO(objectNode);
                    // move back to HomePageNonAuthenticated
                    currentAuth.currentPage = page.type("HomePageNonAuthenticated");
                }
                break;
            case ("register"):
                // only on Register page
                if (!currentAuth.currentPage.pageType.equals("register")) {
                    objectNode = objectMapper.createObjectNode();
                    objectNode.putPOJO("error", "Error");
                    objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                    objectNode.putPOJO("currentUser", null);
                    output.addPOJO(objectNode);
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
                            objectNode = objectMapper.createObjectNode();
                            objectNode.putPOJO("error", "Error");
                            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
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
                    objectNode = objectMapper.createObjectNode();
                    objectNode.putPOJO("error", null);
                    objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                    objectNode.putPOJO("currentUser", new Users(currentAuth.currentUser));
                    output.addPOJO(objectNode);
                }
                break;
            case ("search"):
                // only on Movies Page
                if (!currentAuth.currentPage.pageType.equals("movies")) {
                objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", "Error");
                objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                objectNode.putPOJO("currentUser", null);
                output.addPOJO(objectNode);
                    break;
                }

                // startsWith
                String startsWith = command.getStartsWith();

                // find startsWith movies
                ArrayList<Movies> currentMoviesList1 = new ArrayList<>();
                for (int i = 0; i < currentAuth.currentMoviesList.size(); i++) {
                    if (currentAuth.currentMoviesList.get(i).getName().contains(startsWith)) {
                        currentMoviesList1.add(new Movies(currentAuth.currentMoviesList.get(i)));
                    }
                }

                objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);
                objectNode.putPOJO("currentMoviesList", currentMoviesList1);
                objectNode.putPOJO("currentUser", new Users(currentAuth.currentUser));
                output.addPOJO(objectNode);

                break;
            case ("filter"):
                // only on Movies Page
                if (!currentAuth.currentPage.pageType.equals("movies")) {
                    objectNode = objectMapper.createObjectNode();
                    objectNode.putPOJO("error", "Error");
                    objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                    objectNode.putPOJO("currentUser", null);
                    output.addPOJO(objectNode);
                    break;
                }

                String rating = "increasing";
                String duration = "increasing";
                ArrayList<String> genre = new ArrayList<>();
                ArrayList<String> actors = new ArrayList<>();
                actors.add("Keanu Reeves");

                ArrayList<Movies> currentMoviesList2 = new ArrayList<>();

                for (int i = 0; i < currentAuth.currentMoviesList.size(); i++) {
                    for (int j = 0; j < actors.size(); j ++) {
                        if (currentAuth.currentMoviesList.get(i).getActors().contains(actors.get(j))) {
                            currentMoviesList2.add(currentAuth.currentMoviesList.get(i));
                        }
                    }
                }

                for (int i = 0; i < currentMoviesList2.size() - 1; i++) {
                    for (int j = 0; j < currentMoviesList2.size() - i - 1; j ++) {
                        if (rating.equals("increasing")) {
                            if (currentMoviesList2.get(j).getRating() > currentMoviesList2.get(j + 1).getRating()) {
                                Movies auxMovie = currentMoviesList2.get(j);
                                currentMoviesList2.remove(j);
                                currentMoviesList2.add(j + 1, auxMovie);
                            }
                        }

                        if (rating.equals("decreasing")) {
                            if (currentMoviesList2.get(j).getRating() < currentMoviesList2.get(j + 1).getRating()) {
                                Movies auxMovie = currentMoviesList2.get(j);
                                currentMoviesList2.remove(j);
                                currentMoviesList2.add(j + 1, auxMovie);
                            }
                        }
                        if (currentMoviesList2.get(j).getRating() == currentMoviesList2.get(j + 1).getRating()) {
                            if (duration.equals("increasing")) {
                                if (currentMoviesList2.get(j).getDuration() > currentMoviesList2.get(j + 1).getDuration()) {
                                    Movies auxMovie = currentMoviesList2.get(j);
                                    currentMoviesList2.remove(j);
                                    currentMoviesList2.add(j + 1, auxMovie);
                                }
                            }

                            if (duration.equals("decreasing")) {
                                if (currentMoviesList2.get(j).getDuration() < currentMoviesList2.get(j + 1).getDuration()) {
                                    Movies auxMovie = currentMoviesList2.get(j);
                                    currentMoviesList2.remove(j);
                                    currentMoviesList2.add(j + 1, auxMovie);
                                }
                            }
                        }
                    }
                }
                objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);
                objectNode.putPOJO("currentMoviesList", currentMoviesList2);
                objectNode.putPOJO("currentUser", new Users(currentAuth.currentUser));
                output.addPOJO(objectNode);
                break;

            case ("purchase"):
                // only on SeeDetails page
//                if (!currentAuth.currentPage.pageType.equals("see details")) {
//                    objectNode = objectMapper.createObjectNode();
//                    objectNode.putPOJO("error", "Error");
//                    objectNode.putPOJO("currentMoviesList", new ArrayList<>());
//                    objectNode.putPOJO("currentUser", null);
//                    output.addPOJO(objectNode);
//                    break;
//                }
//
//                String objectType = command.getObjectType();
//                String movie = command.getMovie();
//
//                if (currentAuth.currentUser.getCredentials().getAccountType().equals("premium")) {
//                    currentAuth.currentUser.setPremiumAccount(currentAuth.currentUser.getPremiumAccount() - 1);
//                } else {
//                    currentAuth.currentUser.setTokensCount(currentAuth.currentUser.getTokensCount() - 2);
//                }
//
//                String movieTitle = command.getMovie();
//                int index = -1;
//
//                for (int i = 0; i < currentAuth.currentMoviesList.size(); i++) {
//                    if (currentAuth.currentMoviesList.get(i).getName().equals(movieTitle)) {
//                        index = i;
//                        break;
//                    }
//                }
//
//                if (index != -1) {
//                    currentAuth.purchasedMovies.add(currentAuth.currentMoviesList.get(index));
//                    // output message
//                    objectNode = objectMapper.createObjectNode();
//                    objectNode.putPOJO("error", null);
//                    objectNode.putPOJO("currentMoviesList", new ArrayList<>());
//                    objectNode.putPOJO("currentUser", new Users(currentAuth.currentUser));
//                    output.addPOJO(objectNode);
//                } else {
//                    // output message
//                    objectNode = objectMapper.createObjectNode();
//                    objectNode.putPOJO("error", "Error");
//                    objectNode.putPOJO("currentMoviesList", new ArrayList<>());
//                    objectNode.putPOJO("currentUser", null);
//                    output.addPOJO(objectNode);
//                }

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
                // only on Upgrades page
                if (!currentAuth.currentPage.pageType.equals("upgrades")) {
                    objectNode = objectMapper.createObjectNode();
                    objectNode.putPOJO("error", "Error");
                    objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                    objectNode.putPOJO("currentUser", null);
                    output.addPOJO(objectNode);
                    break;
                }
                // count
                int count = command.getCount();
                // set Tokens
                currentAuth.currentUser.setTokensCount(currentAuth.currentUser.getTokensCount() + count);
                int newBalance = Integer.parseInt(currentAuth.currentUser.getCredentials().getBalance()) - count;
                // set Balance
                currentAuth.currentUser.getCredentials().setBalance(Integer.toString(newBalance));
                break;
            case ("buy premium account"):
                // only on Upgrades page
                if (!currentAuth.currentPage.pageType.equals("upgrades")) {
                    objectNode = objectMapper.createObjectNode();
                    objectNode.putPOJO("error", "Error");
                    objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                    objectNode.putPOJO("currentUser", null);
                    output.addPOJO(objectNode);
                    break;
                }

                currentAuth.currentUser.setTokensCount(currentAuth.currentUser.getTokensCount() - 10);
                currentAuth.currentUser.getCredentials().setAccountType("premium");
                break;
            case ("like"):

                // check if you watched the movie
                break;
        }

    }
}
