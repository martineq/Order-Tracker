package ordertracker

import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.database.SellerSellsReport
import ordertracker.information.InformationFinder
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class ActualSellsStatsService implements Queryingly {

    private long seller_id
    private def sellerSellsReport

    ActualSellsStatsService() {
        this.seller_id = 0
        this.sellerSellsReport = null
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
        sellerSellsReport = new SellerSellsReport()

        try {
            sellerSellsReport.caclulateVisitedClients()
            sellerSellsReport.caclulateOutOfRouteVisitedClients()
            sellerSellsReport.calculateTotalSoldUnits()
            sellerSellsReport.calculateGrossSales()
        }

        catch (Exception e) {
            this.sellerSellsReport = null
        }

        return ( this.sellerSellsReport != null )
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        return new ProtocolJsonBuilder()
    }
}
