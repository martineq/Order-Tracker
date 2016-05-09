package ordertracker

import ordertracker.constants.ServerDetails
import ordertracker.util.Server
import ordertracker.util.logger.Log
import ordertracker.util.logger.SecurityLog

class BootStrap {

    def pushService

    def init = { servletContext ->

        SecurityLog.InitializeLogger(ServerDetails.SERVER_LOGS_PATH, ServerDetails.SERVER_SECW_LOG_FILE_NAME)

        Log.InitializeLogger(ServerDetails.SERVER_LOGS_PATH, ServerDetails.SERVER_INFO_LOG_FILE_NAME )
        Log.info("Loading server on url: "+ Server.getURL())

        pushService = PushService.getInstance()

        Thread.start {
            pushService.run()
        }

		new UserLoader().load()
        new ClientLoader().load()
            new ProductLoader().load()

            new BrandLoader().load()
            new DiscountLoader().load()
            new AgendaLoader().load()
            new SellerLoader().load()
            new ClientOrderLoader().load()
            new OrderDetailLoader().load()
            new UserTypeLoader().load()


        String productImages = ServerDetails.SERVER_PRODUCTS_IMAGE_DIR.toString()
        String productTmpImages = ServerDetails.SERVER_TMP_DIR.toString() + ServerDetails.SERVER_PRODUCTS_IMAGE_DIR.toString()
        String compressedImages = ServerDetails.SERVER_TMP_DIR.toString() + ServerDetails.SERVER_COMPRESS_DIR.toString() + ServerDetails.SERVER_PRODUCTS_IMAGE_DIR.toString()

        Log.info("Compressing images")
        new ImageResizer( productImages, productTmpImages, compressedImages).resize()

        Log.info("DB UP!")
    }

    def destroy = {
        pushService.stop()
        Log.info("Server DOWN!")
    }
}
