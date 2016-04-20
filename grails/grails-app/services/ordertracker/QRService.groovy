package ordertracker

import ordertracker.constants.HttpProtocol
import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

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

        this.qr = requester.getProperty(HttpProtocol.BODY)

        if ( this.qr == null || this.qr.isEmpty() )
            throw new QueryException("No se recibió el código QR en el cuerpo del HTTP request")

        return ( this.qr.isEmpty() == false )
    }

    @Override
    def generateQuery() {
        this.queryResult = true
        return this.queryResult
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( queryResult == false )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR, "Código QR inválido"))

        def productsService = new AvailableProductsService()
        productsService.generateQuery()
        productsService.obtainResponse(transmissionMedium)
    }
}
