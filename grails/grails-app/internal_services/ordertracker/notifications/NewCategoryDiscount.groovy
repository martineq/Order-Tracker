package ordertracker.notifications

import ordertracker.Discount

class NewCategoryDiscount extends Notification {

    private Discount discount

    NewCategoryDiscount(Discount discount){
        this.discount = discount
    }

    @Override
    NotificationType defineNotificationType() {
        return NotificationType.CATALOG
    }

    def getTitle() {
        return "Nuevo descuento de categoría disponible"
    }

    def getBody() {
        def description = discount.category + ' '
        description += this.generateDiscount()
        return description
    }
}
