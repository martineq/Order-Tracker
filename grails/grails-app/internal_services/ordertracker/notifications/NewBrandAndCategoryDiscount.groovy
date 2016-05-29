package ordertracker.notifications

import ordertracker.Brand
import ordertracker.Discount
import ordertracker.Product

class NewBrandAndCategoryDiscount extends Notification {

    private Discount discount

    NewBrandAndCategoryDiscount(Discount discount){
        this.discount = discount
    }

    @Override
    NotificationType defineNotificationType() {
        return NotificationType.CATALOG
    }

    def getTitle() {
        return "Nuevo descuento por categoría y marca disponible"
    }

    def getBody() {
        def brand = Brand.findById(discount.brand_id)
        def description = discount.category + '- [marca: ' + brand.name + ' ] '
        description += this.generateDiscount()
        return description
    }
}
