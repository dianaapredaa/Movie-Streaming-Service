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
                    if (command.getFeature() != null)
                        commands.features(command, users, movies, output);
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
                        objectNode.putPOJO("error", "null");
                        objectNode.putPOJO("currentMoviesList", currentAuth.currentMoviesList);
                        objectNode.putPOJO("currentUser", new Users(currentAuth.currentUser));
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
                        // only on Movies page
                        currentAuth.currentMoviesList = movies;
                    }

                    // jump to features
                    if (command.getFeature() != null)
                            commands.features(command, users, movies, output);
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
