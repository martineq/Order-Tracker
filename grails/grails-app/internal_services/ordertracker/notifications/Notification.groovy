package ordertracker.notifications

abstract class Notification {

    abstract NotificationType defineNotificationType()

    def addNotification() {
        try {
            new NotificationService().add(this)
        } catch (NullPointerException e) {}
    }

    def addNotification(long seller_id) {
        try {
            new NotificationService().add(seller_id, this)
        } catch (NullPointerException e) {}
    }

    abstract def getTitle()

    abstract def getBody()

    int getNotificationType() {
        return this.defineNotificationType().getType()
    }

	def generateDiscount() {

		if ( discount.range_upto == -1 && discount.range_from == 1 ) 
			return discount.percentage + '% descuento llevando más de ' + discount.range_from + ' item'

		else if ( discount.range_upto == -1 ) 
			return discount.percentage + '% descuento llevando más de ' + discount.range_from + ' items'

		else 
			return discount.percentage + '% descuento llevando de ' + discount.range_from + ' a ' + discount.range_upto + ' items'
	}
}
