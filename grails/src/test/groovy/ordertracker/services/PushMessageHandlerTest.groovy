package ordertracker.services

import grails.test.mixin.Mock
import ordertracker.*
import ordertracker.notifications.PushMessageHandler
import ordertracker.notifications.PushMessageSender
import spock.lang.Specification

@Mock([Seller, User, UserType, UserNotification, Distribution, Push_message])
class PushMessageHandlerTest extends Specification {

    def setup() {
        new SellerLoader().load()
        new UserLoader().load()
        new UserTypeLoader().load()
    }

    private def generatePushMessage(long seller_id) {

        def push_message = new Push_message(title: "title", description: "description", type: 1)
        push_message.save()

        new Distribution(message_id: push_message.id, seller: seller_id).save()

        new UserNotification(user_id: 1, token_gcm: "gcm_token").save()
    }

    void "test jsonBuilder"() {
        given:
            this.generatePushMessage(1)

        and:
            def pushMessageSender = new PushMessageSender(Distribution.findById(1))
        when:
            def distribution = Distribution.findById(1)
            def json = pushMessageSender.buildJson(distribution)

        then:
            json == '{"to":"gcm_token","notification":{"title":"title","body":"description"},"data":{"type":1}}'
    }

    void "test gcm_connection"() {
        given:
            def gcm = new PushMessageHandler("AIzaSyBdpUd3L643VWf12vzRg2uGbb_2-scKOCQ")
        when:
        try {
            def resp = gcm.send()
        } catch (RuntimeException e ) {

        }
        then:
            Distribution.count() == 0
    }
}
