package main;

interface Page {

}

class Movies implements Page {

}

class SeeDetails implements Page {

}

class Upgrades implements Page {

}

class Logout implements Page {

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
            return new SeeDetails();
        }

        // retrieve Upgrades type
        if (pageType.equals("Upgrades")) {
            return new Upgrades();
        }
        // retrieve Logout type
        if (pageType.equals("Logout")) {
            return new Logout();
        }
        return null;
    }

}
