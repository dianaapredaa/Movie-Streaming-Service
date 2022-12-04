package main;

import fileio.Actions;

public class Commands {

    public void features(Actions command){
        switch (command.getFeature()) {
            case ("login"):
                // only on Login page
                String username = command.getCredentials().getName();
                String password = command.getCredentials().getPassword();


                // credentials
                break;
            case ("register"):
                // only on Register page

                // credentials
                break;
            case ("search"):
                // only on Movies Page

                // startsWith
                break;
            case ("filters"):
                // only on Movies Page

                // sort
                    // rating
                    // duration
                // filters
                    // contains
                        // genre
                        // actors
                break;

            case ("purchase"):
                // only on SeeDetails

                // objectType
                    // movie
                    // rate
                break;
            case ("watch"):
                // check if you purchased the movie

                break;
            case ("rate"):
                // rate
                break;
            case ("buy tokens"):
                // count
                break;
            case ("buy premium account"):
                break;
            case ("like"):

                // check if you watched the movie
                break;
        }

    }
}
