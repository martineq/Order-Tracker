package ordertracker

import ordertracker.constants.ServerDetails
import ordertracker.util.Server
import ordertracker.util.logger.Log
import ordertracker.util.logger.SecurityLog

class BootStrap {

    def init = { servletContext ->

        SecurityLog.InitializeLogger(ServerDetails.SERVER_LOGS_PATH, ServerDetails.SERVER_SECW_LOG_FILE_NAME)

        Log.InitializeLogger(ServerDetails.SERVER_LOGS_PATH, ServerDetails.SERVER_INFO_LOG_FILE_NAME )
        Log.info("Loading server on url: "+ Server.getURL())

		new UserLoader().load()
        new ClientLoader().load()
            new ProductLoader().load()

            new BrandLoader().load()
            new DiscountLoader().load()
            new AgendaLoader().load()
            new SellerLoader().load()
            new ClientOrderLoader().load()
            new OrderDetailLoader().load()

        Log.info("DB UP!")
    }

    def destroy = {
        Log.info("Server DOWN!")
    }
}
