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
    
    def searchstateorder() {
    
        def orders = ClientOrder.list(sort:"date", order:"desc")
        
        def ordersres=[]
        def clients=[]
        def sellers=[]
        def days=[]
        def res=0
        
        orders.each { ord ->
                    if( ord.state.toLowerCase().contains(params.orderstate.toLowerCase()) ) {
                        def seller = Seller.findById(ord.seller_id)
                        def client = Client.findById(ord.client_id)
                        res=res+1
                        String day = getDay(ord.date)
                        clients.add(client);
                        sellers.add(seller);
                        days.add(day)
                        ordersres.add(ord);
                    }
        };
        
        [orders:ordersres,sellers:sellers,clients:clients,days:days,res:res]
    }
    
    def searchnameorder() {
    
        def orders = ClientOrder.list(sort:"date", order:"desc")
        
        def ordersres=[]
        def clients=[]
        def sellers=[]
        def days=[]
        def res=0
        
        orders.each { ord ->
                def seller = Seller.findById(ord.seller_id)
                if(seller!=null) {
                    if( seller.name.toLowerCase().contains(params.nameseller.toLowerCase()) ) {
                        def client = Client.findById(ord.client_id)
                        res=res+1
                        String day = getDay(ord.date)
                        clients.add(client);
                        sellers.add(seller);
                        days.add(day)
                        ordersres.add(ord);
                    }
                }
        };
        
        [orders:ordersres,sellers:sellers,clients:clients,days:days,res:res]
    }
    
    def searchdateorder() {
        def orders = ClientOrder.list(sort:"date", order:"desc")
        def res=0
        def ordersres=[]
        def clients=[]
        def sellers=[]
        def days=[]
        
        long dateInit=getDate(params.yearinit.toInteger(),params.monthinit.toInteger()-1,params.dayinit.toInteger(),0,0)
        long dateEnd=getDate(params.yearend.toInteger(),params.monthend.toInteger()-1,params.dayend.toInteger(),23,59)
        
        orders.each { ord ->
                if(containDate(dateInit,dateEnd,ord.date)){
                    def client = Client.findById(ord.client_id)
                    def seller = Seller.findById(ord.seller_id)
                    String day = getDay(ord.date)
                    clients.add(client);
                    sellers.add(seller);
                    days.add(day)
                    ordersres.add(ord)
                    res=res+1
                }
        };
        
        [orders:ordersres,sellers:sellers,clients:clients,days:days,res:res]
    }
    
    private boolean containDate(long init,long end, long date){

        if(date <= end && date >=init) {
            return true
        }
        return false

    }
    
    private long getDate(int year,int monthint,int day, int hour, int min) {
    
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.setFirstDayOfWeek(calendar.SUNDAY)
        
        long date= this.generateTimeInMs(calendar, year, monthint, day, hour, min)
        return date

    }
    
    private long generateTimeInMs(Calendar calendar, int year, int month, int day, int hour, int min) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DATE, day)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, min)
        calendar.set(Calendar.SECOND, 0)
        //println calendar.time 
        
        return calendar.getTimeInMillis()
    }
    
    def searchnameclientorder() {
        def orders = ClientOrder.list(sort:"date", order:"desc")
        def res=0
        def ordersres=[]
        def clients=[]
        def sellers=[]
        def days=[]
        
        orders.each { ord ->
                def client = Client.findById(ord.client_id)
                if(client!=null) {
                    if( client.name.toLowerCase().contains(params.nameclient.toLowerCase()) ) {
                        def seller = Seller.findById(ord.seller_id)
                        res=res+1
                        String day = getDay(ord.date)
                        clients.add(client);
                        sellers.add(seller);
                        days.add(day)
                        ordersres.add(ord);
                    }
                }
        };
        
        [orders:ordersres,sellers:sellers,clients:clients,days:days,res:res]
    }
    
    def orderdetails() {
    
        def seller = Seller.findById(params.sellerid)
        def client = Client.findById(params.clientid)
        def orders= []
        def products = []
        def brands = []

        
        def ordertotal = OrderDetail.list()
        
        ordertotal.each { ord ->
                if (ord.order_id==params.id.toInteger()) {
                        orders.add(ord);
                        def prod = Product.findById(ord.product_id)
                        products.add(prod)
                        if(prod!=null) {
                            def brand = Brand.findById(prod.brand_id)
                            brands.add(brand.name)
                        }
                        else {
                            brands.add("-")
                        }
                }
        };
        
        [seller:seller,client:client,orders:orders,products:products,brands:brands]
    
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
