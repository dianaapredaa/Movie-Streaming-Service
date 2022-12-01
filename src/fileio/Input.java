package fileio;

import java.util.ArrayList;

public class Input {

    public Input() {
    }

    private ArrayList<Users> users;
    private ArrayList<Movies> movies;
    private ArrayList<Actions> actions;

    public ArrayList<Users> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Users> users) {
        this.users = users;
    }

    public ArrayList<Movies> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movies> movies) {
        this.movies = movies;
    }

    public ArrayList<Actions> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Actions> actions) {
        this.actions = actions;
    }
}
