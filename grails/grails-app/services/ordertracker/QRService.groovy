package ordertracker

import ordertracker.constants.HttpProtocol
import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class QRService implements Queryingly{

    private String qr;

    QRService() {
        this.qr = new String();
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
        return true
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        return new ProtocolJsonBuilder()
    }

    private Data generateData() {

    }
}
