package ordertracker

import ordertracker.notifications.PushMessageCreator
import ordertracker.util.logger.Log

class PushService {

    private static PushService pushService = null

    public static PushService getInstance() {
        if ( PushService.pushService == null )
            PushService.pushService = new PushService()

        return PushService.pushService
    }

    private synchronized void waitForPushOrder(boolean wait) {

        if ( wait == true ) {
            Log.info("Push Service sleeping")
            this.wait()
            Log.info("Waking up Push Service")
        }

        else {
            PushService.pushService.notifyAll()
        }

    }

    private DatagramSocket datagramSocket
    private boolean running

    private PushService() {
        running = true
    }

    public void push() {
        this.waitForPushOrder(false)
    }

    public void run() {
        datagramSocket = new DatagramSocket();
        datagramSocket.setReuseAddress(true);

        Log.info("Push Service UP!")
        while ( running ) {
            new PushMessageCreator(datagramSocket).send()
            this.waitForPushOrder(true)
        }

        datagramSocket.close()
        Log.info("Push Service DOWN!")
    }

    public void stop() {
        running = false
        this.waitForPushOrder(false)
    }

}
