package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.queries.RawQueryFacade

class NotificationsController {

    def update() {
        response << new QueryFacade(new NotificationsRequestService()).solve(request)
    }

    def push() {
        PushService.getInstance().push()
        response << "OK"
    }

    def notification() {
        response << new RawQueryFacade(new CreatePushNotificationService()).solve(request)
    }
}
