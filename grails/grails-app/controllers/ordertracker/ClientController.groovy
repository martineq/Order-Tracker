package ordertracker

import ordertracker.queries.QueryFacade

class ClientController {

    def index() {
        def renderer = new Renderer()
        render renderer.message("Métodos disponibles<br>")
        render renderer.link("list", "devuelve la lista de clientes")
    }

    def list() {
        response << new QueryFacade(new AssignedClientsListService()).solve(request)
    }
}
