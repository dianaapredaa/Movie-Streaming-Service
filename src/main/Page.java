package main;

import java.util.ArrayList;

abstract class Page {
    ArrayList<String> nextPossiblePage = new ArrayList<>();
}

class HomePageAuthenticated extends Page {
    public HomePageAuthenticated() {
        this.nextPossiblePage.add("Movies");
        this.nextPossiblePage.add("SeeDetails");
        this.nextPossiblePage.add("Logout");
    }
}

class HomePageNonAuthenticated extends Page {
    public HomePageNonAuthenticated() {
        this.nextPossiblePage.add("Login");
        this.nextPossiblePage.add("Register");
    }

}

class Register extends Page {
    public Register() {
        this.nextPossiblePage.add("HomePageAuthenticated");
    }

}

class Login extends Page {
    public Login() {
        this.nextPossiblePage.add("HomePageAuthenticated");
    }

}

class MoviesPage extends Page {
    public MoviesPage() {
        this.nextPossiblePage.add("HomePageAuthenticated");
        this.nextPossiblePage.add("SeeDetails");
        this.nextPossiblePage.add("Logout");
    }

}

class SeeDetails extends Page {
    public SeeDetails() {
        this.nextPossiblePage.add("HomePageAuthenticated");
        this.nextPossiblePage.add("SeeDetails");
        this.nextPossiblePage.add("Logout");
        this.nextPossiblePage.add("Upgrades");
    }

}

class Upgrades extends Page {
    public Upgrades() {
        this.nextPossiblePage.add("HomePageAuthenticated");
    }

}

class Logout extends Page {
    public Logout() {
        this.nextPossiblePage.add("HomePageNonAuthenticated");
    }

}

class PageType {
    public Page type(String pageType) {
        if (pageType == null) {
            return null;
        }
        // retrieve SeeDetails type
        if (pageType.equals("SeeDetails")) {
            return new SeeDetails();
        }

        // retrieve main.Movies type
        if (pageType.equals("Movies")) {
            return new MoviesPage();
        }

        // retrieve Upgrades type
        if (pageType.equals("Upgrades")) {
            return new Upgrades();
        }
        // retrieve Logout type
        if (pageType.equals("Logout")) {
            return new Logout();
        }

        if (pageType.equals("HomePageAuthenticated")) {
            return new HomePageAuthenticated();
        }

        if (pageType.equals("HomePageNonAuthenticated")) {
            return new HomePageNonAuthenticated();
        }

        if (pageType.equals("Register")) {
            return new Register();
        }

        if (pageType.equals("Login")) {
            return new Login();
        }
        return null;
    }

}
