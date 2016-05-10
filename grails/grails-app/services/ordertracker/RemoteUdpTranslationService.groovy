package ordertracker

import ordertracker.notifications.RemoteDeviceAddressTranslator
import ordertracker.util.logger.Log

class RemoteUdpTranslationService {

    private static RemoteUdpTranslationService remoteUdpTranslationService = null

    public static RemoteUdpTranslationService getInstance() {
        if ( RemoteUdpTranslationService.remoteUdpTranslationService == null )
            RemoteUdpTranslationService.remoteUdpTranslationService = new RemoteUdpTranslationService()

        return RemoteUdpTranslationService.remoteUdpTranslationService
    }

    private DatagramSocket datagramSocket
    private boolean running

    private RemoteUdpTranslationService() {
        running = true
    }

    public void run() {
        byte []message = new byte[1024]
        DatagramPacket datagramPacket = new DatagramPacket(message, message.size())

        datagramSocket = new DatagramSocket(2222);

        Log.info("UDP Translation Service UP!")
        while ( running ) {
            try {
                datagramSocket.receive(datagramPacket);

                def remoteDeviceAddressTranslator = new RemoteDeviceAddressTranslator(datagramPacket)
                remoteDeviceAddressTranslator.obtainRemoteDeviceInformation()
                remoteDeviceAddressTranslator.persistInformation()

                PushService.getInstance().push()
            }

            catch (Exception se) {
                Log.info("UDP Translation Service Exception: "+ se.getMessage())
            }
        }

        datagramSocket.close()
        Log.info("UDP Translation Service DOWN!")
    }

    public void stop() {
        running = false
        this.datagramSocket.close()
    }

}
