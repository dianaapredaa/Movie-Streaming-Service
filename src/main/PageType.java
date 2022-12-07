package main;

public final class PageType {
    /**
     *
     * @param pageType
     * @return
     */
    public Page type(final String pageType) {
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
