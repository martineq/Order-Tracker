package ordertracker.internalServices

import ordertracker.constants.ServerDetails

class ImageService {

    private static String imageDir = ServerDetails.SERVER_PRODUCTS_IMAGE_DIR.toString()
    private static String tmpImageDir = ServerDetails.SERVER_TMP_DIR.toString() + ServerDetails.SERVER_PRODUCTS_IMAGE_DIR.toString()
    private static String errorDir = ServerDetails.SERVER_ERROR_IMAGE_DIR.toString()
    private static String tmpErrorDir = ServerDetails.SERVER_TMP_DIR.toString() + ServerDetails.SERVER_ERROR_IMAGE_DIR.toString()

    public static def loadImage(int productID) {
        ImageServiceHelper imageServiceHelper = new ImageServiceHelper()

        try {
            imageServiceHelper.loadValues(productID, imageDir, tmpImageDir)
            return imageServiceHelper.loadImage()
        }

        catch (FileNotFoundException ) {
            int errorImageFilename = 10

            imageServiceHelper.loadValues(errorImageFilename, errorDir, tmpErrorDir)
            return imageServiceHelper.loadImage()
        }
    }
}
