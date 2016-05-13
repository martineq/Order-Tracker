package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.constants.ClientStates

class DiscountController {

    def index() {
        def discounts = Discount.list()
        def brands = []
        def products = []
        
        discounts.each { disc ->
            def discc=disc;
            def brand=Brand.get(discc.brand_id)
            def product = Product.get(discc.product_id)
            products.add(product)
            brands.add(brand)
        };
        
        [discounts:discounts,products:products,brands:brands]
    }
            
    def deleteconfirm() {
    }
    
    def editdiscount() {
    }
    
    def up() {
    }
}
