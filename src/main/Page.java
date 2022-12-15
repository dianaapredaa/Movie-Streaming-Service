package main;

import java.util.ArrayList;

// Implementation Of The Factory Pattern
public abstract class Page {
    private ArrayList<String> nextPossiblePage = new ArrayList<>();
    private String pageType = null;

    /**
     *
     * @return
     */
    public ArrayList<String> getNextPossiblePage() {
        return nextPossiblePage;
    }

    /**
     *
     * @return
     */
    public String getPageType() {
        return pageType;
    }

    /**
     *
     * @param pageType
     */
    public void setPageType(final String pageType) {
        this.pageType = pageType;
    }
}

class HomePageAuthenticated extends Page {
    HomePageAuthenticated() {
        this.getNextPossiblePage().add("movies");
        this.getNextPossiblePage().add("logout");
        this.getNextPossiblePage().add("upgrades");
        this.setPageType("HomePageAuthenticated");
    }
}

class HomePageNonAuthenticated extends Page {
    HomePageNonAuthenticated() {
        this.getNextPossiblePage().add("login");
        this.getNextPossiblePage().add("register");
        this.setPageType("HomePageNonAuthenticated");
    }

}

class Register extends Page {
    Register() {
        this.getNextPossiblePage().add("HomePageAuthenticated");
        this.setPageType("register");
    }

}

class Login extends Page {
    Login() {
        this.getNextPossiblePage().add("HomePageAuthenticated");
        this.setPageType("login");
    }

}

class MoviesPage extends Page {
    MoviesPage() {
        this.getNextPossiblePage().add("HomePageAuthenticated");
        this.getNextPossiblePage().add("see details");
        this.getNextPossiblePage().add("logout");
        this.setPageType("movies");
    }

}

class SeeDetails extends Page {
    SeeDetails() {
        this.getNextPossiblePage().add("HomePageAuthenticated");
        this.getNextPossiblePage().add("logout");
        this.getNextPossiblePage().add("upgrades");
        this.getNextPossiblePage().add("movies");
        this.setPageType("see details");

    }

}

class Upgrades extends Page {
    Upgrades() {
        this.getNextPossiblePage().add("HomePageAuthenticated");
        this.getNextPossiblePage().add("movies");
        this.getNextPossiblePage().add("logout");
        this.setPageType("upgrades");
    }

}

class Logout extends Page {
    Logout() {
        this.getNextPossiblePage().add("HomePageNonAuthenticated");
        this.setPageType("logout");
    }
}

