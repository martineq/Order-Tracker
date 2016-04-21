package ordertracker

import grails.transaction.Transactional
import ordertracker.constants.HttpProtocol
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
    private String errorMessageDescription
    private OrderRequestDTO orderRequestDTO

    OrderRequestService() {
        this.jsonObject = null
        this.orderRequestDTO = null
        this.requestAccepted = false
        this.errorMessageDescription = ""
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.POST.toString()) )
            throw new QueryException("Invalid HTTP request method: must be POST")

        this.orderRequestDTO = new RequestParser().parse(requester.getProperty(HttpProtocol.BODY), new OrderRequestDTO())
        return true
    }

    @Override
    @Transactional
    def generateQuery() {
        this.requestAccepted = true
        return null
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( this.requestAccepted == false )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR, this.errorMessageDescription))

        return new ProtocolJsonBuilder().addStatus(new Status(Result.OK, "La solictud ha sido aceptada."))
    }
}
