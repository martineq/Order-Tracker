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
