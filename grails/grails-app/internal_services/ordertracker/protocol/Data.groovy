package ordertracker.protocol

import ordertracker.protocol.builder.Jsonable
import ordertracker.protocol.builder.properties.JsonPropertyFactory

class Data implements Jsonable {

    private String jsonablePropertyName
    private Jsonable jsonablePropertyElement

    Data(Jsonable jsonable) {
        this.jsonablePropertyName = "data"
        this.jsonablePropertyElement = jsonable
    }

    @Override
    def buildJson() {
        return new JsonPropertyFactory(this.jsonablePropertyName, this.jsonablePropertyElement).buildJson()
    }
}
