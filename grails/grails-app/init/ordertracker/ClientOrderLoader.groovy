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
        new ClientOrder(seller_id:1, client_id:1, state:OrderStates.SOLICITADO.toString(), date: CalendarDate.fromCurrentDate(-35), total_price:8550.00,sellername:seller.name,clientname:client.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller.id, client_id: client.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(-35), seller_id: seller.id, client_id: client.id])

        def client2 = Client.findById(2)
        def seller2 = Seller.findById(1)
        new ClientOrder(seller_id:1, client_id:2, state:OrderStates.SOLICITADO.toString(), date: CalendarDate.fromCurrentDate(-65), total_price:8415.50,sellername:seller2.name,clientname:client2.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller2.id, client_id: client2.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(-65), seller_id: seller2.id, client_id: client2.id])


        def client3 = Client.findById(3)
        def seller3 = Seller.findById(1)
        new ClientOrder(seller_id:1, client_id:3, state:OrderStates.DESPACHADO.toString(), date:CalendarDate.fromCurrentDate(-95),total_price:3157.40,sellername:seller3.name,clientname:client3.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller3.id, client_id: client3.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(-95), seller_id: seller3.id, client_id: client3.id])

        def client4 = Client.findById(2)
        def seller4 = Seller.findById(5)
        new ClientOrder(seller_id:5, client_id:2, state:OrderStates.DESPACHADO.toString(), date:CalendarDate.fromCurrentDate(-155),total_price:1491.60,sellername:seller4.name,clientname:client4.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller4.id, client_id: client4.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(-155), seller_id: seller4.id, client_id: client4.id])


        def client5 = Client.findById(9)
        def seller5 = Seller.findById(4)
        new ClientOrder(seller_id:4, client_id:9, state:OrderStates.SOLICITADO.toString(), date: CalendarDate.fromCurrentDate(-155), total_price:11045.40,sellername:seller5.name,clientname:client5.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller5.id, client_id: client5.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(-155), seller_id: seller5.id, client_id: client5.id])
        
        
        def client6 = Client.findById(15)
        def seller6 = Seller.findById(1)
        new ClientOrder(seller_id:1, client_id:15, state:OrderStates.SOLICITADO.toString(), date: CalendarDate.fromCurrentDate(-95), total_price:11045.40,sellername:seller6.name,clientname:client6.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller6.id, client_id: client6.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(-95), seller_id: seller6.id, client_id: client6.id])
        
        
        
        def client7 = Client.findById(23)
        def seller7 = Seller.findById(6)
        new ClientOrder(seller_id:6, client_id:23, state:OrderStates.SOLICITADO.toString(), date: CalendarDate.fromCurrentDate(-185), total_price:8415.50,sellername:seller7.name,clientname:client7.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller7.id, client_id: client7.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(-185), seller_id: seller7.id, client_id: client7.id])
        
        
        
        
        
        
        def client8 = Client.findById(20)
        def seller8 = Seller.findById(6)
        new ClientOrder(seller_id:6, client_id:20, state:OrderStates.SOLICITADO.toString(), date: CalendarDate.fromCurrentDate(-215), total_price:8415.50,sellername:seller8.name,clientname:client8.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller8.id, client_id: client8.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(-215), seller_id: seller8.id, client_id: client8.id])
        
        
        
        
        def client9 = Client.findById(21)
        def seller9 = Seller.findById(13)
        new ClientOrder(seller_id:13, client_id:21, state:OrderStates.SOLICITADO.toString(), date: CalendarDate.fromCurrentDate(-245), total_price:10000,sellername:seller9.name,clientname:client9.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller9.id, client_id: client9.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(-245), seller_id: seller9.id, client_id: client9.id])
        
        
        
        
        def client10 = Client.findById(22)
        def seller10 = Seller.findById(14)
        new ClientOrder(seller_id:14, client_id:22, state:OrderStates.SOLICITADO.toString(), date: CalendarDate.fromCurrentDate(-275), total_price:10000,sellername:seller10.name,clientname:client10.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller10.id, client_id: client10.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(-275), seller_id: seller10.id, client_id: client10.id])
        
        
        
        
        new ClientOrder(seller_id:14, client_id:22, state:OrderStates.SOLICITADO.toString(), date: CalendarDate.fromCurrentDate(-305), total_price:8500,sellername:seller10.name,clientname:client10.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller10.id, client_id: client10.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(-305), seller_id: seller10.id, client_id: client10.id])
        
        
        
        def client11 = Client.findById(27)
        def seller11 = Seller.findById(15)
        new ClientOrder(seller_id:15, client_id:27, state:OrderStates.SOLICITADO.toString(), date: CalendarDate.fromCurrentDate(-185), total_price:23.85,sellername:seller11.name,clientname:client11.name).save()
        Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), seller_id: seller11.id, client_id: client11.id])
        Agenda.executeUpdate(setVisitedDate, [ visitedDate: CalendarDate.fromCurrentDate(-185), seller_id: seller11.id, client_id: client11.id])
        
    }
}
