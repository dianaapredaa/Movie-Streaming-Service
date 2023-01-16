package observer;

import fileio.Notifications;

public interface Observer {

    /**
     * Update
     *
     * Updates subscribers about new modifications
     * @param notifications
     */
    void update(Notifications notifications);
}
