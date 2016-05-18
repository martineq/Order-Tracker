package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.information.InformationFinder
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium
import ordertracker.util.logger.Log

class TokenReceptionService implements Queryingly {

    private long user_id
    private String gcm_token

    TokenReceptionService() {
        this.user_id = 0
        this.gcm_token = ""
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.POST.toString()) )
            throw new QueryException("Invalid HTTP request method: must be POST")

        requester.validateRequest(Enums.asList(Keywords.GCM_TOKEN))

        def informationFinder = new InformationFinder(requester)
        gcm_token = informationFinder.findProperty(Keywords.GCM_TOKEN, "Missing token")
        user_id = informationFinder.findSellerID(Keywords.USERNAME)
    }

    @Override
    def generateQuery() {
        def userNotification = UserNotification.findByUser_id(user_id)

        if ( userNotification == null )
            userNotification = new UserNotification(user_id: user_id, token_gcm: gcm_token )

        else userNotification.token_gcm = gcm_token

        userNotification.save(flush: true)
        return false
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( UserNotification.findByUser_idAndToken_gcm(user_id, gcm_token) == null )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR, "Rejected request"))

        Log.info("GCM token recibido: [ " + gcm_token + " ]")
        GCMConnectorService.getInstance().push()
        return new ProtocolJsonBuilder().addStatus(new Status(Result.OK, "User token updated"))

    }
}
