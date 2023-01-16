package features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Actions;
import fileio.Users;
import commands.CurrentAuthentication;
import pages.PageType;

import java.util.ArrayList;
import java.util.LinkedList;

public final class LoginFeatures {
    private final ObjectMapper objectMapper = new ObjectMapper();
    // create a PageType object to get different types of pages
    private static final PageType PAGE_TYPE = new PageType();

    /**
     * Login
     *
     * When on login feature users try to access their account using their credentials
     * @param command
     * @param users
     * @param output
     */
    public void login(final Actions command, final LinkedList<Users> users,
                       final ArrayNode output) {
        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();

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

        for (Users user : users) {
            if (user.getCredentials().getName().equals(username)) {
                if (user.getCredentials().getPassword().equals(password)) {
                    // login successfully
                    currentAuth.setCurrentUser(user);

                    // move to HomePageAuthenticated
                    currentAuth.setCurrentPage(PAGE_TYPE.type("HomePageAuthenticated"));
                    currentAuth.getPageHistory().push("login");

                    // output message for successful authentication
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
        currentAuth.setCurrentPage(PAGE_TYPE.type("HomePageNonAuthenticated"));
        currentAuth.getPageHistory().pop();
    }
}
