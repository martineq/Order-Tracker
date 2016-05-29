package ordertracker.internalServices

import ordertracker.util.logger.Log

class ImageServiceHelper {

    private String image
    private int productID
    private String imageDir
    private String tmpImageDir

    def loadValues(def productID, String imageDir, String tmpImageDir) {
        this.image = ""
        this.productID = productID
        this.imageDir = imageDir
        this.tmpImageDir = tmpImageDir
    }

    def loadImage() throws FileNotFoundException {
        try {
            File tempImage = new File( tmpImageDir + productID)
            image = tempImage.text
        }

        catch(FileNotFoundException e) {
            this.generateNewTempImageFile(tmpImageDir)
        }

        return this.image
    }

    private def generateNewTempImageFile(String tmpImageDir) {
        try {
            this.processSavedImage(imageDir + productID)
            this.saveTmpImage(tmpImageDir)
        }

        catch ( FileNotFoundException e ) {
            Log.warn("No se encontró la imagen correspondiente al producto ["+productID+"] en "+ imageDir + productID )
            throw new FileNotFoundException()
        }
    }

    private def processSavedImage(String fileLocation) {
        try {
            File imageFile = new File(fileLocation)
            image = imageFile.bytes.encodeBase64()
        }
        catch(NullPointerException e) {}
    }

    private def saveTmpImage(String tmpImageDir) {
        try {
            new File(tmpImageDir).mkdirs()
            new File(tmpImageDir + productID).write(image)
        }

        catch(FileNotFoundException e) {}
    }

}
