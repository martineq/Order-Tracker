package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class HistoricalOrdersService implements Queryingly {

    private long week_date

    HistoricalOrdersService() {
        this.week_date = 0
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.GET.toString()) != 0 )
            throw new QueryException("Invalid HTTP request method: must be GET")

        requester.validateRequest( Enums.asList( Keywords.WEEK_DATE) )
        this.week_date = requester.getProperty(Keywords.WEEK_DATE)

        return true
    }

    @Override
    def generateQuery() {
        return true
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        return new ProtocolJsonBuilder()
    }
}
