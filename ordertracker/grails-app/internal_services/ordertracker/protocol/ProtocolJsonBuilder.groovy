package ordertracker.protocol

import ordertracker.protocol.builder.JsonException
import ordertracker.protocol.builder.JsonObjectBuilder

class ProtocolJsonBuilder {

    private def jsonObjectBuilder

    ProtocolJsonBuilder() {
        this.jsonObjectBuilder = new JsonObjectBuilder()
    }

    ProtocolJsonBuilder(Status status) {
        this.jsonObjectBuilder = new JsonObjectBuilder()
        this.jsonObjectBuilder.addJsonableItem(status)
    }

    ProtocolJsonBuilder(Status status, Data dataStrategy) {
        this.jsonObjectBuilder = new JsonObjectBuilder()
        this.jsonObjectBuilder.addJsonableItem(status)
        this.jsonObjectBuilder.addJsonableItem(dataStrategy)
    }

    def addStatus(Status status) {
        this.jsonObjectBuilder.addJsonableItem(status)
    }

    def addData(Data data) {
        this.jsonObjectBuilder.addJsonableItem(data)
    }

    def build() {
        try {
            return this.jsonObjectBuilder.buildJson()
        }

        catch(JsonException jep) {
            return new Status(Result.ERROR).buildJson()
        }
    }
}