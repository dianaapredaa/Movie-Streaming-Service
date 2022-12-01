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
public class Main {

    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(args[0]), Input.class);
        ArrayNode output = objectMapper.createArrayNode();


        // call getInstance to retrieve the object available from the class
        Homepage homepage = Homepage.getInstance();

        // create a PageType object to get different types of pages
        PageType page = new PageType();

        Page movies = page.type("Movies");
        Page seeDetails = page.type("SeeDetails");
        Page upgrades = page.type("Upgrades");
        Page logout = page.type("Logout");

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(), output);
    }
}
