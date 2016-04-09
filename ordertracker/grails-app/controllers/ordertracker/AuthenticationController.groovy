package ordertracker

import ordertracker.queries.AuthenticationQuery
import ordertracker.queries.QueryFacade

class AuthenticationController {

    def index() {
        def renderer = new Renderer()
        render renderer.message("Métodos disponibles<br>")
        render renderer.link("authenticate", "verifica si el usuario es válido")
    }

    def authenticate() {
        response << new QueryFacade(new AuthenticationQuery()).solve(request)
    }
}
