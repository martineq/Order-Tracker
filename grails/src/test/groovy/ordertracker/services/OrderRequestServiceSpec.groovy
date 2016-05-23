package ordertracker.services

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import ordertracker.Agenda
import ordertracker.Brand
import ordertracker.BrandLoader
import ordertracker.Client
import ordertracker.ClientLoader
import ordertracker.ClientOrder
import ordertracker.Discount
import ordertracker.Distribution
import ordertracker.OrderDetail
import ordertracker.OrderRequestService
import ordertracker.Product
import ordertracker.ProductLoader
import ordertracker.Push_message
import ordertracker.Seller
import ordertracker.SellerLoader
import ordertracker.User
import ordertracker.UserLoader
import ordertracker.UserType
import ordertracker.UserTypeLoader
import ordertracker.constants.ClientStates
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.constants.OrderStates
import ordertracker.notifications.EmptyStockNotification
import ordertracker.queries.QueryException
import ordertracker.queries.Requester
import ordertracker.tranmission.DefaultTransmission
import ordertracker.util.CalendarDate
import spock.lang.Specification

@Mock( [User, Product, Client, Seller, ClientOrder, OrderDetail, UserType, Agenda, Brand, Push_message, Discount, Distribution] )
@TestFor(OrderRequestService)
class OrderRequestServiceSpec extends Specification {

    void setup() {
        new UserLoader().load()
        new UserTypeLoader().load()
        new ProductLoader().load()
        new SellerLoader().load()
        new ClientLoader().load()
        new BrandLoader().load()
    }

    private Requester generateMartinRequester(Enum httpMethod) {
        Requester requester = new Requester()
        requester.addProperty(HttpProtocol.METHOD, httpMethod)
        requester.addProperty(Keywords.USERNAME, 'martin')
        requester.addProperty(Keywords.TOKEN, 'token1')
        return requester
    }

    void "test emptyBodyRequest"() {
        given:
            Requester requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, "")

        when:
            String exceptionMessage = "";
            try {
                new OrderRequestService().validate(requester)
            }
            catch( QueryException e ) {
                exceptionMessage = e.getMessage();
            }

