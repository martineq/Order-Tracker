package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.constants.Keywords
import ordertracker.constants.OrderStates

class OrderController {

    def orderRequestService

    def index() {
        def orders = ClientOrder.list(sort:"date", order:"desc")

        def days=[]
        def res=0
        String stat=""
        String seller=""
        String client=""
        def ordersres=[]
        boolean containDat=true
        boolean searchDate=false
        long dateInit
        long dateEnd
        
        if (params.orderstate != null ) {
            if(params.orderstate.toLowerCase().contains("todos") != true){
                stat=params.orderstate.toLowerCase()
            }
        }
        if (params.nameseller != null ) {
            if(params.nameseller.length() != 0 ){
                seller=params.nameseller.toLowerCase()
            }
        }
        if (params.nameclient != null ) {
            if(params.nameclient.length() != 0 ){
                client=params.nameclient.toLowerCase() 
            }
        }
        if (params.yearinit != null && params.yearend != null) {
         if(params.yearinit.length() != 0  && params.yearend.length() != 0 ){
                dateInit=getDate(params.yearinit.toInteger(),params.monthinit.toInteger()-1,params.dayinit.toInteger(),0,0)
                dateEnd=getDate(params.yearend.toInteger(),params.monthend.toInteger()-1,params.dayend.toInteger(),23,59)
                containDat=false
                searchDate=true
            }
        }
        
        orders.each { ord ->
                boolean searchstate=ord.state.toLowerCase().contains(stat)
                boolean searchseller=ord.sellername.toLowerCase().contains(seller)
                boolean searchclient=ord.clientname.toLowerCase().contains(client) 
                if (searchDate) {
                    containDat = containDate(dateInit,dateEnd,ord.date)
                }
                if( searchstate && searchseller && searchclient && containDat) {
                    res=res+1
                    String day = getDay(ord.date)
                    days.add(day)
                    ordersres.add(ord);
                }
        };
        
        [orders:ordersres,days:days,res:res]
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
    

    
    def orderdetails() {
    
        def brands = []
        def orders = []
        
        def ordertotal = OrderDetail.list()
        
        ordertotal.each { ord ->
                if (ord.order_id==params.id.toInteger()) {
                        orders.add(ord);
                }
        };
        
        [orders:orders]
    
    }
    
    def changeorderstate() {
        [state1: OrderStates.SOLICITADO.toString(),state2: OrderStates.CANCELADO.toString(), state3: OrderStates.DESPACHADO.toString()]
    }
    
    def statechanged() {
    
        def order = ClientOrder.get(params.id)
        
        order.state=params.orderstate
        
        order.save(failOnError: true)

        [order:order,day:params.day]
    
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
