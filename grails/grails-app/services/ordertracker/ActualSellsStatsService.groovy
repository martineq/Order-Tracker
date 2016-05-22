package ordertracker

import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.information.InformationFinder
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class ActualSellsStatsService implements Queryingly {

    private long seller_id

    ActualSellsStatsService() {
        this.seller_id = 0
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.GET.toString()) )
            throw new QueryException("Invalid HTTP request method: must be GET")

        seller_id = new InformationFinder(requester).findSellerID(Keywords.USERNAME)
        return true
    }

    @Override
    def generateQuery() {
        return false
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        return new ProtocolJsonBuilder()
    }
}
