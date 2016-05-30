package ordertracker

import ordertracker.constants.Keywords
import ordertracker.constants.OrderStates

class GraphicsController {

    Calendar calendar
    int year
    int month
    
    public GraphicsController() {
    
        calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.setFirstDayOfWeek(calendar.SUNDAY)

        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
    }
    
    def annualsales() { 
    
        int yr=year
        
        def res=[0,0,0,0,0,0,0,0,0,0,0,0]
        if(params.year!=null){
                yr=params.year.toInteger()
        }
        
        def sales= ClientOrder.list()
        
        sales.each { sale ->
            def condicion=!( sale.state.toLowerCase().equals(OrderStates.CANCELADO.toString().toLowerCase() ) ) ;
            if ( getyear(sale.date) == yr && condicion ) {
                res[getmonth(sale.date)]+=sale.total_price;
            }        
        };
        
        [res:res,year:yr]
        
            
    }
    
    def topsellers() {
    
        def sellersAll = Seller.list(sort:"name", order:"des")
        def sellerNames = []
        def months = ["","1","2","3","4","5","6","7","8","9","10","11","12"]
        
        def top10Names = [null,null,null,null,null,null,null,null,null,null]
        def numSales = [0,0,0,0,0,0,0,0,0,0]

        def allNames = []
        def allSales = []
        def sortedSalesInverse =[]
        
        int i=-1;
        String lastname=""
        
        
        def orders = ClientOrder.list(sort:"sellername", order:"desc")

        orders.each { order ->
                    boolean condicion1 = ( ( params.seller==null ) || ( params.seller.equals("") ) || 
                    ( params.seller.toLowerCase().equals(order.sellername.toLowerCase()) ) ) ;
                    boolean condicion2=( ( params.month==null ) || ( params.month.equals("") ) || ( params.month.toInteger()==(getmonth(order.date)+1) ) );
                    boolean condicion3= ( ( params.year==null ) || ( params.year.equals("") ) || ( ( params.year.toInteger() == getyear(order.date) ) ) );
                    boolean condicion4= !( order.state.toLowerCase().equals(OrderStates.CANCELADO.toString().toLowerCase() ) ) ;
                    
                    if ( condicion1 && condicion2 && condicion3 && condicion4) {
                        if (! lastname.equals(order.sellername)) {
                            i=i+1;
                            allNames[i]=order.sellername;
                            allSales[i]=0
                            lastname=order.sellername;
                        }
                        allSales[i]=allSales[i]+1
                        sortedSalesInverse[i]=allSales[i]
                    }
        }
        
        sortedSalesInverse = sortedSalesInverse.sort()
        def sortedSales = []
        
        for (int ind=sortedSalesInverse.size()-1; ind >= 0; ind--) {
            sortedSales.push(sortedSalesInverse[ind])
        }
        
        int j=0
        sortedSales.each { sale->
                    int aux=sale
                    numSales[j]=sale
                    int m=0
                    allSales.each { sal2 ->
                        if(sal2==aux) {
                            top10Names[j]=allNames[m]
                            allSales[m]=-1
                            aux=-2
                        }
                        m=m+1
                    }
                    j++;
                    
        }
        
        sellersAll.each { seller ->
                    sellerNames.add(seller.name);
        }
        sellerNames.add(0,"")

        def sellerCount = 0
        top10Names.each { if ( it != null ) sellerCount++ }
        def value = ( sellerCount / Seller.count ) * 100
        def topSellersPercentage = value.toInteger()

        [sellersAll:sellerNames,top10Names:top10Names,numSales:numSales,months:months, topSellersPercentage: topSellersPercentage]
    }
    
    def topbrands() {
    
    
        def top20Brands = [null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null]
        def numSales = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        def months = ["","1","2","3","4","5","6","7","8","9","10","11","12"]
        def allNames=[]
        def allSales=[]
        def sortedSalesInverse=[]
        
        def orders = OrderDetail.list(sort:"brand", order:"desc")
        String lastname=""
        
        int i=-1;
        
        orders.each { order ->
                        def general= ClientOrder.findById(order.order_id)
                        
                        
                        boolean condicion1=( ( params.month==null ) || ( params.month.equals("") ) || ( params.month.toInteger()==(getmonth(general.date)+1) ) );
                        boolean condicion2= ( ( params.year==null ) || ( params.year.equals("") ) || ( ( params.year.toInteger() == getyear(general.date) ) ) );
                        boolean condicion3= !( general.state.toLowerCase().equals(OrderStates.CANCELADO.toString().toLowerCase()) ) ;
                    
                        if(condicion1 && condicion2 && condicion3) {
                            if (! lastname.equals(order.brand)) {
                                i=i+1;
                                allNames[i]=order.brand;
                                allSales[i]=0
                                lastname=order.brand;
                            }
                            allSales[i]=allSales[i]+ order.requested_items
                            sortedSalesInverse[i]=allSales[i]
                        }
        }
        sortedSalesInverse = sortedSalesInverse.sort()
        def sortedSales = []
        
        for (int ind=sortedSalesInverse.size()-1; ind >= 0; ind--) {
            sortedSales.push(sortedSalesInverse[ind])
        }
        
        int j=0
        sortedSales.each { sale->
                    int aux=sale
                    numSales[j]=sale
                    int m=0
                    allSales.each { sal2 ->
                        if(sal2==aux) {
                            top20Brands[j]=allNames[m]
                            allSales[m]=-1
                            aux=-2
                        }
                        m=m+1
                    }
                    j++;
                    
        }
        
        
        [top20Brands:top20Brands,numSales:numSales,months:months]
    }
    
    
    
    private int getmonth(long time ){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        
        int mMonth = c.get(Calendar.MONTH); 
        
        return mMonth;
    }
    
    private int getyear(long time ){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        
        int mYear = c.get(Calendar.YEAR);
        
        return mYear;
    }
}
