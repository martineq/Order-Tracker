package ordertracker.database

import ordertracker.Agenda
import ordertracker.Client
import ordertracker.ClientOrder
import ordertracker.OrderDetail
import ordertracker.Product
import ordertracker.Seller
import ordertracker.constants.ClientStates
import ordertracker.constants.Keywords
import ordertracker.constants.OrderStates
import ordertracker.internalServices.dtos.OrderRequestDTO
import ordertracker.internalServices.dtos.ProductRequestDTO
import ordertracker.util.logger.Log

class OrderRequestLoader {

    class ReservedItem {
        public int id
        public int quantity

        ReservedItem(int id, int quantity) {
            this.id = id;
            this.quantity = quantity;
        }
    }

    private double totalAmount
    private OrderRequestDTO request
    private List<ReservedItem> reservedProducts

    OrderRequestLoader(OrderRequestDTO orderRequestDTO) {
        this.request = orderRequestDTO
        this.reservedProducts = new ArrayList<>()
        this.totalAmount = 0
    }

    private long startOfTheWeek(long currentDate) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.setTimeInMillis(currentDate)
        calendar.setFirstDayOfWeek(Calendar.SUNDAY)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar.getTimeInMillis()
    }

    private long endOfTheWeek(long currentDate) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.setTimeInMillis(currentDate)
        calendar.setFirstDayOfWeek(Calendar.SUNDAY)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        calendar.add(Calendar.DATE, +7)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar.getTimeInMillis()
    }

    private def validateTime(Agenda agenda) {
        def startOfTheWeek = this.startOfTheWeek(agenda.getDate())
        def currentTime = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString())).getTimeInMillis()
        def endOfTheWeek = this.endOfTheWeek(agenda.getDate())

        if ( currentTime < startOfTheWeek || currentTime >= endOfTheWeek )
            throw new DatabaseException( "Could not save such an old order request" )
    }

    private def validateOrderClient(long seller_id) {

        if ( Seller.findById(seller_id) == null )
            throw new DatabaseException("Seller not found")

        if ( Client.findById(request.getClient_id()) == null )
            throw new DatabaseException("Client not found")

        def agenda = Agenda.findBySeller_idAndClient_idAndDate(seller_id, request.getClient_id(), request.getDate() )

        if ( agenda == null )
            throw new DatabaseException("Date for that client and seller not available")

        if ( ClientOrder.findBySeller_idAndClient_idAndDate(seller_id, request.getClient_id(), request.getDate()) != null )
            throw new DatabaseException("Order has already been taken")

        this.validateTime(agenda)
    }

    private def returnProducts() {
        this.reservedProducts.each { reservedItem ->

            try {
                Product.findById(reservedItem.id).stock += reservedItem.quantity
            }

            catch( NullPointerException e ) {
                Log.info("Product item couldn't be returned: " + reservedItem.id.toString())
            }
        }
    }

    private def validateProductsList() {
        try {
            request.getProductRequests().each { productRequest -> this.reserveProductItem(productRequest) }
        }

        catch ( DatabaseException databaseException) {
            this.returnProducts()
            throw databaseException
        }
    }

    private def reserveProductItem(ProductRequestDTO productRequest) {
        try {
            Product product = Product.findById(productRequest.getProduct_id())
            product.stock -= productRequest.quantity
            reservedProducts.add(new ReservedItem(productRequest.product_id, productRequest.quantity))

            if ( product.validate() == true ) {
                this.totalAmount += product.getPrice() * productRequest.quantity
                product.save(flush: true)
            }

            else throw new OutOfStockException("Product id: "+ productRequest.getProduct_id().toString() + " out of stock")
        }

        catch( NullPointerException e) {
            throw new ProductException("Product id: "+ productRequest.getProduct_id().toString() + " do not exist in database")
        }
    }

    private def persistInformation(long seller_id) {

        def clientOrder = new ClientOrder(seller_id: seller_id, client_id: request.getClient_id(), state: OrderStates.SOLICITADO.toString(), date: request.getDate(), total_price: this.totalAmount )

        if ( clientOrder.validate() == false )
            new DatabaseException("Invalid json parameter")

        else {
            clientOrder.save()
            request.getProductRequests().each { productRequest -> this.saveProductPurchase(clientOrder.client_id, productRequest ) }
        }
    }

    private def saveProductPurchase(int clientOrderID, ProductRequestDTO product){
        def orderDetail = new OrderDetail(order_id: clientOrderID, product_id: product.getProduct_id(), requested_items: product.getQuantity() )
        orderDetail.save()
    }

    public def loadRequest(long seller_id) throws DatabaseException {
        this.validateOrderClient(seller_id)
        this.validateProductsList()

        if ( this.reservedProducts.size() > 0 )
            this.persistInformation(seller_id)

        else
            throw new DatabaseException("Empty items purchase")
    }
}
