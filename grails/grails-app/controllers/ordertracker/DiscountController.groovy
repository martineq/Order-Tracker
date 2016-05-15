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
    
    def upbrand() {
    }
    
    def upproduct() {
    
            def products = Product.list(sort:"name", order:"des")
            def brands = []
            
            products.each { prod ->
                def discc=prod;
                def brand=Brand.get(discc.brand_id)
                brands.add(brand)
            };
        
            [products:products,brands:brands]
    }
    
    def selectproduct() {
        String c=params.range
        
        int numrange=c.toInteger()
        [numrange:numrange]
    
    }
    
     def upentryproduct() {
     
        boolean invalidRanges= valuesValidRanges(params.range.toInteger(), 1, 1)
        
        [invalidRanges:invalidRanges]

     }
     
     boolean valuesValidRanges(int numrange , int ran1, int ran2) {
     
     boolean error=false;

     if(ran1<0 | ran2<0){
        error=true;
        return error;
    }
     if(numrange==3) {
        if (ran1<=ran2){
            error=true;
            return error;
        }
     }
     return error;
     }
}
