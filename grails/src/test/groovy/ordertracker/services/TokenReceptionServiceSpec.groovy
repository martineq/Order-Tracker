package ordertracker.services

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.*
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.queries.Requester
import spock.lang.Specification

@Mock([User, UserType, UserNotification])
@TestFor(ordertracker.TokenReceptionService)
class TokenReceptionServiceSpec extends Specification {

    def setup() {
        new UserLoader().load()
        new UserTypeLoader().load()
    }

    private Requester generateMartinRequester() {
        def requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, HttpProtocol.POST)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        requester.addProperty(Keywords.GCM_TOKEN, 'bla')
        return requester
    }

    void "test newUserToken"() {
        given:
            def user = User.findByUsername('martin')

        and:
            def requester = this.generateMartinRequester()

        and:
            def service = new TokenReceptionService()

        when:
            service.validate(requester)
            service.generateQuery()

        then:
            UserNotification.count == 1
            UserNotification.findByUser_id(user.id) != null
    }

    void "test existingUserToken"() {
        given:
            new UserNotification(user_id: 1, token_gcm: 'invalid_token').save()
            def user = User.findByUsername('martin')

        and:
            def requester = this.generateMartinRequester()

        and:
            def service = new TokenReceptionService()

        when:
            service.validate(requester)
            service.generateQuery()

        then:
            UserNotification.count == 1
            UserNotification.findByUser_id(user.id).token_gcm == 'bla'
    }

}
