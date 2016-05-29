package ordertracker.queries

import ordertracker.AuthenticationService
import ordertracker.tranmission.DefaultTransmission

class AuthenticationQuery extends QueryProtocol {

    private Queryingly query

    AuthenticationQuery() {
        this.query = new AuthenticationService()
        this.builder = null
        this.queryExceptionMessage = ""
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
        }

        finally {
            return this
        }
    }
}
