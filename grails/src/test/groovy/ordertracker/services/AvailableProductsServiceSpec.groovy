package ordertracker.services

import grails.test.mixin.TestFor
import ordertracker.AvailableProductsService
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.queries.QueryException
import ordertracker.queries.Requester
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AvailableProductsService)
class AvailableProductsServiceSpec extends Specification {

    private Requester generateMartinRequester() {
        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, HttpProtocol.GET)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        return requester
    }

    void "test invalidRequestMethod"() {
        given:
        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, HttpProtocol.PUT)

        and:
        def validationService = new AvailableProductsService();
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

    void "test validRequestMethod"() {
        given:
            def requester = this.generateMartinRequester()

        and:
            def products = new AvailableProductsService()

        when:
            def validationResult = products.validate(requester)

        then:
            validationResult == true
    }
}
