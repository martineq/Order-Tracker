package ordertracker.protocol.builder.properties

import ordertracker.protocol.builder.JsonElements
import ordertracker.protocol.builder.Jsonable

class JsonPropertyFactory implements Jsonable {

    private JsonPropertyCommand jsonProperty

    JsonPropertyFactory(String name, String value) {
        jsonProperty = new JsonPropertyCommand(name, value, this.&jsonStringCommandProperty )
    }

    JsonPropertyFactory(String name, Jsonable value) {
        jsonProperty = new JsonPropertyCommand(name, value, this.&jsonJsonableCommandProperty )
    }

    def buildJson() {
        return jsonProperty.buildJson()
    }

    private def encapsulateString(String message) {
        return JsonElements.JSON_QUOTE + message + JsonElements.JSON_QUOTE
    }

    def jsonStringCommandProperty(String name, String value) {
        def propertyName = this.encapsulateString(name)
        def propertyValue = this.encapsulateString(value)
        return propertyName + JsonElements.JSON_PROPERTY_SEPARATOR + propertyValue
    }

    def jsonJsonableCommandProperty(String name, Jsonable value) {
        def propertyName = this.encapsulateString(name)
        def propertyValue = value.buildJson()
        return propertyName + JsonElements.JSON_PROPERTY_SEPARATOR + propertyValue
    }
}
