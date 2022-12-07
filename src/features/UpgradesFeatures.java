package features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Actions;
import fileio.Movies;
import fileio.Users;
import main.CurrentAuthentication;

import java.util.ArrayList;
import java.util.LinkedList;

public final class UpgradesFeatures {
    public static final int PREMIUM_COST = 10;
    private CurrentAuthentication currentAuth;
    public CurrentAuthentication getCurrent() {
        return currentAuth;
    }
    public void setCurrent(final CurrentAuthentication currentAuthentication) {
        this.currentAuth = currentAuthentication;
    }
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @param command
     * @param users
     * @param movies
     * @param output
     */
    public void buyTokens(final Actions command, final LinkedList<Users> users,
                          final LinkedList<Movies> movies, final ArrayNode output) {
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
        int tokensNumber = currentAuth.getCurrentUser().getTokensCount();
        currentAuth.getCurrentUser().setTokensCount(tokensNumber + count);
        String balanceValue = currentAuth.getCurrentUser().getCredentials().getBalance();
        int newBalance = Integer.parseInt(balanceValue) - count;
        // set Balance
        currentAuth.getCurrentUser().getCredentials().setBalance(Integer.toString(newBalance));
    }

    /**
     *
     * @param command
     * @param users
     * @param movies
     * @param output
     */
    public void buyPremiumAccount(final Actions command, final LinkedList<Users> users,
                                  final LinkedList<Movies> movies, final ArrayNode output) {
        // only on Upgrades page
        if (!currentAuth.getCurrentPage().getPageType().equals("upgrades")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        int tokensNumber = currentAuth.getCurrentUser().getTokensCount();
        currentAuth.getCurrentUser().setTokensCount(tokensNumber - PREMIUM_COST);
        currentAuth.getCurrentUser().getCredentials().setAccountType("premium");

    }
}
