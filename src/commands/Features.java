package commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import features.*;
import fileio.Actions;
import fileio.Movies;
import fileio.Users;
import java.util.LinkedList;

public final class Features {

    /**
     * Feature Method
     *
     * In this method we determine which feature is requested by the user
     * @param command
     * @param users
     * @param output
     */
    public void features(final Actions command, final ArrayNode output,
                         final LinkedList<Users> users, final LinkedList<Movies> movies) {
        switch (command.getFeature()) {
            case ("login") -> {
                LoginFeatures login = new LoginFeatures();
                login.login(command, users, output);
            }
            case ("register") -> {
                RegisterFeatures register = new RegisterFeatures();
                register.register(command, users, output);
            }
            case ("search") -> {
                MoviesFeatures moviesForSearch = new MoviesFeatures();
                moviesForSearch.search(command, output);
            }
            case ("filter") -> {
                MoviesFeatures moviesForFilters = new MoviesFeatures();
                moviesForFilters.filters(command, output);
            }
            case ("purchase") -> {
                SeeDetailsFeatures seeDetailsPurchase = new SeeDetailsFeatures();
                seeDetailsPurchase.purchase(command, output);
            }
            case ("watch") -> {
                SeeDetailsFeatures seeDetailsWatch = new SeeDetailsFeatures();
                seeDetailsWatch.watch(command, output);
            }
            case ("rate") -> {
                SeeDetailsFeatures seeDetailsRate = new SeeDetailsFeatures();
                seeDetailsRate.rate(command, output);
            }
            case ("buy tokens") -> {
                UpgradesFeatures upgradesBuyTokens = new UpgradesFeatures();
                upgradesBuyTokens.buyTokens(command, output);
            }
            case ("buy premium account") -> {
                UpgradesFeatures upgradesBuyPremiumAccount = new UpgradesFeatures();
                upgradesBuyPremiumAccount.buyPremiumAccount(output);
            }
            case ("like") -> {
                SeeDetailsFeatures seeDetailsLike = new SeeDetailsFeatures();
                seeDetailsLike.like(command, output);
            }
            case ("subscribe") -> {
                SeeDetailsFeatures seeDetailsSubscribe = new SeeDetailsFeatures();
                seeDetailsSubscribe.seeDetailsSubscribe(command, output);
            }
            case ("add") -> {
                AdminFeatures adminFeaturesAdd = new AdminFeatures();
                adminFeaturesAdd.add(command, movies);
            }
            case ("delete") -> {
                AdminFeatures adminFeaturesDelete = new AdminFeatures();
                adminFeaturesDelete.delete(command, movies, users);
            }
            default -> System.out.println("Nothing to do here");
        }

    }
}
