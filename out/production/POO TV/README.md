*Copyright 2022-2023 Preda Diana 324CA*

# PooTv

**Student:** *Preda Diana*

**Group:** *324CA*

## Description

This project represents the implementation of a simpler version of Netflix website back-end.
Page Structure:

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

In order to implement website's pages we used Factory Design Pattern. We created a Page abstract class and concrete
classes implementing the Page abstract class. 
A factory class PageType is defined as a next step. We will use PageType class to get 
a Page object. It will pass information to PageType to get the type of object it needs.

## Commands

HOMEPAGE NON-AUTHENTICATED - everybody stars from here, use have only two options,
go to register or go to login

REGISTER (register action) - new user with given credentials is added to the current users list if
the user doesn't already exist

LOGIN (login action) - using given credential log into your account if you already registered

HOMEPAGE AUTHENTICATED - after login/register we go to this page

MOVIE (search/filter action) - show all available movies in your country, you can also search on this list or filter this
list

SEE DETAILS (purchase/watch/rate/like action) - shows details of a specified movie, you can also purchase, watch, like or rate a movie

UPGRADES (buy premium account/ buy tokens action) - buy tokens or buy premium account

LOGOUT - logout of your account

## Implementation

To use the readValue() method in ObjectMapper, we made the Input class. We created a PageType
object to get different types of pages, we always start from page HomepageNonAuthenticated.

We made a copy of the given input to work on it. We also created a currentAuthentication object
to work with and initialize it.

We iterate through command list. There are two type of actions, ON PAGE and CHANGE PAGE.
If actions type is on page, we jump to features, otherwise we have to see if page can be changed.
If page can be changed, we make necessary adjustments and move on to features if there are any, otherwise error message
is displayed.

We created CurrentAuthentication class to store current user's credentials, movie list and the page he is on.
We make sure to always update current user's info after every command.

## Possible improvements:

- better code structure
- shorter methods
- more complex design patterns

## Final comments:


