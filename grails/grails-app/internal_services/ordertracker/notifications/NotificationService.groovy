package ordertracker.notifications

import ordertracker.Distribution
import ordertracker.Push_message
import ordertracker.Seller
import ordertracker.UserType

class NotificationService {

    def add(String title, String description) {

        def push_message = new Push_message(title: title, description: description)
        push_message.save()

        Seller.findAll().each {
            Seller seller ->
                if ( UserType.findByType_idAndType(seller.id, Seller.getTypeName()) != null )
                    new Distribution(message_id: push_message.id, seller: seller.id).save()
        }
    }

    // return all distribution messages for that seller
    def searchMessages(long seller_id) {
        return Distribution.findAllBySeller(seller_id)
    }

}
