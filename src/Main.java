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
import commands.*;
import pages.PageType;

public final class Main {
    private Main() {

    }

    /**
     * Main method
     *
     * Read input and write output
     * We make a copy of the input to work with, initialize current authentication and iterate
     * through command list to determine action type
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

        // Singleton instantiation
        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();

        // initialize all values
        currentAuth.setCurrentPage(currentAuth.getPage().type("HomePageNonAuthenticated"));
        currentAuth.setCurrentMoviesList(new LinkedList<>());
        currentAuth.setCurrentUser(null);
        currentAuth.setPageHistory(new Stack());

        currentAuth.getPageHistory().push("HomePageNonAuthenticated");

        Features commands = new Features();
        commands.setCurrent(currentAuth);

        for (int i = 0; i < inputData.getActions().size(); i++) {
            Actions command =  inputData.getActions().get(i);

            switch (command.getType()) {
                case("on page"):
//                    Type onPage = new Type();
//                    onPage.setCurrent(currentAuth);
//                    onPage.onPage(command, users, movies, output);
                    // jump to features if possible
                    if (command.getFeature() != null) {
                        commands.features(command, output, users, movies);
                    }
                    break;
                case("change page"):
//                    Type changePage = new Type();
//                    changePage.setCurrent(currentAuth);
//                    changePage.onPage(command, movies, output);
                    // jump to features if possible
                    String pageName = command.getPage();

                    // check if is possible to change pages
                    if (currentAuth.getCurrentPage().getNextPossiblePage().contains(pageName)
                            || pageName.equals(currentAuth.getCurrentPage().getPageType())) {
                        // change page
                        currentAuth.setCurrentPage(page.type(pageName));
                        currentAuth.getPageHistory().push(pageName);
                    } else {
                        // output message for can not change page
                        ObjectNode objectNode = objectMapper.createObjectNode();
                        objectNode.putPOJO("error", "Error");
                        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                        objectNode.putPOJO("currentUser", null);
                        output.addPOJO(objectNode);
                        break;
                    }

                    // if changed page is Logout Page
                    if (pageName.equals("logout")) {
                        // only on Logout Page
                        currentAuth.setCurrentUser(null);
                        currentAuth.setCurrentPage(page.type("HomePageNonAuthenticated"));
                        currentAuth.setCurrentMoviesList(new LinkedList<>());
                        currentAuth.setPageHistory(new Stack());
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
                        seeDetailsFeatures.seeDetailsMovies(command, movies, output);
                        break;
                    }
                    break;

                case("database"):
//                    Type database = new Type();
//                    database.setCurrent(currentAuth);
//                    database.onPage(command, output, users, movies);
                    commands.features(command, output, users, movies);

                    break;
                case("back"):
//                    Type back = new Type();
//                    back.setCurrent(currentAuth);
//                    back.onPage(command, movies, output);
                    if (currentAuth.getCurrentUser() == null) {
                        // nobody is authenticated
                        ObjectNode objectNode = objectMapper.createObjectNode();
                        objectNode.putPOJO("error", "Error");
                        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                        objectNode.putPOJO("currentUser", null);
                        output.addPOJO(objectNode);
                        break;
                    }

                    if (currentAuth.getPageHistory().empty()) {
                        // nowhere to go
                        ObjectNode objectNode = objectMapper.createObjectNode();
                        objectNode.putPOJO("error", "Error");
                        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                        objectNode.putPOJO("currentUser", null);
                        output.addPOJO(objectNode);
                        break;
                    }
                    currentAuth.getPageHistory().pop();
                    if (currentAuth.getPageHistory().empty()) {
                        // nowhere to go back
                        ObjectNode objectNode = objectMapper.createObjectNode();
                        objectNode.putPOJO("error", "Error");
                        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                        objectNode.putPOJO("currentUser", null);
                        output.addPOJO(objectNode);
                        break;
                    }
                    currentAuth.setCurrentPage(page.type((String) currentAuth.getPageHistory().peek()));
                default:
                    System.out.println(command.getType());
            }

        }

        // output
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
        char[] inputPath = args[0].toCharArray();
        objectWriter.writeValue(new File("checker/resources/out/out_" + inputPath[inputPath.length - 6] + ".json"), output);
    }
}
