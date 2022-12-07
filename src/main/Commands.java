package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import features.*;
import fileio.Actions;
import fileio.Movies;
import fileio.Users;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.LinkedList;

public class Commands {
    private ObjectMapper objectMapper = new ObjectMapper();
    private CurrentAuthentication currentAuth;
    public CurrentAuthentication getCurrent() {
        return currentAuth;
    }
    public void setCurrent(CurrentAuthentication currentAuth) {
        this.currentAuth = currentAuth;
    }
    PageType page = new PageType();
    ObjectNode objectNode;

    public void features(Actions command, LinkedList<Users> users, LinkedList<Movies> movies, ArrayNode output) {
        switch (command.getFeature()) {
            case ("login"):
                LoginFeatures login = new LoginFeatures();
                login.setCurrent(currentAuth);
                login.login(command, users, movies, output);
                break;
            case ("register"):
                RegisterFeatures register = new RegisterFeatures();
                register.setCurrent(currentAuth);
                register.register(command, users, movies, output);
                break;
            case ("search"):
                MoviesFeatures moviesForSearch = new MoviesFeatures();
                moviesForSearch.setCurrent(currentAuth);
                moviesForSearch.search(command, users, movies, output);
                break;
            case ("filter"):
                MoviesFeatures moviesForFilters = new MoviesFeatures();
                moviesForFilters.setCurrent(currentAuth);
                moviesForFilters.filters(command,users, movies, output);
                break;
            case ("purchase"):
                SeeDetailsFeatures seeDetailsPurchase = new SeeDetailsFeatures();
                seeDetailsPurchase.setCurrent(currentAuth);
                seeDetailsPurchase.purchase(command, users, movies, output);
                break;
            case ("watch"):
                SeeDetailsFeatures seeDetailsWatch = new SeeDetailsFeatures();
                seeDetailsWatch.setCurrent(currentAuth);
                seeDetailsWatch.watch(command, users, movies, output);
                break;
            case ("rate"):
                SeeDetailsFeatures seeDetailsRate = new SeeDetailsFeatures();
                seeDetailsRate.setCurrent(currentAuth);
                seeDetailsRate.rate(command, users, movies, output);
                break;
            case ("buy tokens"):
                UpgradesFeatures upgradesBuyTokens = new UpgradesFeatures();
                upgradesBuyTokens.setCurrent(currentAuth);
                upgradesBuyTokens.buyTokens(command, users, movies, output);
                break;
            case ("buy premium account"):
                UpgradesFeatures upgradesBuyPremiumAccount = new UpgradesFeatures();
                upgradesBuyPremiumAccount.setCurrent(currentAuth);
                upgradesBuyPremiumAccount.buyPremiumAccount(command, users, movies, output);
                break;
            case ("like"):
                SeeDetailsFeatures seeDetailsLike = new SeeDetailsFeatures();
                seeDetailsLike.setCurrent(currentAuth);
                seeDetailsLike.like(command, users, movies, output);
                break;
        }

    }
}
