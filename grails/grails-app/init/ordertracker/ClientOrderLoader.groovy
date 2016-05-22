package ordertracker

import ordertracker.constants.OrderStates
import ordertracker.util.CalendarDate

class ClientOrderLoader {
    def load() {
        def client = Client.findById(1)
        def seller = Seller.findById(1)
        new ClientOrder(seller_id:1, client_id:1, state:OrderStates.SOLICITADO.toString(), date: CalendarDate.fromCurrentDate(0), total_price:8550.00,sellername:seller.name,clientname:client.name).save()
        def client2 = Client.findById(2)
        def seller2 = Seller.findById(1)
        new ClientOrder(seller_id:1, client_id:2, state:OrderStates.CANCELADO.toString(), date: CalendarDate.fromCurrentDate(1), total_price:8415.50,sellername:seller2.name,clientname:client2.name).save()
        def client3 = Client.findById(3)
        def seller3 = Seller.findById(1)
        new ClientOrder(seller_id:1, client_id:3, state:OrderStates.DESPACHADO.toString(), date:CalendarDate.fromCurrentDate(2),total_price:3157.40,sellername:seller3.name,clientname:client3.name).save()
        def client4 = Client.findById(5)
        def seller4 = Seller.findById(2)
        new ClientOrder(seller_id:2, client_id:5, state:OrderStates.DESPACHADO.toString(), date:CalendarDate.fromCurrentDate(3),total_price:1491.60,sellername:seller4.name,clientname:client4.name).save()
        
        def client5 = Client.findById(6)
        def seller5 = Seller.findById(6)
        new ClientOrder(seller_id:6, client_id:6, state:OrderStates.CANCELADO.toString(), date: CalendarDate.fromCurrentDate(1), total_price:11045.40,sellername:seller5.name,clientname:client5.name).save()

    }
}
