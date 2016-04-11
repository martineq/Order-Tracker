package ordertracker

import ordertracker.queries.QueryFacade

class AgendaController {

    def index() {
        def sellers = Seller.list(sort:"document_number", order:"des")
        [sellers:sellers]
    }
    
    def show() {
        def agenda = Agenda.findAllBySeller_id(params.id)
        def sell = Seller.findById(params.id)
        
        def numsell=params.id
        
        def resul= Agenda.executeQuery("select t2.id,t2.name,t1.day,t1.time from Agenda t1,Client t2 where t1.client_id = t2.id and t1.seller_id = ${numsell}")

        [sell:sell,resul:resul]
    }

}
