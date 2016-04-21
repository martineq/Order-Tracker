package ordertracker

import grails.transaction.Transactional
import ordertracker.constants.HttpProtocol
import ordertracker.internalServices.dtos.OrderRequestDTO
import ordertracker.internalServices.dtos.RequestParser
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class OrderRequestService implements Queryingly {

    private def jsonObject
    private OrderRequestDTO orderRequestDTO

    OrderRequestService() {
        this.jsonObject = null
        this.orderRequestDTO = null
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
        return null
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        return new ProtocolJsonBuilder()
    }
}
