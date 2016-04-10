package ordertracker.queries

import ordertracker.ValidationService
import ordertracker.tranmission.DefaultTransmission

class SimpleQuery extends QueryProtocol {

    private Queryingly query
    private boolean error

    SimpleQuery(Queryingly query) {
        this.query = query
        this.error = false
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
            this.error = true
        }

        finally {
            return this
        }
    }

    @Override
    def buildResponse() {
        if ( error == true )
            return ""

        return this.query.obtainResponse(DefaultTransmission.obtainDefaultTransmission())
    }
}
