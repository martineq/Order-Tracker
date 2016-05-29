package ordertracker.database

import ordertracker.Agenda
import ordertracker.Client
import ordertracker.Seller
import ordertracker.constants.ClientStates
import ordertracker.internalServices.dtos.OrderRequestDTO
import ordertracker.util.CalendarDate

/**
 * Created by martin on 15/05/16.
 */
class OrderRequestValidator {

    private OrderRequestDTO request

    OrderRequestValidator(OrderRequestDTO orderRequestDTO) {
        this.request = orderRequestDTO
    }

    private def validateTime(Agenda agenda) {
        def calendarDate = new CalendarDate(agenda.getDate())
        def startOfTheWeek = calendarDate.startingDateOfThisWeek()
        def endOfTheWeek = calendarDate.finishingDateOfThisWeek()
        def currentTime = CalendarDate.currentDate()

        if ( currentTime < startOfTheWeek || currentTime >= endOfTheWeek )
            throw new DatabaseException( "No se puede solicitar una orden fuera de la semana de trabajo" )
    }

    public def validateOrder(long seller_id) {

        if ( Seller.findById(seller_id) == null )
            throw new DatabaseException("Seller not found")

        if ( Client.findById(request.getClient_id()) == null )
            throw new DatabaseException("Client not found")

        def agenda = Agenda.findById(request.visit_id)

        if ( agenda == null )
            throw new DatabaseException("No se encontró una visita para ese cliente")

        if ( agenda.getState() == ClientStates.VISITADO.toString() )
            throw new DatabaseException("No se puede tomar la orden, el cliente ya fue visitado")

        this.validateTime(agenda)
    }
}
