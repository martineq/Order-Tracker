package ordertracker.services

import grails.test.mixin.Mock
import ordertracker.Distribution
import ordertracker.NotificationsRequestService
import ordertracker.Push_message
import ordertracker.Seller
import ordertracker.SellerLoader
import ordertracker.User
import ordertracker.UserLoader
import ordertracker.UserType
import ordertracker.UserTypeLoader
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.notifications.NotificationService
import ordertracker.queries.Requester
import ordertracker.tranmission.DefaultTransmission
import spock.lang.Specification

@Mock( [User, UserType, Seller, Distribution, Push_message])
class NotificationsRequestServiceSpec extends Specification {

    def setup() {
        new SellerLoader().load()
        new UserLoader().load()
        new UserTypeLoader().load()
    }

    private Requester generateMartinRequester() {
        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, HttpProtocol.GET)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        return requester
    }

    def "test requestMessage"() {
        given:
        def title = "title"
        def message = "message"
        new NotificationService().add(title, message)

        and:
        def notificationService = new NotificationsRequestService()

        when:
        notificationService.validate(this.generateMartinRequester())

        then:
        notificationService.generateQuery() == true

        def response = notificationService.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build()
        response == "{\"status\":{\"result\":\"ok\",\"description\":\"Permitted access\"},\"data\":[{\"id\":1,\"title\":\"title\",\"body\":\"message\"}]}"
    }

    def "test emptyNotifications"() {
        given:
            def notificationService = new NotificationsRequestService()

        when:
            notificationService.validate(this.generateMartinRequester())

        then:
            def response = notificationService.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build()
            response == "{\"status\":{\"result\":\"ok\",\"description\":\"No notifications available\"},\"data\":[{}]}"
    }
}
