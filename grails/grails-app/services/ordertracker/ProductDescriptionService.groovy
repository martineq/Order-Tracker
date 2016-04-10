package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.protocol.Data
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium
import ordertracker.util.Server

class ProductDescriptionService implements Queryingly {

    private boolean validationResult
    private def productID
    private def product

    ProductDescriptionService() {
        this.validationResult = false
        this.productID = -1
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.GET.toString()) )
            throw new QueryException("Invalid HTTP request method: must be GET")

        requester.validateRequest(Enums.asList(Keywords.PRODUCT_ID))
        this.productID = requester.getProperty(Keywords.PRODUCT_ID)
        return true
    }

    @Override
    def generateQuery() {
        this.product = Product.findById(this.productID.toString())
        return this.validationResult = this.product != null
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( this.validationResult == false )
            return new ProtocolJsonBuilder().addStatus(new Status(Result.ERROR,"Product not found"))

        return new ProtocolJsonBuilder().addStatus(new Status(Result.OK,"Available product")).addData(this.generateData())
    }

    private Data generateData() {
        def jsonObject = new JsonObjectBuilder()

        // TODO Brand subquery
        // TODO IMAGE ID
        jsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.NAME, product.name))
        jsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.BRAND_ID, product.brand_id))
        jsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.IMAGE, this.getImageURL()))
        jsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.CATEGORY, product.category))
        jsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.CHARACTERISTIC, product.characteristic))
        jsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.STOCK, product.stock))
        jsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.PRICE, product.price))
        jsonObject.addJsonableItem(new JsonPropertyFactory(Keywords.STATE, product.state))

        return new Data(jsonObject)
    }

    private String getImageURL() {
        return Server.getURL()+"/"+Keywords.PRODUCT.toString()+'/'+Keywords.IMAGE.toString()+'/'+this.productID.toString()
    }

}