        then:
            exceptionMessage == "No se recibió un objeto json válido"
    }

    void "test noBody"() {
        given:
            def requester = this.generateMartinRequester(HttpProtocol.POST)

        when:
            String message = "";
            try {
                new OrderRequestService().validate(requester)
            }
            catch( QueryException e ) {
                message = e.getMessage();
            }

        then:
             message == "No se recibió un objeto json válido"
    }

    void "test validOrderRequest"() {
        given:
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
            Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
            agenda.save()

            def lines = '[ { order: 1, product: 1, quantity: 1, price:100} ]'
            def body = '{visit_id: '+agenda.id+', client: 1, fecha: '+ calendar.getTimeInMillis() +', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal: 0.0, vendedor: 1 , lines: '+lines+'}'

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        when:
            orderRequesterService.validate(requester)
            orderRequesterService.generateQuery()
            String response = orderRequesterService.obtainResponse().build()

        then:
            response == '{"status":{"result":"ok","description":"Pedido aceptado"}}'
    }

    void "test validTime"() {
        given:
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
            Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
            agenda.save()

            def lines = '[ { order: 1, product: 1, quantity: 1, price:100} ]'
            def body = '{visit_id: '+agenda.id+',client: 1, fecha: '+ calendar.getTimeInMillis() +', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal: 0.0, vendedor: 1 , lines: '+lines+'}'

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        when:
            orderRequesterService.validate(requester)
            orderRequesterService.generateQuery()

            def clientOrder = ClientOrder.findById(1)

        then:
            clientOrder.getDate() == calendar.getTimeInMillis()
    }

    void "test stockDecreased"() {
        given:
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
            Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
            agenda.save()

            def quantity = 3
            def product_id = 4
            def lines = '[ { order: 1, product: '+product_id+', quantity: '+quantity.toString()+', price:100} ]'
            def body = '{visit_id: '+agenda.id+',client: 1, fecha: '+ calendar.getTimeInMillis() +', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal: 0.0, vendedor: 1 , lines: '+lines+'}'
            def product = Product.findById(product_id)
            def originalStock = product.stock

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        when:
            orderRequesterService.validate(requester)
            orderRequesterService.generateQuery()

        then:
            product.stock == originalStock - quantity
    }

    void "test excesiveAmountOfProducts"() {
        given:
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
            Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
            agenda.save()

            def quantity = 25
            def lines = '[ { order: 1, product: 1, quantity: '+quantity.toString()+', price:100} ]'
            def body = '{visit_id: ' + agenda.id + ',client: 1, fecha: '+ calendar.getTimeInMillis() +', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal: 0.0, vendedor: 1 , lines: '+lines+'}'
            def product = Product.findById(1)
            def originalStock = product.stock

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        when:
            orderRequesterService.validate(requester)
            orderRequesterService.generateQuery()

        then:
            product.stock == originalStock
            def result = '{"status":{"result":"error","description":"No se aceptó la solicitud del pedido: producto mesa solicitado supera el stock [ 20 items ]"}}'
            result == orderRequesterService.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build()
    }

    void "test invalidClient"() {
        given:
            def lines = '[ { order: 1, product: 1, quantity: 1, price:100} ]'
            String time = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString())).getTimeInMillis()
            def body = '{visit_id: 0, client: 1000, fecha: '+ time +', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal: 0.0, vendedor: 1 , lines: '+lines+'}'

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        when:
            orderRequesterService.validate(requester)
            orderRequesterService.generateQuery()
            String result = orderRequesterService.obtainResponse(DefaultTransmission.newInstance()).build()

        then:
            result == "{\"status\":{\"result\":\"error\",\"description\":\"No se aceptó la solicitud del pedido: Client not found\"}}"
    }

    void "test invalidProduct"() {
        given:
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
        agenda.save()

        def lines = '[ { order: 1, product: 1000, quantity: 1, price:100} ]'
        String time = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString())).getTimeInMillis()
        def body = '{visit_id: ' + agenda.id + ', client: 1, fecha: '+ calendar.getTimeInMillis() +', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal: 0.0, vendedor: 1 , lines: '+lines+'}'

        and:
        def requester = this.generateMartinRequester(HttpProtocol.POST)
        requester.addProperty(HttpProtocol.BODY, body)

        and:
        def orderRequesterService = new OrderRequestService()

        when:
        orderRequesterService.validate(requester)
        orderRequesterService.generateQuery()
        String result = orderRequesterService.obtainResponse(DefaultTransmission.newInstance()).build()

        then:
        result == "{\"status\":{\"result\":\"error\",\"description\":\"No se aceptó la solicitud del pedido: Product id: 1000 do not exist in database\"}}"
    }

    void "test invalidTime"() {
        given:
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
            calendar.add(Calendar.DATE, -7)
            Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
            agenda.save()

            def lines = '[ { order: 1, product: 1, quantity: 1, price:100} ]'
            def body = '{visit_id: ' + agenda.id + ', client: 1, fecha: 0, estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal: 0.0, vendedor: 1 , lines: '+lines+'}'

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        when:
            orderRequesterService.validate(requester)
            orderRequesterService.generateQuery()
            String result = orderRequesterService.obtainResponse(DefaultTransmission.newInstance()).build()

        then:
            result == "{\"status\":{\"result\":\"error\",\"description\":\"No se aceptó la solicitud del pedido: No se puede solicitar una orden fuera de la semana de trabajo\"}}"
    }

    void "test aWeekAgoDate"() {
        given:
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.add(Calendar.DATE, -7)
        Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
        agenda.save()

        def lines = '[ { order: 1, product: 1, quantity: 1, price:100} ]'
        def body = '{visit_id: ' + agenda.id + ',client: 1, fecha: '+ calendar.getTimeInMillis()+', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal: 0.0, vendedor: 1 , lines: '+lines+'}'

        and:
        def requester = this.generateMartinRequester(HttpProtocol.POST)
        requester.addProperty(HttpProtocol.BODY, body)

        and:
        def orderRequesterService = new OrderRequestService()

        when:
        orderRequesterService.validate(requester)
        orderRequesterService.generateQuery()
        String result = orderRequesterService.obtainResponse(DefaultTransmission.newInstance()).build()

        then:
        result == '{"status":{"result":"error","description":"No se aceptó la solicitud del pedido: No se puede solicitar una orden fuera de la semana de trabajo"}}'
    }

    void "test validDate"() {
        given:
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
        agenda.save()

        def lines = '[ { order: 1, product: 1, quantity: 1, price:100} ]'
        def body = '{visit_id: ' + agenda.id + ',client: 1, fecha: '+ calendar.getTimeInMillis()+', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal: 0.0, vendedor: 1 , lines: '+lines+'}'

        and:
        def requester = this.generateMartinRequester(HttpProtocol.POST)
        requester.addProperty(HttpProtocol.BODY, body)

        and:
        def orderRequesterService = new OrderRequestService()

        when:
        orderRequesterService.validate(requester)
        orderRequesterService.generateQuery()
        String result = orderRequesterService.obtainResponse(DefaultTransmission.newInstance()).build()

        then:
        result == '{"status":{"result":"ok","description":"Pedido aceptado"}}'
    }

    void "test productNotBuyed"() {
        given:
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
            Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
            agenda.save()

            def quantity = 25
            def lines = '[ { order: 1, product: 1, quantity: '+quantity.toString()+', price:100} ]'
            String time = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString())).getTimeInMillis()
            def body = '{visit_id: ' + agenda.id + ',client: 1, fecha: '+ calendar.getTimeInMillis() +', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal: 0.0, vendedor: 1 , lines: '+lines+'}'

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        when:
            orderRequesterService.validate(requester)
            orderRequesterService.generateQuery()

        then:
            def message = orderRequesterService.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build()
            message == '{"status":{"result":"error","description":"No se aceptó la solicitud del pedido: producto mesa solicitado supera el stock [ 20 items ]"}}'
    }

    void "test validStock"() {
        given:
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
            Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
            agenda.save()

            def product = Product.findById(1)
            def quantity = 15
            def lines = '[ { order: 1, product: 1, quantity: '+quantity.toString()+', price:100} ]'
            def body = '{visit_id: ' + agenda.id + ',client: 1, fecha: '+ calendar.getTimeInMillis() +', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal:' + quantity * product.price + ', vendedor: 1 , lines: '+lines+'}'

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        when:
            orderRequesterService.validate(requester)
            orderRequesterService.generateQuery()
            def order = ClientOrder.findById(1)

        then:
            order.total_price == quantity * product.price
    }

    void "test twoProductsInStock"() {
        given:
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
            Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
            agenda.save()

            def quantity = 15
            def product1 = Product.findById(1)
            def product2 = Product.findById(2)
            def total = quantity * product1.price + quantity * product2.price

            def lines = '[ { order: 1, product: 1, quantity: '+quantity.toString()+', price:100}, { order: 1, product: 2, quantity: '+quantity.toString()+', price:10} ]'
            def body = '{visit_id: ' + agenda.id + ',client: 1, fecha: '+ calendar.getTimeInMillis() +', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal: ' + total +', vendedor: 1 , lines: '+lines+'}'

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        when:
            orderRequesterService.validate(requester)
            orderRequesterService.generateQuery()
            def order = ClientOrder.findById(1)

        then:
            order.total_price == quantity * ( product1.price + product2.price )
    }

    void "test validateAssignedState"() {
        given:
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
        agenda.save()

        def quantity = 15
        def lines = '[ { order: 1, product: 1, quantity: '+quantity.toString()+', price:100}, { order: 1, product: 2, quantity: '+quantity.toString()+', price:10} ]'
        def body = '{visit_id: ' + agenda.id + ',client: 1, fecha: '+ calendar.getTimeInMillis() +', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal: 0.0, vendedor: 1 , lines: '+lines+'}'

        and:
        def requester = this.generateMartinRequester(HttpProtocol.POST)
        requester.addProperty(HttpProtocol.BODY, body)

        and:
        def orderRequesterService = new OrderRequestService()

        when:
        orderRequesterService.validate(requester)
        orderRequesterService.generateQuery()
        def order = ClientOrder.findById(1)

        then:
        order.state == OrderStates.SOLICITADO.toString()
    }

    void "test orderAlreadyHasBeenRequested"() {
        given:
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
            Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
            agenda.save()

            def quantity = 2
            def lines = '[ {order: 1, product: 6, quantity: '+quantity.toString()+', price:100}]'
            def body = '{visit_id: ' + agenda.id + ',client: 1, fecha: '+ calendar.getTimeInMillis() +', estado: "'+ ClientStates.NO_VISITADO.toString() + '", importeTotal: 0.0, vendedor: 1 , lines: '+lines+'}'

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        and:
            orderRequesterService.validate(requester)
            orderRequesterService.generateQuery()

        when:
            def requester2 = this.generateMartinRequester(HttpProtocol.POST)
            requester2.addProperty(HttpProtocol.BODY, body)
            def orderRequestService2 = new OrderRequestService()
            orderRequestService2.validate(requester)
            orderRequestService2.generateQuery()
            String message = orderRequestService2.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build()

        then:
            message == '{"status":{"result":"error","description":"No se aceptó la solicitud del pedido: No se puede tomar la orden, el cliente ya fue visitado"}}'
    }


    void "test clientJson"() {
        given:
            long today = CalendarDate.currentDate()

            Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: today, day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
            agenda.save()
            int stock1 = Product.findById(1).getStock()
            int stock2 = Product.findById(2).getStock()

        and:
            def body = '{"lines":"[{\"order\":\"0\",\"product\":\"1\",\"quantity\":\"1\",\"price\":\"600.0\"},{\"order\":\"0\",\"product\":\"2\",\"quantity\":\"2\",\"price\":\"150.0\"}]","estado":"","client":"1","fecha":'+today+',"importeTotal":"900.0", visit_id: ' + agenda.id + '}'

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        and:
            orderRequesterService.validate(requester)
            orderRequesterService.generateQuery()

        when:
            String message = orderRequesterService.obtainResponse(DefaultTransmission.obtainDefaultTransmission()).build()

        then:
            Product.findById(1).getStock() == stock1 - 1
            Product.findById(2).getStock() == stock2 - 2
            message == '{"status":{"result":"ok","description":"Pedido aceptado"}}'
    }

    void "test emptyPurchase"() {
        given:
            Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: 1462152644190, day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
            agenda.save()

        and:
            def body = '{"lines":"[{}]","estado":"","client":"1","fecha":"1462152644190","importeTotal":"900.0", visit_id: ' + agenda.id + '}'

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        when:
            String message = ""
            try {
                orderRequesterService.validate(requester)
            }
            catch (QueryException q) {
                message = q.getMessage()
            }

        then:
            message.size() != 0
    }

    void "test validStockToEmptyStock"() {
        given:
            int pushMessages = Push_message.count
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
            Agenda agenda = new Agenda(seller_id: 1, client_id: 1, date: calendar.getTimeInMillis(), day:1, time: '00:00', state: ClientStates.PENDIENTE.toString())
            agenda.save()

            def product = Product.findById(1)
            def quantity = product.getStock()
            def lines = '[ { order: 1, product: 1, quantity: '+quantity.toString()+', price:100} ]'
            def body = '{visit_id: ' + agenda.id + ',client: 1, fecha: '+ calendar.getTimeInMillis() +', estado: "'+ ClientStates.VISITADO.toString() + '", importeTotal:' + quantity * product.price + ', vendedor: 1 , lines: '+lines+'}'

        and:
            def requester = this.generateMartinRequester(HttpProtocol.POST)
            requester.addProperty(HttpProtocol.BODY, body)

        and:
            def orderRequesterService = new OrderRequestService()

        when:
            orderRequesterService.validate(requester)
            orderRequesterService.generateQuery()
            def order = ClientOrder.findById(1)

        then:
            order.total_price == quantity * product.price
            Push_message.count == pushMessages + 1
    }
}
