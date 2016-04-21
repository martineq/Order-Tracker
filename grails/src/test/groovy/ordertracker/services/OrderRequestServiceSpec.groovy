package ordertracker.services

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.OrderRequestService
import ordertracker.User
import ordertracker.UserLoader
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.queries.QueryException
import ordertracker.queries.Requester

@Mock(User)
@TestFor(OrderRequestService)
class OrderRequestServiceSpec {

    def setup() {
        new UserLoader().load()
    }

    private Requester generateMartinRequester(Enum httpMethod) {
        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, httpMethod)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        return requester
    }

    void "test emptyBodyRequest"() {
        given:
            Requester requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, "")

        when:
            String exceptionMessage = "";
            try {
                new OrderRequestService().validate(requester)
            }
            catch( QueryException e ) {
                exceptionMessage = e.getMessage();
            }

        then:
            exceptionMessage == "No se recibió un objeto json válido."
    }

    void "test noBody"() {
        given:
            def requester = this.generateMartinRequester(HttpProtocol.POST)

        when:
            String message = "";
            try {
                new OrderRequestService().validate(requester)
            }
            catch( QueryException e ) {
                message = e.getMessage();
            }

        then:
             message == "No se recibió un objeto json válido."
    }

}
