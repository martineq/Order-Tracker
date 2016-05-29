package ordertracker.notifications

import grails.converters.JSON
import ordertracker.PushService
import ordertracker.User
import ordertracker.UserSocket
import ordertracker.util.logger.Log
import org.grails.web.json.JSONObject

class RemoteDeviceAddressTranslator {

    private DatagramPacket datagramPacket

    private boolean validInformation
    private int remotePort
    private String remoteIP
    private String username
    private String token

    RemoteDeviceAddressTranslator(DatagramPacket datagramPacket) {
        this.datagramPacket = datagramPacket
        this.validInformation = false
        this.remotePort = 0
        this.remoteIP = ""
        this.username = ""
        this.token = ""
    }

    public boolean obtainRemoteDeviceInformation() {
        boolean result = false
        try {
            this.remoteIP = datagramPacket.address.getHostAddress()
            this.remotePort = datagramPacket.port

            JSONObject parser = JSON.parse(new String(datagramPacket.data, "UTF-8"))
            this.username = parser.getString('username')
            this.token = parser.getString('token')
            result = true
        }

        catch (Exception e) {
            Log.info("Error al parsear mensaje UDP: "+e.getMessage())
        }

        finally { return result }
    }

    public void persistInformation() {
        try {
            User.withTransaction {
                User user = User.findByUsernameAndToken(username, token)
                UserSocket socket = UserSocket.findByUser_id(user.id)

                if (socket == null)
                    socket = new UserSocket()

                socket.setUser_id(user.id)
                socket.setRemote_ip(this.remoteIP)
                socket.setRemote_port(this.remotePort)
                socket.save()

                Log.info("Mensaje UDP enviado por: " + username + " desde " + remoteIP+':'+remotePort)
            }
        }

        catch (Exception e) {
            Log.info("Error al crear el userSocket: " +e.getMessage())
        }

    }
}
