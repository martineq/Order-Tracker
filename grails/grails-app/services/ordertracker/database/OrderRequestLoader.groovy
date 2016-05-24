package ordertracker.database

import ordertracker.Agenda
import ordertracker.Brand
import ordertracker.Client
import ordertracker.ClientOrder
import ordertracker.GCMConnectorService
import ordertracker.OrderDetail
import ordertracker.Product
import ordertracker.PushService
import ordertracker.Seller
import ordertracker.constants.ClientStates
import ordertracker.constants.OrderStates
import ordertracker.internalServices.dtos.OrderRequestDTO
import ordertracker.notifications.EmptyStockNotification
import ordertracker.util.logger.Log

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
            String setState = "UPDATE Agenda a SET a.state = :state WHERE a.id = :id"
            String setVisitedDate = "UPDATE Agenda a SET a.visitedDate = :visitedDate WHERE a.id = :id "

            Agenda.executeUpdate(setState, [ state: ClientStates.VISITADO.toString(), id: request.getVisit_id()])
            Agenda.executeUpdate(setVisitedDate, [ visitedDate: request.getDate(), id: request.getVisit_id()])


            Log.info(Client.findById(request.client_id).name + " fue visitado el " + new Date(request.getDate()).getDateTimeString())
        }

        catch (NullPointerException n) {}
    }

    private def addOrderToDB(long seller_id) {
        try {
            def sellerName = Seller.findById(seller_id).name
            def clientName = Client.findById(request.getClient_id()).name

            def clientOrder = new ClientOrder(seller_id: seller_id, client_id: request.getClient_id(), state: OrderStates.SOLICITADO.toString(), date: request.getDate(), total_price: request.getTotalAmount(), sellername: sellerName, clientname: clientName)

            if (clientOrder.validate() == false)
                throw new DatabaseException("Invalid json parameter")

            clientOrder.save()
            return clientOrder.id
        }

        catch ( NullPointerException e) {
            throw new DatabaseException("Inconsistent internal database information")
        }
    }

    private def addOrderItemsToDB(long order_id) {
        try {
            request.getProductRequests().each {
                def product = Product.findById(it.getProduct_id())
                def brand = Brand.findById(product.getBrand_id())

                new OrderDetail(order_id: order_id, product_id: it.getProduct_id(), requested_items: it.getQuantity(), productname: product.getName(), brand: brand.getName(), characteristic: product.getCharacteristic(), category: product.getCategory(), price: product.getPrice()).save()
                this.verifyEmptyStock(it.getProduct_id())
            }
        }

        catch (NullPointerException e ) {
            throw new DatabaseException("Inconsistent internal exception")
        }
    }

    private def verifyEmptyStock(long product_id) {
        try {
            def product = Product.findById(product_id)
            if (product.getStock() == 0) {
                new EmptyStockNotification(product).addNotification()
                GCMConnectorService.getInstance().push()
            }
        } catch (NullPointerException e) {}
    }
}
