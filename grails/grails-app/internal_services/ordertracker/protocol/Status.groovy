package ordertracker.protocol

import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.Jsonable
import ordertracker.protocol.builder.properties.JsonPropertyFactory

class Status implements Jsonable {

    private Result result
    private String description

    Status(Result result) {
        this.result = result
        this.description = ""
    }

    Status(Result result, String description) {
        this.result = result
        this.description = description
    }

    def buildJson() {
        def resultItem = new JsonPropertyFactory(this.result.getEnumName(), this.result.toString())
        def descriptionItem = new JsonPropertyFactory("description", this.description)
        def statusJsonObject = new JsonObjectBuilder().addJsonableItem(resultItem).addJsonableItem(descriptionItem)

        new JsonPropertyFactory( this.getClass().getSimpleName().toLowerCase(), statusJsonObject ).buildJson()
    }
}