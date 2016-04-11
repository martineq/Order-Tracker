package ordertracker

import ordertracker.queries.QueryFacade

class SellerController {

    def index() {
        def sellers = Seller.list()
        [sellers:sellers]
    }

}
