package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Movies;
import fileio.Notifications;
import fileio.Users;

import java.util.ArrayList;
import java.util.HashMap;

public class Recommendation {
    ObjectMapper objectMapper = new ObjectMapper();
    public void recommendation(final ArrayNode output) {
        HashMap<String, Integer> genres = new HashMap<>();

        CurrentAuthentication currentAuth = CurrentAuthentication.getInstance();
        if (currentAuth.getCurrentUser() != null) {
            // check for premium users
            if (currentAuth.getCurrentUser().getCredentials().getAccountType().equals("premium")) {

                for (int i = 0; i < currentAuth.getCurrentUser().getLikedMovies().size(); i++) {
                    Movies movie = currentAuth.getCurrentUser().getLikedMovies().get(i);
                    for (int j = 0; j < movie.getGenres().size(); j++) {
                        String genre = movie.getGenres().get(j);
                        if (genres.containsKey(genre)) {
                            genres.put(genre, genres.get(genre) + 1);
                        } else {
                            genres.put(genre, 1);
                        }
                    }
                }

                Notifications notification = new Notifications("No recommendation",
                        Notifications.Message.Recommendation);
                currentAuth.getCurrentUser().getNotifications().add(notification);
                // output message
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.putPOJO("error", null);
                objectNode.putPOJO("currentMoviesList", null);
                objectNode.putPOJO("currentUser", new Users(currentAuth.getCurrentUser()));
                output.addPOJO(objectNode);
            }
        }
    }
}
