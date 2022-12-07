package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import features.*;
import fileio.Actions;
import fileio.Movies;
import fileio.Users;
import java.util.LinkedList;

public final class Commands {
    private CurrentAuthentication currentAuth;
    public CurrentAuthentication getCurrent() {
        return currentAuth;
    }
    public void setCurrent(final CurrentAuthentication currentAuthentication) {
        this.currentAuth = currentAuthentication;
    }

    /**
     *
     * @param command
     * @param users
     * @param movies
     * @param output
     */
    public void features(final Actions command, final LinkedList<Users> users,
                         final LinkedList<Movies> movies, final ArrayNode output) {
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
                moviesForFilters.filters(command, users, movies, output);
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
            default:
                System.out.println("Nothing to do here");
        }

    }
}
