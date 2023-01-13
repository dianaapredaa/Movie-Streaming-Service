package pages;

public final class PageType {
    /**
     * Determine page type
     *
     * Depending on given page name we return the new page type class instance
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

        // retrieve Movies type
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

        // retrieve HomePageAuthenticated type
        if (pageType.equals("HomePageAuthenticated")) {
            return new HomePageAuthenticated();
        }

        // retrieve HomePageNonAuthenticated type
        if (pageType.equals("HomePageNonAuthenticated")) {
            return new HomePageNonAuthenticated();
        }

        // retrieve Register type
        if (pageType.equals("register")) {
            return new Register();
        }

        // retrieve Login type
        if (pageType.equals("login")) {
            return new Login();
        }
        return null;
    }

}
