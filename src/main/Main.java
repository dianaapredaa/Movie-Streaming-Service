package main;
import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import features.MoviesFeatures;
import features.SeeDetailsFeatures;
import fileio.*;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class Main {
    private Main() {

    }

    /**
     *
     * @param args
     * @throws IOException
     */
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
                    if (command.getFeature() != null) {
                        commands.features(command, users, movies, output);
                    }
                    break;
                case("change page"):
                    String pageName = command.getPage();

                    // check if is possible to change pages
                    if (currentAuth.getCurrentPage().getNextPossiblePage().contains(pageName)) {
                        // change page
                        currentAuth.setCurrentPage(page.type(pageName));
                    } else {
                        // output message for can not change page
                        ObjectNode objectNode = objectMapper.createObjectNode();
                        objectNode.putPOJO("error", "Error");
                        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                        objectNode.putPOJO("currentUser", null);
                        output.addPOJO(objectNode);
                        break;
                    }

                    // jump to features
                    if (command.getFeature() != null) {
                        commands.features(command, users, movies, output);
                        break;
                    }

                    // if changed page is Logout Page
                    if (pageName.equals("logout")) {
                        // only on Logout Page
                        currentAuth.setCurrentUser(null);
                        currentAuth.setCurrentPage(page.type("HomePageNonAuthenticated"));
                        currentAuth.setCurrentMoviesList(new LinkedList<>());
                        break;
                    }

                    // if changed page is Movies
                    if (pageName.equals("movies")) {
                        MoviesFeatures moviesFeatures = new MoviesFeatures();
                        moviesFeatures.setCurrent(currentAuth);
                        moviesFeatures.onMoviesPage(movies, output);
                        break;
                    }

                    // if changed page is SeeDetails
                    if (pageName.equals("see details")) {
                        SeeDetailsFeatures seeDetailsFeatures = new SeeDetailsFeatures();
                        seeDetailsFeatures.setCurrent(currentAuth);
                        seeDetailsFeatures.seeDetailsMovies(command, output);
                        break;
                    }
                    break;
                default:
                    System.out.println("Nothing to do here");
            }

        }

        // output
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
        char[] inputPath = args[0].toCharArray();
        objectWriter.writeValue(new File("checker/resources/out/out_" + inputPath[inputPath.length - 6] + ".json"), output);
    }
}
