package ordertracker.notifications

/**
 * Created by martin on 15/05/16.
 */
enum NotificationType {

    NONE(0),
    AGENDA(1),
    CATALOG(2),

    private final int value

    private NotificationType(int value) {
        this.value = value
    }

    int getType() {
        return this.value
    }
}