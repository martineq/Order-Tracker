package ordertracker.util

import ordertracker.util.logger.Log

/**
 * Created by martin on 18/05/16.
 */
class JobThread {

    private int rings
    private boolean running
    private String serviceName

    JobThread(String serviceName) {
        this.running = true
        this.serviceName = serviceName
    }

    public void stop() {
        running = false
        this.waitForPushOrder(false)
    }

    private synchronized void waitForPushOrder(boolean wait) {
        if (wait == true){
            if ( rings == 0 ) {
                Log.info("GCM Connection Service sleeping")
                this.wait()
                Log.info("waking up GCM Connection Service")
                rings -= 1

            } else {
                Log.info("Waiting notifications: " + rings)
                rings = 0
            }
        }
        else {
            rings += 1
            this.notifyAll()
        }
    }

    public void run() {
        beforeRun()
        while (running)
        {
            whileRunning()
            this.waitForPushOrder(true)
        }
        afterRun()
    }

    public void push() {
        this.waitForPushOrder(false)
    }

    protected boolean threadIsRunning() {
        return running
    }

    def beforeRun() {}

    def whileRunning() {}

    def afterRun() {}
}
