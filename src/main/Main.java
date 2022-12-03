package main;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

        // call getInstance to retrieve the object available from the class
        Homepage homepage = Homepage.getInstance();

        // create a PageType object to get different types of pages
        PageType page = new PageType();
        Page moviesPage = page.type("Movies");
        Page seeDetailsPage = page.type("SeeDetails");
        Page upgradesPage = page.type("Upgrades");
        Page logoutPage = page.type("Logout");

        LinkedList<Users> users = new LinkedList<>();
        for (int i = 0; i < inputData.getUsers().size(); i++) {
            Users user = new Users(inputData.getUsers().get(i));
            users.addLast(user);
        }

        System.out.println(users.get(0).getCredentials().getName());

        LinkedList<Movies> movies = new LinkedList<>();
        for (int i = 0; i < inputData.getMovies().size(); i++) {
            Movies movie = new Movies(inputData.getMovies().get(i));
            movies.addLast(movie);
        }

        for (int i = 0; i < inputData.getActions().size(); i++) {

        }

        // output
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
    }
}
