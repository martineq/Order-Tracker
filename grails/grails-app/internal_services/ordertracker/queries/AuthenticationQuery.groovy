package ordertracker.queries

import ordertracker.AuthenticationService
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.tranmission.DefaultTransmission
import org.apache.catalina.connector.RequestFacade

class AuthenticationQuery implements QueryProtocol {

    private Queryingly query
    private ProtocolJsonBuilder builder
    private String queryExceptionMessage
    private Requester requester

    AuthenticationQuery() {
        this.query = new AuthenticationService()
        this.builder = null
        this.queryExceptionMessage = ""
        this.requester = new Requester()
    }

    AuthenticationQuery(Queryingly query) {
        this.query = query
        this.builder = null
        this.queryExceptionMessage = ""
        this.requester = new Requester()
    }

    @Override
    QueryProtocol analyse(RequestFacade request) {
        this.requester.addProperty( "method", request.method )
        request.headerNames.each { header -> this.requester.addProperty( header, request.getHeader(header)) }
        return this
    }

    @Override
    QueryProtocol run() {
        try {
            this.query.validate(this.requester)
            this.query.generateQuery()
            this.builder = query.obtainResponse(DefaultTransmission.obtainDefaultTransmission())
        }

        catch( QueryException qe ) {
            this.queryExceptionMessage = qe.getMessage()
        }

        finally {
            return this
        }
    }

    @Override
    def buildJson() {
        if ( this.queryExceptionMessage.empty == true )
            return this.builder.build()

        return new ProtocolJsonBuilder(new Status(Result.ERROR, this.queryExceptionMessage)).build()
    }
}
