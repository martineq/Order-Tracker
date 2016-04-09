package ordertracker.queries

import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import org.apache.catalina.connector.RequestFacade

abstract class QueryProtocol {

    protected ProtocolJsonBuilder builder
    protected String queryExceptionMessage
    protected Requester requester

    QueryProtocol analyse(RequestFacade request) {
        this.requester.addProperty( "method", request.method )
        request.headerNames.each { header -> this.requester.addProperty( header, request.getHeader(header)) }
        return this
    }

    abstract QueryProtocol run()

    def buildJson() {
        if ( this.queryExceptionMessage.empty == true )
            return this.builder.build()

        return new ProtocolJsonBuilder(new Status(Result.ERROR, this.queryExceptionMessage)).build()
    }
}
