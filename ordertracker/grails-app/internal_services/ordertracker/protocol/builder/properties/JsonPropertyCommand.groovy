package ordertracker.protocol.builder.properties

import ordertracker.protocol.builder.Jsonable

class JsonPropertyCommand implements Jsonable {

    String propertyName
    def propertyValue
    Closure strategyBuilderMethod

    JsonPropertyCommand(String propertyName, def propertyValue, Closure strategyBuilderMethod) {
        this.propertyName = propertyName
        this.propertyValue = propertyValue
        this.strategyBuilderMethod = strategyBuilderMethod
    }

    def buildJson() {
        return this.strategyBuilderMethod(this.propertyName, this.propertyValue)
    }
}

