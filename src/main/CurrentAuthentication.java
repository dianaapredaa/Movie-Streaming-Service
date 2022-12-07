package main;

import fileio.Movies;
import fileio.Users;

import java.util.LinkedList;

public class CurrentAuthentication {
    public CurrentAuthentication() {
    }
    private LinkedList<Movies> currentMoviesList = new LinkedList<>();
    private Users currentUser;
    private PageType page = new PageType();
    private Page currentPage = page.type("HomePageNonAuthenticated");

    public LinkedList<Movies> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(LinkedList<Movies> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    public PageType getPage() {
        return page;
    }

    public void setPage(PageType page) {
        this.page = page;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }
}
