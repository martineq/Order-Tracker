package ordertracker

import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.database.DatabaseException
import ordertracker.database.OrderRequestLoader
import ordertracker.internalServices.dtos.OrderRequestDTO
import ordertracker.internalServices.dtos.RequestParser
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class OrderRequestService implements Queryingly {

    private def jsonObject
    private boolean requestAccepted = false
    private String messageDescription
    private OrderRequestDTO orderRequestDTO
    private long seller_id

    OrderRequestService() {
        this.jsonObject = null
        this.orderRequestDTO = null
        this.requestAccepted = false
        this.messageDescription = ""
        this.seller_id = 0
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.POST.toString()) )
            throw new QueryException("Invalid HTTP request method: must be POST")

        try {
            String username = requester.getProperty(Keywords.USERNAME)
            def user = User.findByUsername(username)
            def userType = UserType.findByUser_idAndType(user.id, Seller.getTypeName())
            this.seller_id = userType.type_id
        }

        catch ( NullPointerException npe ) {
            throw new QueryException("Inexistent seller")
        }

        try {
            String body = requester.getProperty(HttpProtocol.BODY)
            this.orderRequestDTO = new RequestParser().parse(this.prepareJson(body), new OrderRequestDTO())
        }

        catch (NullPointerException e) {
            e.getMessage()
        }


        return true
    }

    private String prepareJson(String message) {
        message = message.replace('"[{', '[{')
        message = message.replace('\\','')
        return message.replace('}]"', '}]')
    }

    @Override
    def generateQuery() {
        try {
            def orderRequestLoader = new OrderRequestLoader(this.orderRequestDTO)
            orderRequestLoader.loadRequest(this.seller_id)
            this.messageDescription = "Request accepted"
            this.requestAccepted = true
        }

        catch (DatabaseException databaseException) {
            this.messageDescription = databaseException.getMessage()
        }

        finally {
            return this.requestAccepted
        }
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( this.requestAccepted == false )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR, this.messageDescription))

        return new ProtocolJsonBuilder().addStatus(new Status(Result.OK, this.messageDescription))
    }
}
