import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import commands.*;
import fileio.Actions;
import fileio.Input;
import fileio.Movies;
import fileio.Users;
import observer.GenreObservable;
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
        GenreObservable genreObservable = GenreObservable.getInstance();

        // initialize all values
        currentAuth.setCurrentPage(currentAuth.getPage().type("HomePageNonAuthenticated"));
        currentAuth.setCurrentMoviesList(new LinkedList<>());
        currentAuth.setCurrentUser(null);
        currentAuth.setPageHistory(new Stack());
        genreObservable.setGenres(new HashMap<>());

        for (int i = 0; i < inputData.getActions().size(); i++) {
            Actions command =  inputData.getActions().get(i);
//
//            ObjectNode objectNode = objectMapper.createObjectNode();
//            objectNode.putPOJO("comanda data", command.getType());
//            output.addPOJO(objectNode);

            switch (command.getType()) {
                case ("on page") -> {
                    Type onPage = new Type();
                    onPage.onPage(command, users, movies, output);
                }
                case ("change page") -> {
                    Type changePage = new Type();
                    changePage.changePage(command, output, movies);
                }
                case ("database") -> {
                    Type database = new Type();
                    database.database(command, output, users, movies);
                }
                case ("back") -> {
                    Type back = new Type();
                    back.back(output, movies);
                }
                default -> System.out.println("nothing to be done");
            }
        }

        // display recommendations if necessary
        Recommendation end = new Recommendation();
        end.recommendation(output, movies);

        // output write
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
        char[] inputPath = args[0].toCharArray();
        objectWriter.writeValue(new File("checker/resources/out/out_" + inputPath[inputPath.length - 6] + ".json"), output);
    }
}
