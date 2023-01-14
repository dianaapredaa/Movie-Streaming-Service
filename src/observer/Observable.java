package observer;

import fileio.Notifications;

public interface Observable {
    void attach(Observer o, String property);

    void detach(Observer o, String property);

    void notifyObservers(Notifications notifications);
}
