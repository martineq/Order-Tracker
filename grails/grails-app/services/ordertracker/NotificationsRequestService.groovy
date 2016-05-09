package ordertracker

import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.notifications.NotificationService
import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class NotificationsRequestService implements Queryingly {

    private long seller_id
    private def messages

    NotificationsRequestService() {
        this.seller_id = 0;
        this.messages = null
    }


    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.GET.toString()) != 0 )
            throw new QueryException("Invalid HTTP request method: must be GET")

        try {
            def username = requester.getProperty(Keywords.USERNAME)
            def userType = UserType.findByUser_id(User.findByUsername(username).id)
            if (userType.type == Seller.getTypeName())
                seller_id = userType.type_id
            else
                new QueryException("User is not a Seller")
        }
        catch (NullPointerException e ){
            new QueryException("Invalid request")
        }

        return true
    }

    @Override
    def generateQuery() {
        messages = new NotificationService().searchMessages(seller_id)
        return true
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {

        try {
            if (messages.size() != 0) {
                def data = new Data(this.generateData())
                return new ProtocolJsonBuilder(new Status(Result.OK, "Permitted access"), data)
            } else {
                def data = new Data(new JsonObjectBuilder().addJsonableItem(new JsonObjectBuilder()))
                return new ProtocolJsonBuilder(new Status(Result.OK, "No notifications available"), data)
            }
        }

        catch (NullPointerException e) {
            return new ProtocolJsonBuilder(new Status(Result.ERROR, "Server error"))
        }
    }

    private def generateData() {
        def jsonObjectBuilder = new JsonObjectBuilder()

        messages.each { Distribution distribution ->

            def message = Push_message.findById(distribution.message_id)

            def notification = new JsonObjectBuilder()
            notification.addJsonableItem(new JsonPropertyFactory(Keywords.ID, message.id))
            notification.addJsonableItem(new JsonPropertyFactory(Keywords.TITLE, message.title))
            notification.addJsonableItem(new JsonPropertyFactory(Keywords.BODY, message.description))

            jsonObjectBuilder.addJsonableItem(notification)
        }

        return jsonObjectBuilder
    }
}
