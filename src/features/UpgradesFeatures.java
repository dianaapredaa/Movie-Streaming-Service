package features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Actions;
import commands.CurrentAuthentication;

import java.util.ArrayList;

public final class UpgradesFeatures {
    public static final int PREMIUM_COST = 10;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Upgrades - Buy Tokens
     *
     * When on buy tokens feature, users can use their balance to buy tokens
     * @param command
     * @param output
     */
    public void buyTokens(final Actions command, final ArrayNode output) {
        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();

        // only on Upgrades page
        if (!currentAuth.getCurrentPage().getPageType().equals("upgrades")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        int count = command.getCount();
        int tokensNumber = currentAuth.getCurrentUser().getTokensCount();

        // update tokens and balance value
        currentAuth.getCurrentUser().setTokensCount(tokensNumber + count);
        String balanceValue = currentAuth.getCurrentUser().getCredentials().getBalance();
        int newBalance = Integer.parseInt(balanceValue) - count;
        currentAuth.getCurrentUser().getCredentials().setBalance(Integer.toString(newBalance));

    }

    /**
     * Upgrades - Buy Premium Account
     *
     * When on buy tokens feature, users can use their tokens to buy premium subscription
     * @param output
     */
    public void buyPremiumAccount(final ArrayNode output) {
        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();

        // only on Upgrades page
        if (!currentAuth.getCurrentPage().getPageType().equals("upgrades")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }

        // puy for premium
        int tokensNumber = currentAuth.getCurrentUser().getTokensCount();

        // check if you have enough tokens to buy a premium account
        if (tokensNumber > PREMIUM_COST) {
            currentAuth.getCurrentUser().setTokensCount(tokensNumber - PREMIUM_COST);
            currentAuth.getCurrentUser().getCredentials().setAccountType("premium");
        } else {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("error", "Error");
            objectNode.putPOJO("currentMoviesList", new ArrayList<>());
            objectNode.putPOJO("currentUser", null);
            output.addPOJO(objectNode);
            return;
        }
    }
}
