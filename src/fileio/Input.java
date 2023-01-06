package fileio;

import java.util.ArrayList;

public final class Input {

    public Input() {
    }
    private ArrayList<Users> users;
    private ArrayList<Movies> movies;
    private ArrayList<Actions> actions;

    public ArrayList<Movies> getMovies() {
        return movies;
    }
    public void setMovies(final ArrayList<Movies> movies) {
        this.movies = movies;
    }
    public ArrayList<Actions> getActions() {
        return actions;
    }
    public void setActions(final ArrayList<Actions> actions) {
        this.actions = actions;
    }
    public ArrayList<Users> getUsers() {
        return users;
    }
    public void setUsers(final ArrayList<Users> users) {
        this.users = users;
    }
}
