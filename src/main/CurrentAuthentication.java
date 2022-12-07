package main;

import fileio.Movies;
import fileio.Users;

import java.util.LinkedList;

public final class CurrentAuthentication {
    public CurrentAuthentication() {
    }
    private LinkedList<Movies> currentMoviesList = new LinkedList<>();
    private Users currentUser;
    private PageType page = new PageType();
    private Page currentPage = page.type("HomePageNonAuthenticated");

    public LinkedList<Movies> getCurrentMoviesList() {
        return currentMoviesList;
    }
    public void setCurrentMoviesList(final LinkedList<Movies> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }
    public Users getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(final Users currentUser) {
        this.currentUser = currentUser;
    }
    public PageType getPage() {
        return page;
    }
    public void setPage(final PageType page) {
        this.page = page;
    }
    public Page getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }
}
