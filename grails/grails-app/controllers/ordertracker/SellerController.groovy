package ordertracker

import ordertracker.queries.QueryFacade

class SellerController {

    def index() {
        def sellers = Seller.list(sort:"document_number", order:"des")
        [sellers:sellers]
    }

    def weeklySchedule() {
        response << new QueryFacade(new WeeklyScheduleService()).solve(request)
    }

}
