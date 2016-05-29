package ordertracker.database

import ordertracker.Product
import ordertracker.internalServices.dtos.OrderRequestDTO
import ordertracker.internalServices.dtos.ProductRequestDTO
import ordertracker.util.logger.Log

/**
 * Created by martin on 15/05/16.
 */
class OrderBuilder {

    class ReservedItem {
        public int id
        public int quantity

        ReservedItem(int id, int quantity) {
            this.id = id;
            this.quantity = quantity;
        }
    }

    private List<ReservedItem> reservedItemList

    OrderBuilder(){
        this.reservedItemList = new ArrayList<>()
    }

    public def reserveItems(OrderRequestDTO request) {
        request.getProductRequests().each { productRequest -> this.reserveProductItem(productRequest) }

        if ( this.reservedItemList.size() == 0 )
            throw new DatabaseException("Empty items purchase")
    }

    private def reserveProductItem(ProductRequestDTO productRequest) {
        try {
            Product product = Product.findById(productRequest.getProduct_id())
            long originalStock = product.stock
            product.stock -= productRequest.quantity
            product.save(flush: true)

            this.reservedItemList.add(new ReservedItem(productRequest.product_id, productRequest.quantity))

            if ( product.validate() == false ) {
                Log.info("Product out of stock: " + product.id + " [ stock: " + product.stock + " ]")

                String message = ""

                if ( originalStock == 1 )
                    message = "producto "+ product.getName() + " solicitado supera el stock [ " + originalStock +" item ]"

                else if ( originalStock > 1)
                    message = "producto "+ product.getName() + " solicitado supera el stock [ " + originalStock +" items ]"

                else message = "producto "+ product.getName() + " solicitado agotado"

                throw new OutOfStockException(message)
            }
        }

        catch( NullPointerException e) {
            throw new ProductException("Product id: "+ productRequest.getProduct_id().toString() + " do not exist in database")
        }
    }

    public def returnProducts() {
        this.reservedItemList.each { reservedItem ->

            try {
                def product = Product.findById(reservedItem.id)
                product.stock += reservedItem.quantity
                product.save(flush: true)
            }

            catch (NullPointerException e) {
                Log.info("Product item couldn't be returned: " + reservedItem.id.toString())
            }
        }
    }


}
