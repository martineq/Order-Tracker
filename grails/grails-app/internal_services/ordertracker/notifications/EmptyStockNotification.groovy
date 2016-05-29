package ordertracker.notifications

import ordertracker.Brand
import ordertracker.Product

class EmptyStockNotification extends Notification {

    private Product product

    EmptyStockNotification(Product product){
        this.product = product
    }

    @Override
    NotificationType defineNotificationType() {
        return NotificationType.NONE
    }

    @Override
    def getTitle() {
        return "Producto agotado"
    }

    @Override
    def getBody() {
        def brand = Brand.findById(product.brand_id)
        return product.category +': ' + product.name +' [ marca: ' + brand.name + ' ]';
    }
}
