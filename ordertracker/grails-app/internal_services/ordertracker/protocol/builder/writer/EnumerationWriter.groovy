package ordertracker.protocol.builder.writer

import ordertracker.protocol.builder.JsonElements
import ordertracker.protocol.builder.Jsonable

class EnumerationWriter {

    private Collection<Jsonable> collection
    private String elementsStart
    private String elementsEnd
    private String elementsSeparator

    EnumerationWriter(Collection<Jsonable> collection, String start = "", String end = "", String separator = JsonElements.JSON_ELEMENT_SEPARATOR) {
        this.collection = collection
        this.elementsSeparator = separator
        this.elementsStart = start
        this.elementsEnd = end
    }

    def write(){
        if ( collection.size() == 0 )
            return this.elementsStart + this.elementsEnd

        def json = this.elementsStart
        collection.each { element -> json += element.buildJson() + this.elementsSeparator }
        return json.replaceFirst(',$', this.elementsEnd)
    }
}
