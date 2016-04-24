package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.database.HistoricalOrderQuery
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class HistoricalOrdersService implements Queryingly {

    private long week_date
    private String username
    private List<ClientOrder> orders

    HistoricalOrdersService() {
        this.week_date = 0
        this.username = ""
        this.orders = null
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.GET.toString()) != 0 )
            throw new QueryException("Invalid HTTP request method: must be GET")

        requester.validateRequest( Enums.asList( Keywords.WEEK_DATE, Keywords.USERNAME) )
        this.week_date = requester.getProperty(Keywords.WEEK_DATE)
        this.username = requester.getProperty(Keywords.USERNAME)

        return true
    }

    @Override
    def generateQuery() {
        orders = new HistoricalOrderQuery(this.week_date).search(this.username)
        return true
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        return new ProtocolJsonBuilder()
    }
}
