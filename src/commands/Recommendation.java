package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Movies;
import fileio.Notifications;
import fileio.Users;

import java.util.*;

public final class Recommendation {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Sort by Value
     *
     * This function rearrange given hashmap decreasingly by its value
     * @param hashMap
     * @return
     */
    // function to sort hashmap by values
    public static HashMap<String, Integer> sortByValue(final HashMap<String, Integer> hashMap) {
        // create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(hashMap.entrySet());

        // sort the list by value
        list.sort((o1, o2) -> {
            return -(o1.getValue()).compareTo(o2.getValue());
        });

        // reassemble hashmap
        HashMap<String, Integer> sortedHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            sortedHashMap.put(aa.getKey(), aa.getValue());
        }
        return sortedHashMap;
    }
    public static HashMap<String, Integer> sortByKey(final HashMap<String, Integer> hashMap) {
        // create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(hashMap.entrySet());

        // sort the list by key
        list.sort((o1, o2) -> {
            return (o1.getKey()).compareTo(o2.getKey());
        });

        // reassemble hashmap
        HashMap<String, Integer> sortedHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            sortedHashMap.put(aa.getKey(), aa.getValue());
        }
        return sortedHashMap;
    }

    /**
     * Recommendation
     *
     * At the end of the program connected premium user is rewarded with
     * a movie recommendation
     * @param output
     */
    public void recommendation(final ArrayNode output, final LinkedList<Movies> movies) {
        HashMap<String, Integer> genres = new HashMap<>();

        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();
        if (currentAuth.getCurrentUser() != null) {
            // check for premium users
            if (currentAuth.getCurrentUser().getCredentials().getAccountType().equals("premium")) {

                // populate hashmap for <genre, number of likes> pairs
                for (int i = 0; i < currentAuth.getCurrentUser().getLikedMovies().size(); i++) {
                    Movies movie = currentAuth.getCurrentUser().getLikedMovies().get(i);
                    for (String genre : movie.getGenres()) {
                        if (genres.containsKey(genre)) {
                            genres.put(genre, genres.get(genre) + 1);
                        } else {
                            genres.put(genre, 1);
                        }
                    }
                }

                // current user's movie list
                ArrayList<Movies> topMovies = new ArrayList<>();

                for (Movies movie : movies) {
                    // current User's Country
                    String userCountry = currentAuth.getCurrentUser().getCredentials().getCountry();

                    // populate current User's MovieList with non-banned movies
                    if (!movie.getCountriesBanned().contains(userCountry)
                            && !currentAuth.getCurrentUser().getWatchedMovies().contains(movie)) {
                        topMovies.add(movie);
                    }
                }

                // ordinate movies decreasingly depending on their number of likes
                for (int i = 0; i < topMovies.size() - 1; i++) {
                    if (topMovies.get(i).getNumLikes() < topMovies.get(i + 1).getNumLikes()) {
                        Collections.swap(topMovies, i, i + 1);
                    }
                }

                // ordinate genres decreasingly depending on their number of likes
                genres = sortByKey(genres);
                genres = sortByValue(genres);

                // find best movie
                Set<Map.Entry<String, Integer>> genre = genres.entrySet();
                Iterator<Map.Entry<String, Integer>> it = genre.iterator();

                // iterate through genres to find if the best most suitable recommendation
                while (it.hasNext()) {
                    Map.Entry<String, Integer> entry = it.next();
                    for (Movies topMovie : topMovies) {
                        if (topMovie.getGenres().contains(entry.getKey())) {
                            Notifications notification = new Notifications(topMovie.getName(),
                                    Notifications.Message.Recommendation);
                            currentAuth.getCurrentUser().getNotifications().add(notification);

                            // output message for recommendation
                            ObjectNode objectNode = objectMapper.createObjectNode();
                            objectNode.putPOJO("error", null);
                            objectNode.putPOJO("currentMoviesList", null);
                            objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
                            output.addPOJO(objectNode);
                            return;
                        }
                    }
                }

                // output message for no recommendation
                Notifications notification = new Notifications("No recommendation",
                        Notifications.Message.Recommendation);
                currentAuth.getCurrentUser().getNotifications().add(notification);

                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);
                objectNode.putPOJO("currentMoviesList", null);
                objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
                output.addPOJO(objectNode);
            }
        }
    }
}
