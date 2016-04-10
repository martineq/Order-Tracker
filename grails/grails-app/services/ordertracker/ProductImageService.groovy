package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.InvalidConstants
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class ProductImageService implements Queryingly{

    private def productID
    private def image

    ProductImageService() {
        this.productID = InvalidConstants.INVALID_PRODUCT
        this.image = InvalidConstants.INVALID_IMAGE
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.GET.toString()) )
            throw new QueryException("Invalid HTTP request method: must be GET")

        requester.validateRequest(Enums.asList(HttpProtocol.REQUEST_URI))
        this.productID = requester.getProperty(HttpProtocol.REQUEST_URI)
        this.productID = this.productID.substring(this.productID.toString().lastIndexOf('/')+1)
        return true
    }

    @Override
    def generateQuery() {
        try {
            this.image = Product.findById(this.productID.toString()).image
            return true
        }

        catch( NullPointerException npe ) {
            return false
        }
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        return this.image
    }
}
