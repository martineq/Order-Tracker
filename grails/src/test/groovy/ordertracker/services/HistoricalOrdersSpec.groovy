package ordertracker.services

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.Client
import ordertracker.ClientLoader
import ordertracker.ClientOrder
import ordertracker.ClientOrderLoader
import ordertracker.HistoricalOrdersService
import ordertracker.Seller
import ordertracker.SellerLoader
import ordertracker.User
import ordertracker.UserLoader
import ordertracker.UserType
import ordertracker.UserTypeLoader
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.constants.ServerDetails
import ordertracker.queries.Requester
import ordertracker.tranmission.DefaultTransmission
import ordertracker.util.CalendarDate
import ordertracker.util.logger.Log
import spock.lang.Specification

@Mock( [Seller, Client, ClientOrder, User, UserType])
@TestFor(HistoricalOrdersService)
class HistoricalOrdersSpec extends Specification {

    def setup() {
        Log.InitializeLogger(ServerDetails.SERVER_LOGS_PATH, ServerDetails.SERVER_INFO_LOG_FILE_NAME )

        new SellerLoader().load()
        new ClientLoader().load()
        new UserLoader().load()
        new UserTypeLoader().load()
        new ClientOrderLoader().load()
    }

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
            requester.addProperty(Keywords.DATE_FROM, CalendarDate.fromCurrentDate(-7))
            requester.addProperty(Keywords.DATE_UPTO, CalendarDate.fromCurrentDate(+7))

        when:
            def service = new HistoricalOrdersService()

        then:
            service.validate(requester) == true
    }

    void "test completeValidRequest"() {
        given:
            def requester = this.generateRequester(HttpProtocol.GET)
            requester.addProperty(Keywords.DATE_FROM, CalendarDate.fromCurrentDate(-7))
            requester.addProperty(Keywords.DATE_UPTO, CalendarDate.fromCurrentDate(+7))

        when:
            def service = new HistoricalOrdersService()

        and:
            service.validate(requester)
            service.generateQuery()
            String response = service.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build()

        then:
            response.contains('{"status":{"result":"ok","description":"Query succeded"},"data":[{"id":') == true
    }
}
