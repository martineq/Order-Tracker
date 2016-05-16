package ordertracker.notifications

import ordertracker.Brand
import ordertracker.Discount
import ordertracker.Product

class NewProductDiscount extends Notification {

    private Discount discount

    NewProductDiscount(Discount discount){
        this.discount = discount
    }

    @Override
    NotificationType defineNotificationType() {
        return NotificationType.CATALOG
    }

    def getTitle() {
        return "Nuevo descuento de producto disponible"
    }

    def getBody() {
        def product = Product.findById(discount.product_id)
        def brand = Brand.findById(discount.brand_id)
        def description = product.category + ': ' + product.name +' [ marca: ' + brand.name + ' ] '
        description += this.generateDiscount()
        return description
    }
}
