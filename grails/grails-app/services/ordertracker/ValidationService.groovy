package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.security.UserEncryptor
import ordertracker.tranmission.TransmissionMedium

class ValidationService implements Queryingly{

    private User user
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
        try {
            User user = User.findByUsername(this.user.username.toString())
            return this.validationResult = new UserEncryptor(user).validateToken(this.user.getToken())
        }

        catch (NullPointerException e) {
            throw new QueryException("Invalid request - user rejected")
        }
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( this.validationResult == false )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR), "Validation Service failed")

        return new ProtocolJsonBuilder().addStatus(new Status(Result.OK,"Permitted access"))
    }
}
