package observer;

import fileio.Notifications;

public interface Observer {
    void update(Notifications notifications);
}
