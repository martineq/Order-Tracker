package ordertracker

import ordertracker.constants.ClientStates
import ordertracker.constants.OrderStates
import ordertracker.util.CalendarDate

class ClientOrderLoader {
    def load() {
        String setState = "UPDATE Agenda a SET a.state = :state WHERE a.seller_id = :seller_id AND a.client_id = :client_id"
        String setVisitedDate = "UPDATE Agenda a SET a.visitedDate = :visitedDate WHERE a.seller_id = :seller_id AND a.client_id = :client_id"


        def client = Client.findById(1)
        def seller = Seller.findById(1)
        new ClientOrder(seller_id:1, client_id:1, state:OrderStates.SOLICITADO.toString(), date: CalendarDate.fromCurrentDate(0), total_price:8550.00,sellername:seller.name,clientname:client.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller.id, client_id: client.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(0), seller_id: seller.id, client_id: client.id])

        def client2 = Client.findById(2)
        def seller2 = Seller.findById(1)
        new ClientOrder(seller_id:1, client_id:2, state:OrderStates.CANCELADO.toString(), date: CalendarDate.fromCurrentDate(1), total_price:8415.50,sellername:seller2.name,clientname:client2.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller2.id, client_id: client2.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(1), seller_id: seller2.id, client_id: client2.id])


        def client3 = Client.findById(3)
        def seller3 = Seller.findById(1)
        new ClientOrder(seller_id:1, client_id:3, state:OrderStates.DESPACHADO.toString(), date:CalendarDate.fromCurrentDate(2),total_price:3157.40,sellername:seller3.name,clientname:client3.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller3.id, client_id: client3.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(2), seller_id: seller3.id, client_id: client3.id])

        def client4 = Client.findById(2)
        def seller4 = Seller.findById(5)
        new ClientOrder(seller_id:5, client_id:2, state:OrderStates.DESPACHADO.toString(), date:CalendarDate.fromCurrentDate(3),total_price:1491.60,sellername:seller4.name,clientname:client4.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller4.id, client_id: client4.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(3), seller_id: seller4.id, client_id: client4.id])


        def client5 = Client.findById(46)
        def seller5 = Seller.findById(4)
        new ClientOrder(seller_id:4, client_id:46, state:OrderStates.CANCELADO.toString(), date: CalendarDate.fromCurrentDate(1), total_price:11045.40,sellername:seller5.name,clientname:client5.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller5.id, client_id: client5.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(1), seller_id: seller5.id, client_id: client5.id])
    }
}
