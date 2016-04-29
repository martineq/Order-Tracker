package ordertracker

import ordertracker.queries.QueryFacade

class AgendaController {

    def index() {
        def sellers = Seller.list(sort:"document_number", order:"des")
        [sellers:sellers]
    }

    
     def editagenda() {
        def time = params.hour
        def trec= params.day;
        def minutes = time.substring(3);
        def hours = time.substring(0,2);
        def day = ""
        
        
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
        
        [hours:hours,minutes:minutes,day:day]
    }
    
    def savechanges() {
    
    
        def agenda = Agenda.get(params.id)
        
        def tim=params.hourr.toString()
        
        def min=params.minutr.toString()
        
        if (min.size()==1) {
                min="0"+min
        }
        
        agenda.time=tim+":"+min
        
        def day=params.dayy
        
        if ( day=='Domingo' ) {
            agenda.day=1;
        }
        else if ( day=='Lunes' ) {
            agenda.day=2;
        }
        else if ( day=='Martes' ) {
            agenda.day=3;
        }
        else if ( day=='Miércoles' ) {
            agenda.day=4;
        }
         else if ( day=='Jueves' ) {
            agenda.day=5;
        }
        else if ( day=='Viernes' ) {
            agenda.day=6;
        }
         else if ( day=='Sábado' ) {
            agenda.day=7;
        }


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
        
        def trec= params.day;
        
        def day=""
        
        
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

}
