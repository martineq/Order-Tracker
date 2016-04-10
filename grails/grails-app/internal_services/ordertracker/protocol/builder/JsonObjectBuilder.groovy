package ordertracker.protocol.builder

import ordertracker.protocol.builder.writer.JsonWriterFactory

class JsonObjectBuilder implements Jsonable {

    private List<Jsonable> properties
    private Class jsonablesItemsType

    JsonObjectBuilder() {
        this.properties = new ArrayList<Jsonable>()
    }

    def addJsonableItem(Jsonable jsonable) {

        // TODO
        // validar si son todos iguales sino tira excepcion en la construcción
        this.jsonablesItemsType = jsonable.class
        this.properties.add(jsonable)
        return this
    }

    def buildJson() {
        try {
            if (this.jsonablesItemsType.simpleName.toString().compareTo(JsonObjectBuilder.simpleName) == 0)
                return new JsonWriterFactory(properties, true).writeJson()

            return new JsonWriterFactory(properties).writeJson()
        }

        catch (NullPointerException npe) {
            return new JsonWriterFactory(properties).writeJson()
        }
    }
}