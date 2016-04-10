package ordertracker

import ordertracker.queries.QueryFacade

class SellerController {

    def index() {
        def sellers = Seller.list()
        [sellers:sellers]
    }
    
    	def hi() {
		render (view:'index.gsp')
	}
}
