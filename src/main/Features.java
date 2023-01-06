package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import page_features.*;
import fileio.Actions;
import fileio.Movies;
import fileio.Users;
import java.util.LinkedList;

public final class Features {
    private CurrentAuthentication currentAuth;
    public CurrentAuthentication getCurrent() {
        return currentAuth;
    }
    public void setCurrent(final CurrentAuthentication currentAuthentication) {
        this.currentAuth = currentAuthentication;
    }

    /**
     * Feature Method
     *
     * In this method we determine which feature is requested by the user
     * @param command
     * @param users
     * @param output
     */
    public void features(final Actions command, final LinkedList<Users> users,
                         final ArrayNode output) {
        switch (command.getFeature()) {
            case ("login") -> {
                LoginFeatures login = new LoginFeatures();
                login.setCurrent(currentAuth);
                login.login(command, users, output);
            }
            case ("register") -> {
                RegisterFeatures register = new RegisterFeatures();
                register.setCurrent(currentAuth);
                register.register(command, users, output);
            }
            case ("search") -> {
                MoviesFeatures moviesForSearch = new MoviesFeatures();
                moviesForSearch.setCurrent(currentAuth);
                moviesForSearch.search(command, output);
            }
            case ("filter") -> {
                MoviesFeatures moviesForFilters = new MoviesFeatures();
                moviesForFilters.setCurrent(currentAuth);
                moviesForFilters.filters(command, output);
            }
            case ("purchase") -> {
                SeeDetailsFeatures seeDetailsPurchase = new SeeDetailsFeatures();
                seeDetailsPurchase.setCurrent(currentAuth);
                seeDetailsPurchase.purchase(command, output);
            }
            case ("watch") -> {
                SeeDetailsFeatures seeDetailsWatch = new SeeDetailsFeatures();
                seeDetailsWatch.setCurrent(currentAuth);
                seeDetailsWatch.watch(command, output);
            }
            case ("rate") -> {
                SeeDetailsFeatures seeDetailsRate = new SeeDetailsFeatures();
                seeDetailsRate.setCurrent(currentAuth);
                seeDetailsRate.rate(command, output);
            }
            case ("buy tokens") -> {
                UpgradesFeatures upgradesBuyTokens = new UpgradesFeatures();
                upgradesBuyTokens.setCurrent(currentAuth);
                upgradesBuyTokens.buyTokens(command, output);
            }
            case ("buy premium account") -> {
                UpgradesFeatures upgradesBuyPremiumAccount = new UpgradesFeatures();
                upgradesBuyPremiumAccount.setCurrent(currentAuth);
                upgradesBuyPremiumAccount.buyPremiumAccount(output);
            }
            case ("like") -> {
                SeeDetailsFeatures seeDetailsLike = new SeeDetailsFeatures();
                seeDetailsLike.setCurrent(currentAuth);
                seeDetailsLike.like(command, output);
            }
            case ("subscribe") -> {
                String subscribedGenre = command.getSubscribedGenre();

            }
            case ("database") -> {

            }
            case ("add") -> {
                Movies addedMovie = command.getAddedMovie();
            }
            case ("delete") -> {
                String deletedMovie = command.getDeletedMovie();
            }
            default -> System.out.println("Nothing to do here");
        }

    }
}
