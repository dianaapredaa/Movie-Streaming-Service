package main;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.*;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Main {

    public static void main(final String[] args) throws IOException {

        // read input
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(args[0]), Input.class);
        ArrayNode output = objectMapper.createArrayNode();

        // create a PageType object to get different types of pages
        PageType page = new PageType();

        LinkedList<Users> users = new LinkedList<>();
        for (int i = 0; i < inputData.getUsers().size(); i++) {
            Users user = new Users(inputData.getUsers().get(i));
            users.addLast(user);
            if (users.get(i).getCredentials().getAccountType().equals("premium")) {
                users.get(i).setPremiumAccount(15);
            }
        }

        LinkedList<Movies> movies = new LinkedList<>();
        for (int i = 0; i < inputData.getMovies().size(); i++) {
            Movies movie = new Movies(inputData.getMovies().get(i));
            movies.addLast(movie);
        }

        CurrentAuthentication currentAuth = new CurrentAuthentication();
        Commands commands = new Commands();
        commands.setCurrent(currentAuth);

        for (int i = 0; i < inputData.getActions().size(); i++) {
            Actions command =  inputData.getActions().get(i);

            switch (command.getType()) {
                case("on page"):
                    // jump to features if possible
                    if (command.getFeature() != null) {
                        commands.features(command, users, movies, output);
                    }
                    break;
                case("change page"):
                    String pageName = command.getPage();
                    // check if is possible to change pages
                    if (currentAuth.currentPage.nextPossiblePage.contains(pageName)) {
                        // change page
                        currentAuth.currentPage = page.type(pageName);
                    } else {
                        // can not change page
                        // output message
                        ObjectNode objectNode = objectMapper.createObjectNode();
                        objectNode.putPOJO("error", "Error");
                        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                        objectNode.putPOJO("currentUser", null);
                        output.addPOJO(objectNode);
                        break;
                    }

                    if (pageName.equals("logout")) {
                        // only on Logout Page
                        currentAuth.currentUser = null;
                        currentAuth.currentPage = page.type("HomePageNonAuthenticated");
                        currentAuth.currentMoviesList = new LinkedList<>();
                        break;
                    }

                    if (pageName.equals("movies")) {
                        for (int j = 0; j < movies.size(); j++) {
                            // current User's Country
                            String userCountry = currentAuth.currentUser.getCredentials().getCountry();

                            // populate current User's MovieList with non-banned movies
                            if (!movies.get(j).getCountriesBanned().contains(userCountry)) {
                                currentAuth.currentMoviesList.add(movies.get(j));
                            }
                        }
                        ObjectNode objectNode = objectMapper.createObjectNode();
                        objectNode.putPOJO("error", null);

                        ArrayList<Movies> currentMoviesList = new ArrayList<>();
                        for (int j = 0; j < currentAuth.currentMoviesList.size(); j++) {
                            Movies newMovie = new Movies(currentAuth.currentMoviesList.get(j));
                            currentMoviesList.add(newMovie);
                        }
                        objectNode.putPOJO("currentMoviesList", currentMoviesList);
                        objectNode.putPOJO("currentUser", new Users(currentAuth.currentUser));
                        output.addPOJO(objectNode);
                    }

                    // jump to features
                    if (command.getFeature() != null) {
                        commands.features(command, users, movies, output);
                    } else if (pageName.equals("see details")) {
                        String movieName = command.getMovie();

                        ArrayList<Movies> currentMoviesList5 = new ArrayList<>();
                        for (int j = 0; j < currentAuth.currentMoviesList.size(); j++) {
                            if (currentAuth.currentMoviesList.get(j).getName().equals(movieName)) {
                                currentMoviesList5.add(currentAuth.currentMoviesList.get(j));
                                // output message
                                ObjectNode objectNode = objectMapper.createObjectNode();
                                objectNode.putPOJO("error", null);
                                objectNode.putPOJO("currentMoviesList", currentMoviesList5);
                                objectNode.putPOJO("currentUser", new Users(currentAuth.currentUser));
                                output.addPOJO(objectNode);
                                break;
                            }
                        }

                        // output message
                        ObjectNode objectNode = objectMapper.createObjectNode();
                        objectNode.putPOJO("error", "Error");
                        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                        objectNode.putPOJO("currentUser", null);
                        output.addPOJO(objectNode);
                    }
                    break;
            }

        }

        // output
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
        char[] inputPath = args[0].toCharArray();
        objectWriter.writeValue(new File("checker/resources/out/out_" + inputPath[inputPath.length - 6] + ".json"), output);
    }
}
