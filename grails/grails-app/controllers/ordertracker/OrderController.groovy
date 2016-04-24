package ordertracker

import ordertracker.queries.QueryFacade

class OrderController {

    def orderRequestService

    def help() { }

    def qr() {
        response << new QueryFacade(new QRService()).solve(request)
    }

    def request() {
        response << new QueryFacade(orderRequestService).solve(request)
    }

    def historical() {
        response << new QueryFacade(new HistoricalOrdersService()).solve(request)
    }
}
