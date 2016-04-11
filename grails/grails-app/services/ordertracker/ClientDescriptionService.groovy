package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.InvalidConstants
import ordertracker.constants.Keywords
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

class ClientDescriptionService implements Queryingly{

    private def clientID
    private Client client

    ClientDescriptionService() {
        this.clientID = InvalidConstants.INVALID_TUPLA
        this.client = null
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.GET.toString()) != 0 )
            throw new QueryException("Invalid HTTP request method: must be GET")

        requester.validateRequest( Enums.asList( Keywords.CLIENT_ID))
        this.clientID = requester.getProperty(Keywords.CLIENT_ID)
        return true
    }

    @Override
    def generateQuery() {
        this.client = Client.findById(this.clientID.toString())

        if ( this.client == null )
            throw new QueryException("Client not found in database")

        return true
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        try {
            return new ProtocolJsonBuilder().addStatus(new Status(Result.OK, "Client found")).addData(this.generateData())
        }

        catch( NullPointerException npe ){
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR, "Server error"))
        }
    }

    private Data generateData() {
        JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder()

        jsonObjectBuilder.addJsonableItem(new JsonPropertyFactory(Keywords.CLIENT_ID, this.client.id.toString()))
        jsonObjectBuilder.addJsonableItem(new JsonPropertyFactory(Keywords.NAME, this.client.name))
        jsonObjectBuilder.addJsonableItem(new JsonPropertyFactory(Keywords.ADDRESS, this.client.address))
        jsonObjectBuilder.addJsonableItem(new JsonPropertyFactory(Keywords.CITY, this.client.city))
        jsonObjectBuilder.addJsonableItem(new JsonPropertyFactory(Keywords.STATE, this.client.state))

        return new Data(jsonObjectBuilder)
    }
}
