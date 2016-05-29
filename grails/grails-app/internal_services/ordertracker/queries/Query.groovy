package ordertracker.queries

import ordertracker.ValidationService
import ordertracker.tranmission.DefaultTransmission

class Query extends QueryProtocol {

    private Queryingly query

    Query(Queryingly query) {
        this.query = query
        this.builder = null
        this.queryExceptionMessage = ""
        this.requester = new Requester()
    }

    private boolean validateRequester() {
        def validationService = new ValidationService()
        validationService.validate(this.requester)

        if ( validationService.generateQuery() == false )
            throw new QueryException("Invalid token - access permission denied")
    }

    @Override
    QueryProtocol run() {
        try {
            this.validateRequester()
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
