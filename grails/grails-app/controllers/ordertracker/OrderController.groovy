package ordertracker

import ordertracker.queries.QueryFacade

class OrderController {

    def help() { }

    def qr() {
        response << new QueryFacade(new QRService()).solve(request)
    }
}
