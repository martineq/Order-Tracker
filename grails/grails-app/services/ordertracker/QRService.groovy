package ordertracker

import grails.converters.JSON
import ordertracker.constants.ClientStates
import ordertracker.constants.Enums
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

    private long client_id
    private String qr
    private String errorMessage
    private boolean queryResult

    QRService() {
        this.errorMessage = "Código QR Inválido"
        this.qr = new String()
        this.queryResult = false
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.POST.toString()) )
            throw new QueryException("Invalid HTTP request method: must be POST")

        requester.validateRequest(Enums.asList(HttpProtocol.BODY, Keywords.CLIENT_ID))

        try {
            client_id = new Long(requester.getProperty(Keywords.CLIENT_ID))
        }

        catch (NumberFormatException e) {
            throw new QueryException("Invalid client_id type")
        }

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

        Client client = Client.findByIdAndQrcode(client_id, qr)
        if ( client != null ) {
            if ( client.getState() != ClientStates.VISITADO.toString() )
                this.queryResult = true

            else
                errorMessage = "El cliente ya se ha VISITADO"
        }

        return this.queryResult
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( queryResult == false )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR, this.errorMessage))

        def productsService = new AvailableProductsService()
        productsService.generateQuery()
        productsService.obtainResponse(transmissionMedium)
    }
}
