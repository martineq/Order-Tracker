package ordertracker

import ordertracker.queries.QueryFacade

class SellerController {

    def index() {
        def sellers = Seller.list(sort:"document_number", order:"des")
        [sellers:sellers]
    }

    def weeklySchedule() {
        response << new QueryFacade(new WeeklyScheduleService()).solve(request)
    }
    
            
    def up() {
    }
    
    def save() {
        def seller = new Seller()
        seller.name=params.name
        seller.document_number=params.dni.toInteger()
        seller.phone=params.phone.toInteger()
        seller.zone=params.zone
        
        
        //TODO: COMO guardo el usuario acá????
        
        seller.save(failOnError: true)

        [sellern:seller.name]
    }
    
    def updateseller() {

        def seller = Seller.get(params.id)
        
        seller.name=params.name
        seller.phone=params.phone.toInteger()
        seller.document_number=params.dni.toInteger()
        seller.zone=params.zone
        
        //TODO: EDITAR USUARIO ACA
        
        seller.save(failOnError: true)

        [seller:seller]

    }
    
    def deleteconfirm() {
    }
    
    def editseller() {

        def seller = Seller.findById(params.id)
        
        [seller:seller]

    }
    
    def delete() {
        def pid=params.id
        Seller.executeUpdate("delete Seller where id=${pid}")
        
        //borrar todas las entradas de la agenda para este vendedor
        Agenda.executeUpdate("delete Agenda where seller_id=${pid}")
        
        [clients:params.id]
    }

}
