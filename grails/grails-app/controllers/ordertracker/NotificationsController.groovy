package ordertracker

import ordertracker.queries.QueryFacade

class NotificationsController {

    def update() {
        response << new QueryFacade(new NotificationsRequestService()).solve(request)
    }
}
