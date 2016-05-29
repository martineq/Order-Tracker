package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.queries.RawQueryFacade

class NotificationsController {

    def update() {
        Push_message.findAll().each { Push_message p -> p.properties}
        response << new QueryFacade(new NotificationsRequestService()).solve(request)
    }

    def push() {
        GCMConnectorService.getInstance().push()
        response << "OK"
    }

    def notification() {
        response << new RawQueryFacade(new CreatePushNotificationService()).solve(request)
    }

    def ack(){
        response << new QueryFacade(new ACKPushService()).solve(request)
    }

    def gcm_token() {
        response << new QueryFacade(new TokenReceptionService()).solve(request)
    }
}
