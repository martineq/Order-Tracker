package ordertracker

import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory
import ordertracker.queries.Keywords
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class AuthenticationService implements Queryingly{

    private User user
    private boolean authenticationResult
    private List<String> properties

    AuthenticationService() {
        this.user = new User(username: '', password: '', token: '')
        this.authenticationResult = false
        this.properties = Arrays.asList( Keywords.USERNAME, Keywords.PASSWORD )
    }

    private ProtocolJsonBuilder validUser(ProtocolJsonBuilder protocolJsonBuilder) {
        protocolJsonBuilder.addStatus(new Status(Result.OK, "Authenticated username"))
        protocolJsonBuilder.addData(this.generateData());

        return protocolJsonBuilder
    }

    private ProtocolJsonBuilder invalidUser(ProtocolJsonBuilder protocolJsonBuilder) {
        protocolJsonBuilder.addStatus(new Status(Result.ERROR, "Authentication failed"))
        return protocolJsonBuilder
    }

    private Data generateData() {
        new Data(new JsonObjectBuilder().addJsonableItem(new JsonPropertyFactory("token", this.user.token)))
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty("method").toString().compareTo("GET") )
            throw new QueryException("Invalid HTTP request method: must be GET")

        boolean requesterValidationResult = requester.validateRequest(this.properties)

        if ( requesterValidationResult == true ) {
            this.user.username = requester.getProperty(Keywords.USERNAME)
            this.user.password = requester.getProperty(Keywords.PASSWORD)
        }

        return requesterValidationResult
    }

    @Override
    def generateQuery() {
        try {
            User user = User.findByUsername(this.user.username)
            this.user.token = user.token
            this.authenticationResult = this.user.password.compareTo(user.password) == 0 ? true : false
        }

        catch( NullPointerException npe ) {
            this.authenticationResult = false
        }

        finally {
            return this.authenticationResult
        }
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        ProtocolJsonBuilder protocolJsonBuilder = new ProtocolJsonBuilder()
        authenticationResult ? this.validUser(protocolJsonBuilder) : this.invalidUser( protocolJsonBuilder )
        return protocolJsonBuilder
    }
}
