package ordertracker

import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.Jsonable
import ordertracker.protocol.builder.properties.JsonPropertyFactory
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
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.GET.toString()) )
            throw new QueryException("Invalid HTTP request method: must be GET")

        return true
    }

    @Override
    def generateQuery() {
        // TODO: search seller Clients
        this.clients = Client.findAll()
        return this.validationResult = this.clients != null
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( this.validationResult == false )
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

        clientJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.ID, (int) client.id))
        clientJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.NAME, client.name))
        clientJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.ADDRESS, client.address))
        clientJsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.CITY, client.city))
        clientJsonObject.addJsonableItem(new JsonPropertyFactory("email", client.email))
        clientJsonObject.addJsonableItem(new JsonPropertyFactory("qrcode", client.qrcode))

        return clientJsonObject
    }
}
