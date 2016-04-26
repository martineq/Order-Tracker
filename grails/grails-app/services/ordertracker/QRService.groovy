package ordertracker

import grails.converters.JSON
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium
import org.grails.web.json.JSONObject

class QRService implements Queryingly{

    private String qr
    private boolean queryResult

    QRService() {
        this.qr = new String()
        this.queryResult = false
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.POST.toString()) )
            throw new QueryException("Invalid HTTP request method: must be POST")

        String qr = requester.getProperty(HttpProtocol.BODY)

        if ( qr == null || qr.isEmpty() )
            throw new QueryException("No se recibió el código QR en el cuerpo del HTTP request")

        JSONObject jsonObject = JSON.parse(qr)

        if ( jsonObject.size() == 0 )
            throw new QueryException("Malformed JSON")

        this.qr = jsonObject.getString(Keywords.QR.toString())

        return ( this.qr.isEmpty() == false )
    }

    @Override
    def generateQuery() {

        try {
            int space = this.qr.indexOf('-')
            String id = this.qr.substring(0, space)
            String email = this.qr.substring(space + 1, this.qr.size())

            if (Client.findByIdAndEmail(new Long(id), email) != null)
                this.queryResult = true
        }

        catch (NumberFormatException e) {}
        catch (IndexOutOfBoundsException e) {}

        return this.queryResult
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( queryResult == false )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR, "Invalid QR Code"))

        def productsService = new AvailableProductsService()
        productsService.generateQuery()
        productsService.obtainResponse(transmissionMedium)
    }
}
