package ordertracker

import ordertracker.constants.Constants
import ordertracker.constants.ServerDetails
import ordertracker.status.VisitsStatusUpdate
import ordertracker.util.Server
import ordertracker.util.logger.Log
import ordertracker.util.logger.SecurityLog

class BootStrap {

    // def pushService
    // def remoteUdpTranslationService

    def init = { servletContext ->

        SecurityLog.InitializeLogger(ServerDetails.SERVER_LOGS_PATH, ServerDetails.SERVER_SECW_LOG_FILE_NAME)

        Log.InitializeLogger(ServerDetails.SERVER_LOGS_PATH, ServerDetails.SERVER_INFO_LOG_FILE_NAME )
        Log.info("Loading server on url: "+ Server.getURL())

        // pushService = PushService.getInstance()
        // remoteUdpTranslationService = RemoteUdpTranslationService.getInstance()

        // Thread.start {
        //     pushService.run()
        // }

        // Thread.start {
        //    remoteUdpTranslationService.run()
        // }

        Thread.start {
            GCMConnectorService.initializeGCMConnectionService(Constants.GCM_AUTHORIZATION_KEY).run()
        }
    
        new UseradminLoader().load()
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

        VisitsStatusUpdate.getInstance().start()

        String productImages = ServerDetails.SERVER_PRODUCTS_IMAGE_DIR.toString()
        String productTmpImages = ServerDetails.SERVER_TMP_DIR.toString() + ServerDetails.SERVER_PRODUCTS_IMAGE_DIR.toString()
        String compressedImages = ServerDetails.SERVER_TMP_DIR.toString() + ServerDetails.SERVER_COMPRESS_DIR.toString() + ServerDetails.SERVER_PRODUCTS_IMAGE_DIR.toString()

        Log.info("Compressing images")
        new ImageResizer( productImages, productTmpImages, compressedImages).resize()

        Log.info("DB UP!")
    }

    def destroy = {
        // pushService.stop()
        // remoteUdpTranslationService.stop()
        VisitsStatusUpdate.getInstance().terminate()
        GCMConnectorService.getInstance().stop()
        Log.info("Server DOWN!")
    }
}
