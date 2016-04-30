package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.constants.Keywords

class AgendaController {


    int sunday
    int monday
    int tuesday
    int wednesday
    int thursday
    int friday
    int saturday 
    int year
    int month
    Calendar calendar
    
    public AgendaController() {
    
        calendar = Calendar.getInstance(TimeZone.getTimeZone(Keywords.AR_TIMEZONE.toString()))
        calendar.setFirstDayOfWeek(calendar.SUNDAY)

        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        
        sunday = this.getDayOfTheWeek(calendar, Calendar.SUNDAY)
        monday = this.getDayOfTheWeek(calendar, Calendar.MONDAY)
        tuesday = this.getDayOfTheWeek(calendar, Calendar.TUESDAY)
        wednesday = this.getDayOfTheWeek(calendar, Calendar.WEDNESDAY)
        thursday = this.getDayOfTheWeek(calendar, Calendar.THURSDAY)
        friday = this.getDayOfTheWeek(calendar, Calendar.FRIDAY)
        saturday = this.getDayOfTheWeek(calendar, Calendar.SATURDAY)
    }
    
    
    private String getDayString(int trec) {
    
        String day=""
        
        if ( trec=='1' ) {
            day="Domingo"
        }
        else if ( trec=='2' ) {
            day="Lunes"
        }
        else if ( trec=='3' ) {
            day="Martes"
        }
        else if ( trec=='4' ) {
            day="Miércoles"
        }
        else if ( trec=='5' ) {
            day="Jueves"
        }
        else if ( trec=='6' ) {
            day="Viernes"
        }
        else if ( trec=='7' ) {
            day="Sábado"
        }
        
        return day;
    }

    private long generateTimeInMs(int day, int hour, int minutes) {
        this.calendar.set(Calendar.YEAR, this.year)
        this.calendar.set(Calendar.MONTH, this.month)
        this.calendar.set(Calendar.DATE, day)
        this.calendar.set(Calendar.HOUR_OF_DAY, hour)
        this.calendar.set(Calendar.MINUTE, minutes)
        this.calendar.set(Calendar.SECOND, 0)
        
        return this.calendar.getTimeInMillis()
    }
    
    private int getDayOfTheWeek(Calendar calendar, int day) {
        calendar.set(Calendar.DAY_OF_WEEK, day)
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    def index() {
        def sellers = Seller.list(sort:"document_number", order:"des")
        [sellers:sellers]
    }
    
     def editagenda() {
        def time = params.hour
        int trec= params.day;
        def minutes = time.substring(3);
        def hours = time.substring(0,2);
        def day = ""
        
        day=getDayString(trec);
        
        [hours:hours,minutes:minutes,day:day]
    }
    
    def savechanges() {
    
    
        def agenda = Agenda.get(params.id)
        
        int dia;
        
        def tim=params.hourr.toString()
        
        def min=params.minutr.toString()
        
        if (min.size()==1) {
                min="0"+min
        }
        
        agenda.time=tim+":"+min

        
        def day=params.dayy
        
        if ( day=='Domingo' ) {
            agenda.day=1;
            dia=this.sunday;
        }
        else if ( day=='Lunes' ) {
            agenda.day=2;
            dia=this.monday;
        }
        else if ( day=='Martes' ) {
            agenda.day=3;
            dia=this.tuesday;
        }
        else if ( day=='Miércoles' ) {
            agenda.day=4;
            dia=this.wednesday;
        }
         else if ( day=='Jueves' ) {
            agenda.day=5;
            dia=this.thursday;
        }
        else if ( day=='Viernes' ) {
            agenda.day=6;
            dia=this.friday;
        }
         else if ( day=='Sábado' ) {
            agenda.day=7;
            dia=this.saturday;
        }
        
        agenda.date=this.generateTimeInMs(dia, params.hourr.toInteger(), params.minutr.toInteger());


        agenda.save(failOnError: true)
        
        [agenda:agenda,time:agenda.time,day:day]
        

    }
    
    def deleteconfirm() {
    
         
    }
    
    def delete() {
        def pid=params.id
        Agenda.executeUpdate("delete Agenda where id=${pid}")
        
        [agenda:params.id]
    }
    
    def selectclient() {
        
    
        def agenda = Agenda.get(params.agendaid)

        agenda.client_id=(params.clientid.toInteger())
        
        agenda.save(failOnError: true)
        
        [agenda:agenda]
        
        
        
    }
    
    def selectnewclient() {
        def clients = Client.list(sort:"name", order:"des")
        
        int trec= params.day;
        
        def day=""
        
        day=getDayString(trec);
        
        [clients:clients,day:day]
    }
    
    def show() {
        def agenda = Agenda.findAllBySeller_id(params.id)

        def sell = Seller.findById(params.id)
        
        def numsell=params.id
        
        def dayr=params.day
        
        def resul= Agenda.executeQuery("select t2.id,t2.name,t1.day,t1.time,t1.id,t2.id from Agenda t1,Client t2 where t1.client_id = t2.id and t1.seller_id = ${numsell} and (${dayr} = t1.day or ${dayr} =0 ) order by t1.day asc , t1.time asc")

        [sell:sell,resul:resul,dayr:dayr]
    }
    
    def up() {
        def clients = Client.list(sort:"name", order:"des")
        
        [clients:clients]
    
    }
    
    def selectdate() {
    
    }
    
    def upentry() {
    
        def agenda = new Agenda();
        
        int dia;
        
        agenda.seller_id=params.sellerid.toInteger()
        agenda.client_id=params.clientid.toInteger()
        
        def tim=params.hourr.toString()
        
        def min=params.minutr.toString()
        
        if (min.size()==1) {
                min="0"+min
        }
        
        agenda.time=tim+":"+min

        
        def day=params.dayy
        
        if ( day=='Domingo' ) {
            agenda.day=1;
            dia=this.sunday;
        }
        else if ( day=='Lunes' ) {
            agenda.day=2;
            dia=this.monday;
        }
        else if ( day=='Martes' ) {
            agenda.day=3;
            dia=this.tuesday;
        }
        else if ( day=='Miércoles' ) {
            agenda.day=4;
            dia=this.wednesday;
        }
         else if ( day=='Jueves' ) {
            agenda.day=5;
            dia=this.thursday;
        }
        else if ( day=='Viernes' ) {
            agenda.day=6;
            dia=this.friday;
        }
         else if ( day=='Sábado' ) {
            agenda.day=7;
            dia=this.saturday;
        }
        
        agenda.date=this.generateTimeInMs(dia, params.hourr.toInteger(), params.minutr.toInteger());

        agenda.save(failOnError: true)
        
        [agenda:agenda,time:agenda.time,day:day]
        
    }
    
    def changeseller() {
        def sellers = Seller.list(sort:"document_number", order:"des")
        int trec= params.day;
        
        def day=""
        
        day=getDayString(trec);
        
        [sellers:sellers,day:day]
    }
    
    def selectnewseller() {
    
        def agenda = Agenda.get(params.agendaid)
        
        int trec= params.day;
        
        def day=""
        
        day=getDayString(trec);

        agenda.seller_id=(params.newsellerid.toInteger())
        
        agenda.save(failOnError: true)
        
        [agenda:agenda,day:day]
        
    }

}
