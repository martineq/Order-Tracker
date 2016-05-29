package ordertracker

import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.database.SellerSellsReport
import ordertracker.information.InformationFinder
import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class ActualSellsStatsService implements Queryingly {

    private long seller_id
    private SellerSellsReport sellerSellsReport

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
        sellerSellsReport = new SellerSellsReport(seller_id)

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
        if ( sellerSellsReport == null )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR, "Reporting building failed"))

        return new ProtocolJsonBuilder(new Status(Result.OK, "Report OK"), new Data(this.buildDataObject()))
    }

    private def buildDataObject() {
        def dataJsonObject = new JsonObjectBuilder()

        dataJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.VISITED_CLIENTS_ES,sellerSellsReport.getVisitedClients()))
        dataJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.OUT_OF_ROUTE_VISITED_CLIENTS_ES,sellerSellsReport.getOutOfRouteVisitedClients()))
        dataJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.TOTAL_SOLD_UNITS_ES,sellerSellsReport.getTotalSoldUnits()))
        dataJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.TODAY_GROSS_SALES_ES,sellerSellsReport.getTodayGrossSales()))

        return dataJsonObject
    }
}
