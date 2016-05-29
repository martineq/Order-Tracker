package ordertracker.protocol.builder.writer

import ordertracker.protocol.builder.JsonElements
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.Jsonable

class JsonWriterFactory {

    private JsonWritableStrategy jsonWritableStrategy

    JsonWriterFactory(Collection<JsonObjectBuilder> jsonables, boolean many) {
        this.jsonWritableStrategy = new JsonWritableStrategy(jsonables, this.&jsonObjectStrategyMethod )
    }

    JsonWriterFactory(Collection<Jsonable> jsonable) {
        this.jsonWritableStrategy = new JsonWritableStrategy(jsonable, this.&jsonPropertiesStrategyMethod)
    }

    def writeJson() {
        return jsonWritableStrategy.writeJson()
    }

    def jsonObjectStrategyMethod(Collection<JsonObjectBuilder> jsonObjects) {
        return new EnumerationWriter(jsonObjects, JsonElements.JSON_OBJECT_LIST_START, JsonElements.JSON_OBJECT_LIST_END).write()
    }

    def jsonPropertiesStrategyMethod(Collection<Jsonable> jsonProperties)
    {
        return new EnumerationWriter(jsonProperties, JsonElements.JSON_OBJECT_START, JsonElements.JSON_OBJECT_END).write()
    }
}
