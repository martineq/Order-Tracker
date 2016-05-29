package ordertracker.services

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.Product
import ordertracker.ProductDescriptionService
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.queries.QueryException
import ordertracker.queries.Requester
import spock.lang.Specification

@TestFor(ProductDescriptionService)
@Mock(Product)
class ProductDescriptionServiceSpec extends Specification {

    private Requester generateMartinRequester() {
        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, HttpProtocol.GET)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        requester.addProperty(Keywords.PRODUCT_ID, 1)
        return requester
    }

    void "test invalidRequestMethod"() {
        given:
        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, HttpProtocol.PUT)

        and:
        def validationService = new ProductDescriptionService();
        def message = ""

        when:
        try {
            validationService.validate(requester)
        }
        catch (QueryException qe ) {
            message = qe.getMessage()
        }

        then:
        message == 'Invalid HTTP request method: must be GET'
    }

    void "test invalidRequestHeaders"() {
        given:
            Requester requester = new Requester()
            requester.addProperty(HttpProtocol.METHOD, HttpProtocol.GET)
            requester.addProperty(Keywords.USERNAME, 'martin')
            requester.addProperty(Keywords.TOKEN, 'token1')

        and:
            def validationService = new ProductDescriptionService();
            def message = ""

        when:
            try {
                validationService.validate(requester)
            }
            catch (QueryException qe ) {
                message = qe.getMessage()
            }

        then:
            message == 'Invalid request'
    }


}
