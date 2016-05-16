package ordertracker.notifications

import ordertracker.Distribution
import ordertracker.Push_message
import ordertracker.Seller
import ordertracker.UserType

class NotificationService {

    def add(Notification notification) {

        def push_message = new Push_message(title: notification.getTitle(), description: notification.getBody(), type: notification.getNotificationType())
        push_message.save()

        Seller.findAll().each {
            Seller seller ->
                if ( UserType.findByType_idAndType(seller.id, Seller.getTypeName()) != null )
                    new Distribution(message_id: push_message.id, seller: seller.id).save()
        }
    }

    def add(long seller_id, Notification notification) {
        def push_message = new Push_message(title: notification.getTitle(), description: notification.getBody(), type: notification.getNotificationType())
        def seller = Seller.findById(seller_id)

        if ( seller != null ) {
            push_message.save()
            new Distribution(message_id: push_message.id, seller: seller.id).save()
        }
    }

    // return all distribution messages for that seller
    def searchMessages(long seller_id) {
        return Distribution.findAllBySeller(seller_id)
    }

}
