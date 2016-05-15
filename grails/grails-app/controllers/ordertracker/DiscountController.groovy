package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.constants.ClientStates

import ordertracker.constants.Keywords


class DiscountController {

    def index() {
        def discounts = Discount.list()
        def brands = []
        def products = []
        def initDiscount = []
        def endDiscount = []
        def descripcion = []

        discounts.each { disc ->
            def discc=disc;
            def brand=Brand.get(discc.brand_id)
            def product = Product.get(discc.product_id)
            String initDisc=getDay(disc.datebeg)
            String endDisc=getDay(disc.dateend)
            products.add(product)
            brands.add(brand)
            initDiscount.add(initDisc)
            endDiscount.add(endDisc)
        };
        
        [discounts:discounts,products:products,brands:brands,initDiscount:initDiscount,endDiscount:endDiscount,descripcion:descripcion]
    }
    
    private String getDay(long time ){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1; 
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        
        String cad=mDay+"/"+mMonth
        
        return cad
    }
            
    def deleteconfirm() {
    }
    
    def editdiscount() {
    }
    
    def upbrand() {
    }
    
    def upproduct() {
    
            def products = Product.list(sort:"name", order:"des")
            def brands = []
            
            products.each { prod ->
                def discc=prod;
                def brand=Brand.get(discc.brand_id)
                brands.add(brand)
            };
        
            [products:products,brands:brands]
    }
    
    def selectproduct() {
        String c=params.range
        
        int numrange=c.toInteger()
        [numrange:numrange]
    
    }
    
    def newdiscount() {
    
    }

     
     def upentryproduct() {
        
        int desc1=1
        int desc2=1
        int desc3=1
        int descfinal=1
        
        int ran2=1
        int ran3=1
        
        if (params.desc1!=null) {
            desc1=params.desc1.toInteger()
        }
        if (params.desc2!=null) {
            desc2=params.desc2.toInteger()
        }
        if (params.desc3!=null) {
            desc3=params.desc3.toInteger()
        }
        if (params.descfinal!=null) {
            descfinal=params.descfinal.toInteger()
        }

        
        if (params.ran2!=null) {
            ran2=params.ran2.toInteger()
        }
        if (params.ran3!=null) {
            ran3=params.ran3.toInteger()
        }
        

        long datebeg=getDate(params.monthinit,params.dayinit.toInteger())
        long dateend=getDate(params.monthend,params.dayend.toInteger())
        
        //¿Son validos los valores de descuentos?
        boolean validDiscounts=valuesValidDiscNumber(params.range.toInteger(),desc1,desc2,desc3,descfinal)
        //¿Son validos los valores de rangos?
        boolean validRanges=valuesValidRanges(params.range.toInteger(), ran2,ran3)
        //¿Son validos los valores de fechas?
        boolean validDates=valuesValidDates(datebeg,dateend)
        //Buscar si existe algun descuento que se superpone con el que se está por crear
        boolean discountOverlap=findOverlapProducDiscount(params.productid.toInteger(),datebeg,dateend)
        
        [validRanges:validRanges,validDiscounts:validDiscounts,validDates:validDates,discountOverlap:discountOverlap]

     }
     
     
         
    private boolean overlapDates(long i1,long j1,long i2,long j2){

        if (i1>i2 && j1>j2 && j2>i1) {
                return true;
        }
        if(i2>i1 && j2<j1) {
                return true;
        }
        if(i2>i1 && j2>j1 && i2<j1) {
                return true;
        }
        if(i2<i1 && j2>j1) {
                return true;
        }
        return false;
    }
         
    private boolean findOverlapProducDiscount(int productid,long datebeg, long dateend){
        def product = Product.get(productid)
        def discounts = Discount.list()
        def res=false;
        
        println(product.category);
        
        println(product.brand_id);
        
        discounts.each { disc ->
                
                //Si existe un descuento para este producto en fechas que se superponen, hay overlap
                if (disc.product_id==productid) {
                        if( overlapDates(disc.datebeg,disc.dateend,datebeg,dateend)==true ){
                            res=true;
                            return res;
                        }
                }
                //Si existe un descuento para la marca de este producto
                //en la misma categoria de este producto (o sin categoria) y
                //en fechas que se superponen, hay overlap     
                if (disc.brand_id==product.brand_id && (( disc.category.equals(product.category) ) || (disc.category.equals("none")) ) && disc.product_id==-1) {

                        if(overlapDates(disc.datebeg,disc.dateend,datebeg,dateend)){
                            res=true;
                            return res;
                        }
                }
        };
            
        return res;
    }
    
    private boolean valuesValidDates(long datebeg,long dateend) {
        if(dateend<=datebeg){
            return false;
        }
        
        return true;
    }
    
    private getDate(String month,int day) {
    
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.setFirstDayOfWeek(calendar.SUNDAY)
        int year = calendar.get(Calendar.YEAR)
        
        int monthint=getMonth(month);
        
        long date= this.generateTimeInMs(calendar, year, monthint, day)
        

    }
    
    int getMonth(String month){
            if(month=='Enero') return 0;
            if(month=='Febrero') return 1;
            if(month=='Marzo') return 2;
            if(month=='Abril') return 3;
            if(month=='Mayo') return 4;
            if(month=='Junio') return 5;
            if(month=='Julio') return 6;
            if(month=='Agosto') return 7;
            if(month=='Septiembre') return 8;
            if(month=='Octubre') return 9;
            if(month=='Noviembre') return 10;
            if(month=='Diciembre') return 11;
            println("Error calculando calendario!!")
            return -1;
    }
    
    
    
    private boolean valuesValidDiscNumber(int numrange , int desc1, int desc2, int desc3, int descfinal) {
     
        //El % del descuento no puede ser menor que 0
        if(desc1<0 || desc2<0 || desc3<0 || descfinal<0){
                return false
        }
        
        //El % del descuento no puede ser mayor que 99
        if(desc1>99 || desc2>99 || desc3>99 || descfinal>99){
                return false
        }
        // no puede ser un descuento mas grande para menos productos
        if(desc2 > descfinal){
                    return false;
        }
        if(numrange==3) {
        // no puede ser un descuento mas grande para menos productos
            if(desc3 > descfinal){
                    return false;
            }
            if(desc2 > desc3){
                return false;
            }
        }
        
        return true;
     }
     
              
     private boolean valuesValidRanges(int numrange , int ran2,int ran3) {

        //un rango no puede ser ni menor ni igual que 0
        if(ran2<=0| ran3<=0){
            return false
        }
        
        //Si tengo 3 rangos, el segundo tope no puede ser mayor que el primero 
        // (De 1 hasta ran2 productos, de ran2 a ran3 y de ran3 a infinito. ran3>ran2.)
        if(numrange==3){
            if(ran3<=ran2) {
                return false
            }
        }

        return true;
     }
     
    private long generateTimeInMs(Calendar calendar, int year, int month, int day) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DATE, day)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        //println calendar.time 
        
        return calendar.getTimeInMillis()
    }
    
}
