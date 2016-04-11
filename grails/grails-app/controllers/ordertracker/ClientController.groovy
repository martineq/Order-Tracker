package ordertracker

import ordertracker.queries.QueryFacade

class ClientController {

    def index() {
        def clients = Client.list()
        [clients:clients]
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
