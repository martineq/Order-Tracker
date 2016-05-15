package ordertracker.notifications

abstract class Notification {

    def addNotification() {
        try {
            new NotificationService().add(this.defineTitle(), this.defineBody())
        } catch (NullPointerException e) {}
    }

    def addNotification(long seller_id) {
        try {
            new NotificationService().add(seller_id, this.defineTitle(), this.defineBody())
        } catch (NullPointerException e) {}
    }

    abstract def defineTitle()

    abstract def defineBody()

}