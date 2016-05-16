package ordertracker.notifications

import ordertracker.Brand
import ordertracker.Product

class NewProduct extends Notification {

    private Product product

    NewProduct(Product product){
        this.product = product
    }

    @Override
    NotificationType defineNotificationType() {
        return NotificationType.CATALOG
    }

    def getTitle() {
        return "Nuevo producto disponible"
    }

    def getBody() {
        def brand = Brand.findById(product.brand_id)
        return product.category +': ' + product.name +' ( ' + brand.name + ' ) - $' + product.price + ' ( stock: '+ product.stock +' u )'
    }
}
