package ordertracker.database

import ordertracker.Agenda
import ordertracker.ClientOrder
import ordertracker.OrderDetail
import ordertracker.Product
import ordertracker.PushService
import ordertracker.constants.ClientStates
import ordertracker.constants.OrderStates
import ordertracker.internalServices.dtos.OrderRequestDTO
import ordertracker.notifications.EmptyStockNotification

class OrderRequestLoader {

    private OrderRequestDTO request
    private String errorMessage

    OrderRequestLoader(OrderRequestDTO orderRequestDTO) {
        this.request = orderRequestDTO
        this.errorMessage = ""
    }

    public String loadRequest(long seller_id) {
        def validator = new OrderRequestValidator(this.request)
        def orderBuilder = new OrderBuilder()

        try {
            validator.validateOrder(seller_id)
            orderBuilder.reserveItems(this.request)
            long order_id = this.addOrderToDB(seller_id)
            this.addOrderItemsToDB(order_id)
            this.changeAgendaVisitState()
            return ""
        }

        catch ( DatabaseException databaseException ) {
            orderBuilder.returnProducts()
            return databaseException.getMessage()
        }
    }

    private def changeAgendaVisitState() {
        try {
            Agenda.findById(request.visit_id).setState(ClientStates.VISITADO.toString())
        } catch (NullPointerException n) {}
    }

    private def addOrderToDB(long seller_id) {
        def clientOrder = new ClientOrder(seller_id: seller_id, client_id: request.getClient_id(), state: OrderStates.SOLICITADO.toString(), date: request.getDate(), total_price: request.getTotalAmount())
        if (clientOrder.validate() == false)
            throw new DatabaseException("Invalid json parameter")

        clientOrder.save()
        return clientOrder.id
    }

    private def addOrderItemsToDB(long order_id) {
        request.getProductRequests().each {
            new OrderDetail(order_id: order_id, product_id: it.getProduct_id(), requested_items: it.getQuantity()).save()
            this.verifyEmptyStock(it.getProduct_id())
        }
    }

    private def verifyEmptyStock(long product_id) {
        try {
            def product = Product.findById(product_id)
            if (product.getStock() == 0) {
                new EmptyStockNotification(product).addNotification()
                PushService.getInstance().push()
            }
        } catch (NullPointerException e) {}
    }
}
