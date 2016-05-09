package ordertracker.services

import grails.test.mixin.Mock
import ordertracker.*
import ordertracker.notifications.NotificationService
import spock.lang.Specification

@Mock( [Seller, Distribution, Push_message])
class NotificationServiceSpec extends Specification {

    def setup() {
        new SellerLoader().load()
    }

    def "test requestMessage"() {
        given:
           String title = "header"
           String message = "hello world"

        and:
            NotificationService notificationService = new NotificationService();

        when:
            notificationService.add(title, message)

        then:
            Push_message.findById(1).title == title
            Push_message.findById(1).description == message
    }

    def "test SellersAddedInDistributionService"() {
        given:
            String title = "header"
            String message = "hello world"

        and:
            NotificationService notificationService = new NotificationService();

        when:
            notificationService.add(title, message)

        then:
            Distribution.count == Seller.count
    }
}
