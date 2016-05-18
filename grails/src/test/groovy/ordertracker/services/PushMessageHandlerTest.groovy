package ordertracker.services

import grails.test.mixin.Mock
import ordertracker.*
import ordertracker.notifications.PushMessageHandler
import spock.lang.Specification

@Mock([User, UserType, UserNotification, Distribution, Push_message])
class PushMessageHandlerTest extends Specification {

    def setup() {
        new UserLoader().load()
        new UserTypeLoader().load()
    }

    private def generatePushMessage(long seller_id) {

        def push_message = new Push_message(title: "title", description: "description", type: 1)
        push_message.save()

        new Distribution(message_id: push_message.id, seller: seller_id).save()
    }

    void "test jsonBuilder"() {
        given:
            this.generatePushMessage(1)

        and:
            def pushMessageBuilder = new PushMessageHandler("")
        when:
            def distribution = Distribution.findById(1)
            def json = pushMessageBuilder.buildJson(distribution)

        then:
            json == '{"to":"gcm_token","notification":{"title":"title","body":"description"},"data":{"type":1}}'
    }

    void "test gcm_connection"() {
        given:
            def gcm = new PushMessageHandler("AIzaSyBdpUd3L643VWf12vzRg2uGbb_2-scKOCQ")
        when:
        try {
            def resp = gcm.sendMessage('{ "data": {"score": "5x1","time": "15:10" },"to" : "bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1..." }')
        } catch (RuntimeException e ) {

        }
        then:
            1 == ""
    }

    void "test gcm"() {
        given:
            def gcm = new PushMessageHandler("AIzaSyBdpUd3L643VWf12vzRg2uGbb_2-scKOCQ")

        when:
            def json = '{ "data": {"score": "5x1","time": "15:10" },"to" : "bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1..." }'
            def resp = gcm.sendMessage(json)
            def body = resp.getBody()
            gcm.analize(body)

        then:
            1 == ""
    }

}
