package ordertracker

class ClientOrderLoader {
    def load() {
        new ClientOrder(seller_id:1, client_id:1, state:'pendiente', total_price:252.25).save()
        new ClientOrder(seller_id:1, client_id:2, state:'completa', total_price:465.00).save()
        new ClientOrder(seller_id:2, client_id:3, state:'cancelada', total_price:45.70).save()
    }
}
