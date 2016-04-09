package ordertracker

import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.queries.Keywords
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class ValidationService implements Queryingly{

    private def user
    private boolean validationResult

    ValidationService() {
        this.user = new User(username: '', password: '', token: '')
        this.validationResult = false
    }

    @Override
    def validate(Requester requester) {
        if ( requester.validateRequest(Arrays.asList(Keywords.USERNAME, Keywords.TOKEN)) == false )
            throw new QueryException("Invalid request - user rejected")

        this.user.username = requester.getProperty(Keywords.USERNAME)
        this.user.token = requester.getProperty(Keywords.TOKEN)
        return true
    }

    @Override
    def generateQuery() {
        try {
            User user = User.findByUsername(this.user.username.toString())
            this.validationResult = this.user.token.compareTo(user.token) == 0 ? true : false
        }

        catch( NullPointerException npe ) {
            this.validationResult = false
        }

        finally {
            return this.validationResult
        }
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        return new ProtocolJsonBuilder().addStatus(new Status(Result.defineResult(this.validationResult),"Permitted access"))
    }
}
