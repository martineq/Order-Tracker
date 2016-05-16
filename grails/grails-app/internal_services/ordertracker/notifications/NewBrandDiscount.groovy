package ordertracker.notifications

import ordertracker.Brand
import ordertracker.Discount
import ordertracker.Product

class NewBrandDiscount extends Notification {

    private Discount discount

    NewBrandDiscount(Discount discount){
        this.discount = discount
    }

    @Override
    NotificationType defineNotificationType() {
        return NotificationType.CATALOG
    }

    def getTitle() {
        return "Nuevo descuento de marca disponible"
    }

    def getBody() {
        def brand = Brand.findById(discount.brand_id)
        def description = brand.name + ' '
        description += this.generateDiscount()
        return description
    }
}
