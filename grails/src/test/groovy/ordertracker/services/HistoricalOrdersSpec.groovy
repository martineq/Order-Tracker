package ordertracker.services

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.ClientOrder
import ordertracker.HistoricalOrdersService
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.queries.Requester
import ordertracker.tranmission.DefaultTransmission
import spock.lang.Specification

@Mock( [ClientOrder])
@TestFor(HistoricalOrdersService)
class HistoricalOrdersSpec extends Specification {

    private Requester generateRequester(Enum httpProtocol) {
        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, httpProtocol)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        return requester
    }

    void "test invalidRequest"() {
        given:
            def requester = this.generateRequester(HttpProtocol.POST)
        and:
            def message = ""
        when:
            try{
                new HistoricalOrdersService().validate(requester)
            }

            catch ( Exception e ) {
                message = e.getMessage()
            }

        then:
            message == "Invalid HTTP request method: must be GET"
    }

    void "test validMethodRequestWithoutDateHeader"() {
        given:
            def requester = this.generateRequester(HttpProtocol.GET)

        and:
            def message = ""

        when:
            try{
                new HistoricalOrdersService().validate(requester)
            }

            catch ( Exception e ) {
                message = e.getMessage()
            }

        then:
            message == "Invalid request"
    }

    void "test validRequest"() {
        given:
            def requester = this.generateRequester(HttpProtocol.GET)
            requester.addProperty(Keywords.WEEK_DATE, Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString())).getTimeInMillis())

        when:
            def service = new HistoricalOrdersService()

        then:
            service.validate(requester) == true
    }



}
