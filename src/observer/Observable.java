package observer;

import fileio.Notifications;

public interface Observable {

    /**
     * Attach
     *
     * To implement attach method, first check if the observer is already attached and throw an
     * error if it is. Otherwise, add the observer to the array using the JavaScript array method,
     * push:
     */
    void attach(Observer o, String property);

    /**
     * Detach
     *
     * We implement detach method by finding the index and removing it from the array using the
     * JavaScript splice method. (not needed in our case)
     * @param o
     * @param property
     */
    void detach(Observer o, String property);

    /**
     * Notify Observers
     *
     * We implement notify method by looping over our list of observers and calling the update
     * method of each one
     * @param notifications
     */
    void notifyObservers(Notifications notifications);
}
