package ordertracker.services

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.User
import ordertracker.UserLoader
import ordertracker.ValidationService
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.queries.Keywords
import ordertracker.queries.QueryException
import ordertracker.queries.Requester
import ordertracker.tranmission.DefaultTransmission
import spock.lang.Specification

@TestFor(ValidationService)
@Mock(User)
class ValidationServiceSpec extends Specification {

    def setup() {
        new UserLoader().load()
    }

    private Requester generateMartinRequester() {
        Requester requester = new Requester()
        requester.addProperty(Keywords.METHOD, Keywords.GET)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        return requester
    }

    void "test validRequest"() {
        given:
            def requester = this.generateMartinRequester()

        and:
            def validationService = new ValidationService()

        when:
            def validationResult = validationService.validate(requester)

        then:
            validationResult == true
    }

    void "test invalidRequest"() {
        given:
        Requester requester = new Requester()
        requester.addProperty(Keywords.METHOD, Keywords.GET)
        requester.addProperty(Keywords.USERNAME, 'martin')

        and:
        def validationService = new ValidationService();

        try {
            when:
                validationService.validate(requester)
        }

        catch (QueryException qe ) {
            then:
                qe.getMessage() == 'Invalid request - user rejected'
        }
    }

    void "test invalidUsername"() {
        given:
            Requester requester = new Requester()
            requester.addProperty(Keywords.METHOD, Keywords.GET)
            requester.addProperty(Keywords.USERNAME, 'felipe')
            requester.addProperty(Keywords.TOKEN, 'tokenM')

        and:
            def validationService = new ValidationService();

        when:
            validationService.validate(requester)
            def validationResult = validationService.generateQuery()

        then:
            validationResult == false
    }

    void "test invalidUsernameToken"() {
        given:
            Requester requester = new Requester()
            requester.addProperty(Keywords.METHOD, Keywords.GET)
            requester.addProperty(Keywords.USERNAME, 'martin')
            requester.addProperty(Keywords.TOKEN, 'tokenM')

        and:
            def validationService = new ValidationService();

        when:
            validationService.validate(requester)
            def validationResult = validationService.generateQuery()

        then:
            validationResult == false
    }

    void "test validUsernameToken"() {
        given:
            def requester = this.generateMartinRequester()

        and:
            def validationService = new ValidationService();

        when:
            validationService.validate(requester)
            def validationResult = validationService.generateQuery()

        then:
            validationResult == true
    }

    void "test obtainValidJson"() {
        given:
            def requester = this.generateMartinRequester()

        and:
            def validationService = new ValidationService();

        when:
            validationService.validate(requester)
            validationService.generateQuery()
            ProtocolJsonBuilder Builder = validationService.obtainResponse(DefaultTransmission.obtainDefaultTransmission())

        then:
            Builder.build() == '{"status":{"result":"ok","description":"Permitted access"}}'
    }

}
