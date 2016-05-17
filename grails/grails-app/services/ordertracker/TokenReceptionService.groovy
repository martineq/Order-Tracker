package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.information.InformationException
import ordertracker.information.InformationFinder
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

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
        user_id = informationFinder.findUserID(Keywords.USERNAME)
    }

    @Override
    def generateQuery() {
        return false
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR, "Rejected request"))
    }
}
