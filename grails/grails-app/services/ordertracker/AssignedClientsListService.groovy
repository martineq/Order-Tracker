package ordertracker

import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.Jsonable
import ordertracker.protocol.builder.properties.JsonPropertyFactory
import ordertracker.queries.Keywords
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class AssignedClientsListService implements Queryingly{

    private def clients
    private boolean validationResult

    AssignedClientsListService() {
        this.validationResult = false
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(Keywords.METHOD).toString().compareTo(Keywords.GET) )
            throw new QueryException("Invalid HTTP request method: must be GET")

        return true
    }

    @Override
    def generateQuery() {
        try {
            // TODO: search seller Clients
            this.clients = Client.findAll()
        }

        catch( NullPointerException npe ) {
            this.validationResult = false
        }

        finally {
            return this.validationResult
        }
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( this.validationResult == true )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR,"undefined"))

        return new ProtocolJsonBuilder().addStatus(new Status(Result.OK,"Clients listed")).addData(this.generateData())
    }

    private Data generateData() {
        def clientsList = new JsonObjectBuilder()
        this.clients.each() { Client client -> clientsList.addJsonableItem(this.generateJsonClientObject(client)) }
        return new Data(clientsList)
    }

    private Jsonable generateJsonClientObject(Client client) {
        def clientJsonObject = new JsonObjectBuilder()

        clientJsonObject.addJsonableItem(new JsonPropertyFactory("name", client.name))
        clientJsonObject.addJsonableItem(new JsonPropertyFactory("address", client.address))
        clientJsonObject.addJsonableItem(new JsonPropertyFactory("city", client.city))
        clientJsonObject.addJsonableItem(new JsonPropertyFactory("qrcode", client.qrcode))

        return clientJsonObject
    }
}
