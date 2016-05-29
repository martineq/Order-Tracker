package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.InvalidConstants
import ordertracker.constants.ServerDetails
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class ProductImageService implements Queryingly{

    private def productID
    private String image
    private static tmpImageFile = ServerDetails.SERVER_TMP_DIR.toString() + ServerDetails.SERVER_PRODUCTS_IMAGE_DIR.toString()

    ProductImageService() {
        this.productID = InvalidConstants.INVALID_PRODUCT
        this.image= InvalidConstants.INVALID_IMAGE
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
            File tempImage = new File(tmpImageFile + productID.toString())
            image = tempImage.text
        }
        catch(FileNotFoundException e) {
            this.processSavedImage()
            this.saveTmpImage()
        }
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        return this.image
    }

    private def processSavedImage() {
        try {
            Product product = Product.findById(productID.toString())
            File imageFile = new File(product.image)
            image = imageFile.bytes.encodeBase64()
        }
        catch(NullPointerException e) {}
        catch(FileNotFoundException e) {}
    }

    private def saveTmpImage() {
        try {
            new File(tmpImageFile).mkdirs()
            new File(tmpImageFile + productID.toString()).write(image)
        }
        catch(FileNotFoundException e) {}
    }
}
