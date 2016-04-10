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

    JsonPropertyFactory(String name, Double value) {
        jsonProperty = new JsonPropertyCommand(name, value, this.&jsonDoubleCommandProperty )
    }

    JsonPropertyFactory(String name, int value) {
        jsonProperty = new JsonPropertyCommand(name, value, this.&jsonIntegerCommandProperty )
    }

    JsonPropertyFactory(Enum name, String value) {
        jsonProperty = new JsonPropertyCommand(name.toString(), value, this.&jsonStringCommandProperty )
    }

    JsonPropertyFactory(Enum name, Jsonable value) {
        jsonProperty = new JsonPropertyCommand(name.toString(), value, this.&jsonJsonableCommandProperty )
    }

    JsonPropertyFactory(Enum name, Double value) {
        jsonProperty = new JsonPropertyCommand(name.toString(), value, this.&jsonDoubleCommandProperty )
    }

    JsonPropertyFactory(Enum name, int value) {
        jsonProperty = new JsonPropertyCommand(name.toString(), value, this.&jsonIntegerCommandProperty )
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

    def jsonDoubleCommandProperty(String name, Double value) {
        def propertyName = this.encapsulateString(name)
        def propertyValue = value.toString()
        return propertyName + JsonElements.JSON_PROPERTY_SEPARATOR + propertyValue
    }

    def jsonIntegerCommandProperty(String name, int value) {
        def propertyName = this.encapsulateString(name)
        def propertyValue = value.toString()
        return propertyName + JsonElements.JSON_PROPERTY_SEPARATOR + propertyValue
    }
}
