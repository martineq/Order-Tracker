package ordertracker

import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.Jsonable
import ordertracker.protocol.builder.properties.JsonPropertyFactory
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class AvailableProductsService implements Queryingly {

    private def products
    private boolean validationResult

    AvailableProductsService() {
        this.validationResult = false
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.GET.toString()) != 0 )
            throw new QueryException("Invalid HTTP request method: must be GET")

        return true
    }

    @Override
    def generateQuery() {
        this.products = Product.findAll()
        return this.validationResult = this.products != null
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if (this.validationResult == false)
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR, "undefined"))

        return new ProtocolJsonBuilder().addStatus(new Status(Result.OK, "Available product")).addData(this.generateData())
    }

    private Data generateData() {
        def clientsList = new JsonObjectBuilder()
        this.products.each() { product -> clientsList.addJsonableItem(this.generateJsonProductsObject(product)) }
        return new Data(clientsList)
    }

    private Jsonable generateJsonProductsObject(def product) {
        def jsonObject = new JsonObjectBuilder()

        jsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.IMAGE, product.image))
        jsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.NAME, product.name))
        jsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.BRAND, this.getBrandName(product.brand_id)))
        jsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.PRICE, product.price))

        return jsonObject
    }

    private String getBrandName(def brand_id) {
        try {
            def brand = Brand.findById(brand_id.toString())
            return brand.name
        }

        catch (NullPointerException np) {
            new QueryException("Server error: brand name for Product not found")
        }

    }

}
