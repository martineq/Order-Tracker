package ordertracker.queries

import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.tranmission.DefaultTransmission

class SimpleQuery extends QueryProtocol {

    private Queryingly query
    private boolean error

    SimpleQuery(Queryingly query) {
        this.query = query
        this.error = false
        this.requester = new Requester()
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
            this.error = true
        }

        finally {
            return this
        }
    }

    @Override
    def buildResponse() {
        if ( error == true )
            return new ProtocolJsonBuilder(new Status(Result.ERROR, this.queryExceptionMessage)).build()

        return this.builder.build()
    }
}
