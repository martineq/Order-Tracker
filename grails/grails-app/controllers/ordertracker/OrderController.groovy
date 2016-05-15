package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.constants.Keywords

class OrderController {

    def orderRequestService

    def index() {
        def orders = ClientOrder.list(sort:"date", order:"desc")
        def clients=[]
        def sellers=[]
        def days=[]
        
        orders.each { ord ->
                def client = Client.findById(ord.client_id)
                def seller = Seller.findById(ord.seller_id)
                String day = getDay(ord.date)
                clients.add(client);
                sellers.add(seller);
                days.add(day)
        };
        
        [orders:orders,sellers:sellers,clients:clients,days:days]
    }
    
    def orderdetails() {
    
        def seller = Seller.findById(params.sellerid)
        def client = Client.findById(params.clientid)
        
        
        def orders = OrderDetail.list()
        
        println(orders)
        
        [seller:seller,client:client,orders:orders]
    
    }
    
    def help() { }

    def qr() {
        response << new QueryFacade(new QRService()).solve(request)
    }

    def request() {
        response << new QueryFacade(orderRequestService).solve(request)
    }

    def historical() {
        response << new QueryFacade(new HistoricalOrdersService()).solve(request)
    }
    
    private String getDay(long time ){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1; 
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        
        String cad=mDay+"/"+mMonth+"/"+mYear
        
        return cad
    }
    
    
}
