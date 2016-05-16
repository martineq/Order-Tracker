package ordertracker.notifications

class NewNotification extends Notification {

    private NotificationType notificationType
    private String title
    private String body

    NewNotification(String title, String body, NotificationType notificationType){
        this.notificationType = notificationType
        this.title = title
        this.body = body
    }

    @Override
    NotificationType defineNotificationType() {
        return this.notificationType
    }

    def getTitle() {
        return this.title
    }

    def getBody() {
        return this.body
    }
}
