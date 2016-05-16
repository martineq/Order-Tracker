package ordertracker.services

import grails.test.mixin.Mock
import ordertracker.*
import ordertracker.notifications.NewNotification
import ordertracker.notifications.NotificationService
import ordertracker.notifications.NotificationType
import spock.lang.Specification

@Mock( [UserType, Seller, Distribution, Push_message])
class NotificationServiceSpec extends Specification {

    def setup() {
        new UserTypeLoader().load()
        new SellerLoader().load()
    }

    def "test requestMessage"() {
        given:
           String title = "header"
           String message = "hello world"
            def notification = new NewNotification(title, message, NotificationType.NONE)

        and:
            NotificationService notificationService = new NotificationService();

        when:
            notificationService.add(notification)

        then:
            Push_message.findById(1).title == title
            Push_message.findById(1).description == message
            Push_message.findById(1).type == NotificationType.NONE.getType()
    }

    def "test SellersAddedInDistributionService"() {
        given:
            String title = "header"
            String message = "hello world"
            def notification = new NewNotification(title, message, NotificationType.NONE)

        and:
            NotificationService notificationService = new NotificationService();

        when:
            notificationService.add(notification)

        then:
            Distribution.count == UserType.countByType(Seller.getTypeName())
    }
}
