package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.Keywords
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
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
        requester.validateRequest(Enums.asList(Keywords.USERNAME, Keywords.TOKEN))

        this.user.username = requester.getProperty(Keywords.USERNAME)
        this.user.token = requester.getProperty(Keywords.TOKEN)
        return true
    }

    @Override
    def generateQuery() {
        User user = User.findByUsername(this.user.username.toString())

        if ( user == null || this.user.token.compareTo(user.token) != 0 )
            throw new QueryException("Invalid request - user rejected")

        return this.validationResult = true
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( this.validationResult == false )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR), "Validation Service failed")

        return new ProtocolJsonBuilder().addStatus(new Status(Result.OK,"Permitted access"))
    }
}
