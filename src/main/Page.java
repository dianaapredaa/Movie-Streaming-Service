package main;

import java.util.ArrayList;

public abstract class Page {
    private ArrayList<String> nextPossiblePage = new ArrayList<>();
    private String pageType = null;

    public ArrayList<String> getNextPossiblePage() {
        return nextPossiblePage;
    }

    public void setNextPossiblePage(ArrayList<String> nextPossiblePage) {
        this.nextPossiblePage = nextPossiblePage;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }
}

class HomePageAuthenticated extends Page {
    public HomePageAuthenticated() {
        this.getNextPossiblePage().add("movies");
        this.getNextPossiblePage().add("logout");
        this.getNextPossiblePage().add("upgrades");
        this.setPageType("HomePageAuthenticated");
    }
}

class HomePageNonAuthenticated extends Page {
    public HomePageNonAuthenticated() {
        this.getNextPossiblePage().add("login");
        this.getNextPossiblePage().add("register");
        this.setPageType("HomePageNonAuthenticated");
    }

}

class Register extends Page {
    public Register() {
        this.getNextPossiblePage().add("HomePageAuthenticated");
        this.setPageType("register");
    }

}

class Login extends Page {
    public Login() {
        this.getNextPossiblePage().add("HomePageAuthenticated");
        this.setPageType("login");
    }

}

class MoviesPage extends Page {
    public MoviesPage() {
        this.getNextPossiblePage().add("HomePageAuthenticated");
        this.getNextPossiblePage().add("see details");
        this.getNextPossiblePage().add("logout");
        this.setPageType("movies");
    }

}

class SeeDetails extends Page {
    public SeeDetails() {
        this.getNextPossiblePage().add("HomePageAuthenticated");
        this.getNextPossiblePage().add("logout");
        this.getNextPossiblePage().add("upgrades");
        this.getNextPossiblePage().add("movies");
        this.setPageType("see details");

    }

}

class Upgrades extends Page {
    public Upgrades() {
        this.getNextPossiblePage().add("HomePageAuthenticated");
        this.getNextPossiblePage().add("movies");
        this.getNextPossiblePage().add("logout");
        this.setPageType("upgrades");
    }

}

class Logout extends Page {
    public Logout() {
        this.getNextPossiblePage().add("HomePageNonAuthenticated");
        this.setPageType("logout");
    }
}

