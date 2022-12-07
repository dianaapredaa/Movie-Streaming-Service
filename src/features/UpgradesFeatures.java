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

public class UpgradesFeatures {
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

    public void buyTokens(Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output) {
        // only on Upgrades page
        if (!currentAuth.getCurrentPage().getPageType().equals("upgrades")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }
        // count
        int count = command.getCount();
        // set Tokens
        currentAuth.getCurrentUser().setTokensCount(currentAuth.getCurrentUser().getTokensCount() + count);
        int newBalance = Integer.parseInt(currentAuth.getCurrentUser().getCredentials().getBalance()) - count;
        // set Balance
        currentAuth.getCurrentUser().getCredentials().setBalance(Integer.toString(newBalance));
    }

    public void buyPremiumAccount (Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output) {
        // only on Upgrades page
        if (!currentAuth.getCurrentPage().getPageType().equals("upgrades")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }
        currentAuth.getCurrentUser().setTokensCount(currentAuth.getCurrentUser().getTokensCount() - 10);
        currentAuth.getCurrentUser().getCredentials().setAccountType("premium");

    }
}
