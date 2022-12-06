package main;

import java.util.ArrayList;

abstract class Page {
    ArrayList<String> nextPossiblePage = new ArrayList<>();
    String pageType = null;
}

class HomePageAuthenticated extends Page {
    public HomePageAuthenticated() {
        this.nextPossiblePage.add("movies");
        this.nextPossiblePage.add("see details");
        this.nextPossiblePage.add("logout");
        this.nextPossiblePage.add("upgrades");
        this.pageType = "HomePageAuthenticated";
    }
}

class HomePageNonAuthenticated extends Page {
    public HomePageNonAuthenticated() {
        this.nextPossiblePage.add("login");
        this.nextPossiblePage.add("register");
        this.pageType = "HomePageNonAuthenticated";
    }

}

class Register extends Page {
    public Register() {
        this.nextPossiblePage.add("HomePageAuthenticated");
        this.pageType = "register";
    }

}

class Login extends Page {
    public Login() {
        this.nextPossiblePage.add("HomePageAuthenticated");
        this.pageType = "login";
    }

}

class MoviesPage extends Page {
    public MoviesPage() {
        this.nextPossiblePage.add("HomePageAuthenticated");
        this.nextPossiblePage.add("seeDetails");
        this.nextPossiblePage.add("logout");
        this.pageType = "movies";
    }

}

class SeeDetails extends Page {
    public SeeDetails() {
        this.nextPossiblePage.add("HomePageAuthenticated");
        this.nextPossiblePage.add("seeDetails");
        this.nextPossiblePage.add("logout");
        this.nextPossiblePage.add("upgrades");
        this.pageType = "see details";

    }

}

class Upgrades extends Page {
    public Upgrades() {
        this.nextPossiblePage.add("HomePageAuthenticated");
        this.pageType = "upgrades";
    }

}

class Logout extends Page {
    public Logout() {
        this.nextPossiblePage.add("HomePageNonAuthenticated");
        this.pageType = "logout";
    }

}

class PageType {
    public Page type(String pageType) {
        if (pageType == null) {
            return null;
        }
        // retrieve SeeDetails type
        if (pageType.equals("see details")) {
            return new SeeDetails();
        }

        // retrieve main.Movies type
        if (pageType.equals("movies")) {
            return new MoviesPage();
        }

        // retrieve Upgrades type
        if (pageType.equals("upgrades")) {
            return new Upgrades();
        }
        // retrieve Logout type
        if (pageType.equals("logout")) {
            return new Logout();
        }

        if (pageType.equals("HomePageAuthenticated")) {
            return new HomePageAuthenticated();
        }

        if (pageType.equals("HomePageNonAuthenticated")) {
            return new HomePageNonAuthenticated();
        }

        if (pageType.equals("register")) {
            return new Register();
        }

        if (pageType.equals("login")) {
            return new Login();
        }
        return null;
    }

}
