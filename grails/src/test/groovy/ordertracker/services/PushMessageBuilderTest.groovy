package ordertracker.services

import grails.test.mixin.Mock
import ordertracker.*
import ordertracker.notifications.PushMessageBuilder
import spock.lang.Specification

@Mock([User, UserType, UserNotification, Distribution, Push_message])
class PushMessageBuilderTest extends Specification {

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
            def pushMessageBuilder = new PushMessageBuilder("")
        when:
            def distribution = Distribution.findById(1)
            def json = pushMessageBuilder.buildJson(distribution)

        then:
            json == '{"to":"gcm_token","notification":{"title":"title","body":"description"},"data":{"type":1}}'
    }


}
