package ordertracker.queries

import org.apache.commons.collections.map.HashedMap

class Requester {

    private Map<String, Object> properties

    Requester() {
        this.properties = new HashedMap()
    }

    def addProperty(Enum property, Enum object) {
        this.properties.put(property.toString(), object.toString())
    }

    def addProperty(String property, Object object) {
        this.properties.put(property, object)
    }

    def getProperty(String property) {
        return this.properties.get(property)
    }

    def addProperty(Enum property, Object object) {
        this.properties.put(property.toString(), object)
    }

    def getProperty(Enum property) {
        return this.properties.get(property.toString())
    }

    boolean validateRequest(List<String> minimumProperties) {
        def propertiesFound = 0
        def properties = this.properties.keySet()
        minimumProperties.each { property -> propertiesFound += properties.contains(property) ? 1 : 0 }

        if ( propertiesFound != minimumProperties.size() )
            throw new QueryException("Invalid request")
        return true
    }

}
