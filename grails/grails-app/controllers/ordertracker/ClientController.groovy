package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.constants.ClientStates

class ClientController {

    def index() {
        def clients = Client.list(sort:"name", order:"des")
        [clients:clients]
    }
        
    def up() {
    }
    
    def save() {
        def client = new Client()
        client.name=params.name
        client.city=params.city
        client.email=params.mail
        client.address=params.address
        client.state=ClientStates.VISITADO.toString()
        
        //TODO: Aca cargar un qr de verdad!
        client.qrcode="PRUEBA"
        client.save(failOnError: true)
        [clientn:client.name]
    }
    
    def listdelete() {
        def clients = Client.list(sort:"name", order:"des")
        [clients:clients]
    }
    
    def deleteconfirm() {
    }
    
    def delete() {
        def pid=params.id
        Client.executeUpdate("delete Client where id=${pid}")
        [clients:params.id]
    }
    

    def help() {
        def renderer = new Renderer()
        render renderer.message("Métodos disponibles<br>")
        render renderer.link("list", "devuelve la lista de clientes")
        render renderer.link("description", "devuelve la lista de clientes")
    }

    def list() {
        response << new QueryFacade(new AssignedClientsListService()).solve(request)
    }

    def description() {
        response << new QueryFacade(new ClientDescriptionService()).solve(request)
    }
}
