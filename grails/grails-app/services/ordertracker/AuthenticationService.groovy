package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.security.UserEncryptor
import ordertracker.tranmission.TransmissionMedium

class AuthenticationService implements Queryingly{

    private User user
    private boolean authenticationResult
    private String randomToken

    AuthenticationService() {
        this.user = new User(username: '', password: '', token: '')
        this.authenticationResult = false
        this.randomToken = ""
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.GET.toString()) )
            throw new QueryException("Invalid HTTP request method: must be GET")

        requester.validateRequest( Enums.asList( Keywords.USERNAME, Keywords.PASSWORD ))
        this.user.username = requester.getProperty(Keywords.USERNAME)
        this.user.password = requester.getProperty(Keywords.PASSWORD)
        return true
    }

    @Override
    def generateQuery() {
        try {
            User user = User.findByUsername(this.user.username.toString())

            def cipher = new UserEncryptor(user)

            if ( cipher.validatePassword(this.user.getPassword()) == false )
                throw new QueryException("User rejected - invalid password")

            this.randomToken = cipher.generateRandomToken()

            cipher.encryptToken(this.randomToken)

            this.user = user
            return this.authenticationResult = true
        }

        catch (NullPointerException e) {
            throw new QueryException("User rejected")
        }
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( authenticationResult == false )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR, "Authentication failed"))

        return new ProtocolJsonBuilder().addStatus(new Status(Result.OK, "Authenticated username")).addData(this.generateData())
    }

    private Data generateData() {
        def jsonObjectBuilder = new JsonObjectBuilder()
        
        jsonObjectBuilder.addJsonableItem(new JsonPropertyFactory(Keywords.ID, (int) this.user.id))
        jsonObjectBuilder.addJsonableItem(new JsonPropertyFactory(Keywords.USERNAME, this.user.username))
        jsonObjectBuilder.addJsonableItem(new JsonPropertyFactory(Keywords.TOKEN, this.randomToken))

        return new Data(jsonObjectBuilder)
    }
}
