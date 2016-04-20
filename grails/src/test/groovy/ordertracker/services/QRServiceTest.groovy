package ordertracker.services

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.QRService
import ordertracker.User
import ordertracker.UserLoader
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.queries.QueryException
import ordertracker.queries.Requester
import spock.lang.Specification

@Mock(User)
@TestFor(ordertracker.QRService)
class QRServiceTest extends Specification {

    def setup() {
        new UserLoader().load()
    }

    private Requester generateMartinRequester() {
        String qr = "iVBORw0KGgoAAAANSUhEUgAAAdAAAAHQAQMAAAArk6lDAAAABlBMVEX///8AAABVwtN+AAABNUlEQVR4nO3UO27DQAwFwL3/pTeFQZNc2UCKRFQxD7CwH85TJa8lIiIi/5Ld8trHTezOGRRFp+hq61pRSz7Moyg6QHM8rvr+nEFR9Bk0zqIARdEn0/1OnqAo+hR61sTz2wyKolO0Jvj56zMois7QM3H1i1EURQdojORnXYvy5EMViqI30r07TJDwUymKovfTGM2acx9n+URRdIb2T7qva13uUBSdoonzmeNR9Nr3GhRF76Z9GyOVZekRFEVvp/kxB+ufdeX1NSiKTtHcZ0mvyHkURedor8jh/jeQpSiKztAzwWN9rUZRdIrulhzLm2C9CkXRCbqOdS+51qEoOkljONZ5d02+CEXRZ9FaUM9QFH0GXeWugihCUXSS9vV+J/D180dRdIbWrC/JPwQUReeoiIiI/Gl+AF5BmKjyMZFTAAAAAElFTkSuQmCC"

        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, HttpProtocol.POST)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        requester.addProperty(HttpProtocol.BODY, qr)
        return requester
    }

    void "test invalidMethodRequest"() {
        given:
                def requester = new Requester()
                requester.addProperty(HttpProtocol.METHOD, HttpProtocol.GET)

        when:
                String message = "";
                try {
                    new QRService().validate(requester)
                }
                catch( QueryException e ) {
                    message = e.getMessage();
                }

        then:
                message == "Invalid HTTP request method: must be POST"
    }

    void "test requestWithoutBody"() {
        given:
        def requester = new Requester()
            requester.addProperty(HttpProtocol.METHOD, HttpProtocol.POST)

        when:
            String message = "";
            try {
                new QRService().validate(requester)
            }
            catch( QueryException e ) {
                message = e.getMessage();
            }

        then:
            message == "No se recibió el código QR en el cuerpo del HTTP request"
    }

    void "test emptyBodyRequest"() {
        given:
        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, HttpProtocol.POST)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        requester.addProperty(HttpProtocol.BODY, "")

        when:
            String message = "";
            try {
                new QRService().validate(requester)
            }
            catch( QueryException e ) {
                message = e.getMessage();
            }

        then:
            message == "No se recibió el código QR en el cuerpo del HTTP request"
    }

    void "test validRequest"() {
        given:
            def requester = this.generateMartinRequester()

        when:
            QRService qrService = new QRService()

        then:
            qrService.validate(requester) == true
    }

}
