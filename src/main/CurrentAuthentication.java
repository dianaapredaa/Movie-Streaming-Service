package main;

import fileio.Movies;
import fileio.Users;

import java.security.PublicKey;
import java.util.LinkedList;

public class CurrentAuthentication {
    public CurrentAuthentication() {
    }
    LinkedList<Movies> currentMoviesList = new LinkedList<>();
    Users currentUser;
    PageType page = new PageType();
    Page currentPage = page.type("homePageNonAuthenticated");
}
