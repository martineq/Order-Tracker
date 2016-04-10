package ordertracker

import ordertracker.queries.QueryFacade
import ordertracker.queries.RawQueryFacade

class ProductController {

    def index() {
        def renderer = new Renderer()
        render renderer.message("Métodos disponibles<br>")
        render renderer.link("list", "lista los productos existentes")
        render renderer.link("description", "descripción de un producto en particular")
        render renderer.link("image", "devuelve una imagen")
    }

    def list() {
        response << new QueryFacade(new AvailableProductsService()).solve(request)
    }

    def description() {
        response << new QueryFacade(new ProductDescriptionService()).solve(request)
    }

    def image() {
        response << new RawQueryFacade(new ProductImageService()).solve(request)
    }
}
