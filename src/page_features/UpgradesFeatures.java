package page_features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Actions;
import main.CurrentAuthentication;

import java.util.ArrayList;

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
     * Upgrades - Buy Tokens
     *
     * When on buy tokens feature, users can use their balance to buy tokens
     * @param command
     * @param output
     */
    public void buyTokens(final Actions command, final ArrayNode output) {
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
     * Upgrades - Buy Premium Account
     *
     * When on buy tokens feature, users can use their tokens to buy premium subscription
     * @param output
     */
    public void buyPremiumAccount(final ArrayNode output) {
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
