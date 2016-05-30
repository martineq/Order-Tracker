package ordertracker

import ordertracker.constants.Keywords

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

            if ( getyear(sale.date) == yr ) {
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
                    
                    if ( condicion1 && condicion2 && condicion3) {
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
    
        [sellersAll:sellerNames,top10Names:top10Names,numSales:numSales,months:months]
    }
    
    def topbrands() { }
    
    
    
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
