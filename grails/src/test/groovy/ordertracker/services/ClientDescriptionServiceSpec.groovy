package ordertracker.services

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.Client
import ordertracker.ClientDescriptionService
import ordertracker.constants.ClientStates
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.queries.QueryException
import ordertracker.queries.Requester
import ordertracker.tranmission.DefaultTransmission
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ClientDescriptionService)
@Mock(Client)
class ClientDescriptionServiceSpec extends Specification {

    void "test invalidRequestMethod"() {
        given:
            def requester = new Requester()
            requester.addProperty(HttpProtocol.METHOD, HttpProtocol.PUT)

        and:
            def clientDescriptionService = new ClientDescriptionService()

        when:
            def message = ""
            try{
                clientDescriptionService.validate(requester)
            }
            catch( QueryException qe ) {
                message = qe.getMessage()
            }

        then:
            message == 'Invalid HTTP request method: must be GET'
    }

    void "test invalidRequestHeader"() {
        given:
        def requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, HttpProtocol.GET)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')

        and:
            def clientDescriptionService = new ClientDescriptionService()

        when:
            def message = ""
            try{
                clientDescriptionService.validate(requester)
                clientDescriptionService.generateQuery()
            }
            catch( QueryException qe ) {
                message = qe.getMessage()
            }

        then:
        message == 'Invalid request'
    }

    void "test clientDoNotExist"() {
        given:
        def requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, HttpProtocol.GET)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        requester.addProperty(Keywords.CLIENT_ID, 1500)

        and:
            def clientDescriptionService = new ClientDescriptionService()

        when:
            def message = ""
            try{
                clientDescriptionService.validate(requester)
                clientDescriptionService.generateQuery()
            }
            catch( QueryException qe ) {
                message = qe.getMessage()
            }

        then:
            message == 'Client not found in database'
    }

    void "test clientExist"() {
        given:
            def requester = new Requester()
            requester.addProperty(HttpProtocol.METHOD, HttpProtocol.GET)
            requester.addProperty(Keywords.USERNAME, 'martin')
            requester.addProperty(Keywords.TOKEN, 'token1')
            requester.addProperty(Keywords.CLIENT_ID, 1)

        and:
            new Client(name: 'Juan',    address: 'Av. Pres. Figueroa Alcorta 3535', city: 'Ciudad de Buenos Aires', state: ClientStates.NO_VISITADO.toString(),qrcode: 'A', email: 'a@a.com').save()

        and:
            def clientDescriptionService = new ClientDescriptionService()

        when:
            clientDescriptionService.validate(requester)
            clientDescriptionService.generateQuery()

        then:
            def json = '{"status":{"result":"ok","description":"Client found"},"data":{"id":1,"name":"Juan","address":"Av. Pres. Figueroa Alcorta 3535","city":"Ciudad de Buenos Aires","state":"NO_VISITADO"}}'
            clientDescriptionService.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build() == json
    }
}
