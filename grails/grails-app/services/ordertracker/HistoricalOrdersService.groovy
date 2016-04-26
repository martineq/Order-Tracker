package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.database.HistoricalOrderQuery
import ordertracker.json.HistoricalOrdersJsonResponse
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class HistoricalOrdersService implements Queryingly {

    private long date_from
    private long date_upto
    private String username
    private List<ClientOrder> orders

    HistoricalOrdersService() {
        this.date_from = 0
        this.date_upto = 0
        this.username = ""
        this.orders = null
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.GET.toString()) != 0 )
            throw new QueryException("Invalid HTTP request method: must be GET")

        try {
            requester.validateRequest(Enums.asList(Keywords.DATE_FROM, Keywords.DATE_UPTO, Keywords.USERNAME))
            this.date_from = new Long((String) requester.getProperty(Keywords.DATE_FROM))
            this.date_upto = new Long((String) requester.getProperty(Keywords.DATE_UPTO))
            this.username = requester.getProperty(Keywords.USERNAME)

        } catch (NumberFormatException e) {
            throw new QueryException("Invalid week_date format")
        }

        return true
    }

    @Override
    def generateQuery() {
        orders = new HistoricalOrderQuery(this.date_from, this.date_upto).search(this.username)
        return true
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        return new HistoricalOrdersJsonResponse(this.orders).generateProtocolResponse()
    }
}
