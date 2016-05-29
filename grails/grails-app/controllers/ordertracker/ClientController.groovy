package ordertracker

import ordertracker.queries.QueryFacade

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
        client.qrcode=""
        client.save(failOnError: true)

        [clientn:client.name]
    }
    
    def editclient() {

        def client = Client.findById(params.id)
        
        [client:client]
        

    }
    
     def listdelete() {
        def clients = Client.list(sort:"name", order:"des")
        [clients:clients]
    }
    
    def updateclient() {

        def client = Client.get(params.id)
        
        client.name=params.name
        client.city=params.city
        client.email=params.mail
        client.address=params.address
        
        client.save(failOnError: true)

        [client:client]

    }
    
    def deleteconfirm() {
    }
    
    def delete() {
        def pid=params.id
        Client.executeUpdate("delete Client where id=${pid}")
        
        //borrar todas las entradas de la agenda para este cliente.
        Agenda.executeUpdate("delete Agenda where client_id=${pid}")
        
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
    
    def clientdetails(){
        String add = params.address+","+params.city
        [address:add]

    }
    
    def searchnameclient(){
        def clientsAll = Client.list(sort:"name", order:"des")
        def clients=[]
        def res=0
        
        clientsAll.each { cli ->
                if( cli.name.toLowerCase().contains(params.name.toLowerCase()) ){
                    clients.add(cli);
                    res=res+1
                }
        };
        
        [clients:clients,res:res]
    }
    
    
}
