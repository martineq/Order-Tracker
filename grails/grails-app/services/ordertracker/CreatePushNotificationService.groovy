package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.notifications.NotificationService
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class CreatePushNotificationService implements Queryingly {

    private String title
    private String body

    CreatePushNotificationService() {
        this.body = ""
        this.title = ""
    }


    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.POST.toString()) != 0 )
            throw new QueryException("Invalid HTTP request method: must be POST")

        requester.validateRequest( Enums.asList( Keywords.TITLE, Keywords.BODY ))

        title = requester.getProperty(Keywords.TITLE)
        body = requester.getProperty(Keywords.BODY)

        return true
    }

    @Override
    def generateQuery() {
        new NotificationService().add(title, body)
        return true
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        return new ProtocolJsonBuilder(new Status(Result.OK, "Message added"))
    }

}
