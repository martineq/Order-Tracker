package ordertracker

import ordertracker.notifications.PushMessageHandler
import ordertracker.util.JobThread
import ordertracker.util.logger.Log

class GCMConnectorService extends JobThread {

    private static GCMConnectorService gcmConnectorService = null
    private static String authorizationKey

    public static GCMConnectorService initializeGCMConnectionService(String authorizationKey){
        GCMConnectorService.authorizationKey = authorizationKey

        return GCMConnectorService.getInstance()
    }

    public static GCMConnectorService getInstance() {
        if ( GCMConnectorService.gcmConnectorService == null )
            GCMConnectorService.gcmConnectorService = new GCMConnectorService(authorizationKey)

        return GCMConnectorService.gcmConnectorService
    }

    private PushMessageHandler pushMessageHandler

    private GCMConnectorService(String authorizationKey) {
        super("GCM Connector Service")
        this.pushMessageHandler = new PushMessageHandler(authorizationKey)
    }

    @Override
    def beforeRun() {
        Log.info("GCM Connector Service UP!")
    }

    @Override
    def whileRunning() {
        while ( pushMessageHandler.send() != 0 && threadIsRunning() ) System.sleep(10000)
    }

    @Override
    def afterRun() {
        Log.info("GCM Connector Service DOWN!")
    }
}
