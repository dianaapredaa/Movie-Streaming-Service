*Copyright 2022-2023 Preda Diana 324CA*

# PooTv

**Student:** *Preda Diana*

**Group:** *324CA*

## Description

This project represents the implementation of a simpler version of Netflix website back-end.
Page Structure will be as it follows:

    Homepage non-authenticated
        Login
            login
        Register
            register
    Homepage authenticated
        Movies
            Homepage Authenticated
            search
            filter
            See details
                Homepage authenticated
                Movies
                Upgrades
                Purchase
                    watch
                        like
                        rate the movie
                Logout
        Logout
    Upgrades
        Hompage authenticated
        Movie
            Buy premium account
            Buy tokens
            Logout
    Logout

## Design Pattern

In order to implement website's pages we used Factory Design Pattern. We created a Page abstract
class and concrete classes implementing the Page abstract class. 
A factory class PageType is defined as a next step. We will use PageType class to get a Page object.
It will pass information to PageType to get the type of object it needs.

For the Login system we use Singleton Pattern, so that at any point there is only one person
authenticated.

For the Subscribe and Notifications system we used Observer Pattern. An observable is an object
which notifies observers about the changes in its state. In our case users can be subscribed to
different movie genres. When adding/deleting movies from database, subscribers of the movie's
genres are notified.

For generating notifications we used Builder Design Pattern. We set required fields and wrapped
up the object.

For the GenreObservable, we also used Singleton Pattern, so that at any point there is only one
instance of subscribed genres.


## Commands and pages

HOMEPAGE NON-AUTHENTICATED - everybody stars from here, users have only two options, to move to
REGISTER page or to LOGIN page

REGISTER (register action) - new user with given credentials is added to the current users list if
the user doesn't already exist

LOGIN (login action) - using given credential log into your account if you already registered

HOMEPAGE AUTHENTICATED - after login/register action we go to this page

MOVIE (search/filter action) - show all available movies in your country, you can also search on
this list or filter this list

SEE DETAILS (purchase/watch/rate/like/subscribe action) - shows details of a specified movie, you
can also purchase, watch, like or rate a movie, but make use you purchased a movie, in order to watch
it and watched a movie in order to rate/like it. We can also subscribe to a movie genres

UPGRADES (buy premium account/ buy tokens action) - buy tokens or upgrade to premium account

LOGOUT - logout of your account

ADMIN COMMANDS - we can also add and delete movies from movie's database

## Implementation

To use the readValue() method in ObjectMapper, we made the Input class. We created a PageType
object to get different types of pages, we always start from page HomepageNonAuthenticated.

We made a copy of the given input to work with, we also created CurrentAuthentication.getInstance()entication class to
store current user's credentials, movie list and the page he is on.

We iterate through command list. There are four type of actions, ON PAGE, CHANGE PAGE, BACK and DATABASE.
If actions type is ON PAGE, we jump to features.
If action type is CHANGE PAGE, we have to see if page can be changed. If page can be changed, we make 
necessary adjustments and move on, otherwise error message is displayed.
If action type is BACK, we go back to the previews page.
If action type is DATABASE, we alter movie's database info by adding or deleting movies.
We make sure to always update current user's info after every command.

In this part, users can also subscribe to different genres, and so they are notified of any changes
regarding theirs subscribed genres.

At the end of the program if the current authenticated user is a premium user, we reward him with a
new movie recommendation based on his preferences.

## Possible improvements:

- better code structure
- shorter methods

## Sources
Hashmap sort by value:
https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/