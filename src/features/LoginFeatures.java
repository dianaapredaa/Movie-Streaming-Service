package features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Actions;
import fileio.Movies;
import fileio.Users;
import main.CurrentAuthentication;
import main.PageType;

import java.util.ArrayList;
import java.util.LinkedList;

public class LoginFeatures {
    private CurrentAuthentication currentAuth;
    public CurrentAuthentication getCurrent() {
        return currentAuth;
    }
    public void setCurrent(CurrentAuthentication currentAuth) {
        this.currentAuth = currentAuth;
    }
    private ObjectMapper objectMapper = new ObjectMapper();

    // create a PageType object to get different types of pages
    private static PageType page = new PageType();

    public void login (Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output) {
        // only on Login page
        if (!currentAuth.getCurrentPage().getPageType().equals("login")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }
        String username = command.getCredentials().getName();
        String password = command.getCredentials().getPassword();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getCredentials().getName().equals(username)) {
                if (users.get(i).getCredentials().getPassword().equals(password)) {
                    // login successfully
                    currentAuth.setCurrentUser(users.get(i));
                    // move to HomePageAuthenticated
                    currentAuth.setCurrentPage(page.type("HomePageAuthenticated"));

                    // output message
                    ObjectNode objectNode = objectMapper.createObjectNode();
                    objectNode.putPOJO("error", null);
                    objectNode.putPOJO("currentMoviesList", new ArrayList<>());
                    objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
                    output.addPOJO(objectNode);
                    return;
                }
            }
        }

        // output message for login failed
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("error", "Error");
        objectNode.putPOJO("currentMoviesList", new ArrayList<>());
        objectNode.putPOJO("currentUser", null);
        output.addPOJO(objectNode);

        // move back to HomePageNonAuthenticated
        currentAuth.setCurrentPage(page.type("HomePageNonAuthenticated"));
    }
}
