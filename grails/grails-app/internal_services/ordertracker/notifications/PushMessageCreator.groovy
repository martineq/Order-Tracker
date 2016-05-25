package ordertracker.notifications

import ordertracker.Distribution
import ordertracker.Push_message
import ordertracker.Seller
import ordertracker.User
import ordertracker.UserSocket
import ordertracker.UserType
import ordertracker.constants.Keywords
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory
import ordertracker.util.logger.Log

class PushMessageCreator {

    def datagramSocket
    def distributions

    PushMessageCreator(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket
    }

    public void send() {
        Distribution.withTransaction {

            try {
                this.distributions = Distribution.findAll()
            } catch (Exception e) {
                this.distributions = null
            }

            distributions.each { Distribution distribution ->
                try {
                    UserSocket socket = obtainUserSocket(distribution)
                    this.sendMessage(createJson(distribution), InetAddress.getByName(socket.remote_ip), socket.remote_port)
                }
                catch (Exception e) {}
            }
        }
    }

    private UserSocket obtainUserSocket(Distribution distribution) {
        def userType = UserType.findByType_idAndType(distribution.seller, Seller.getTypeName())
        return UserSocket.findByUser_id(userType.user_id)
    }

    private void sendMessage(String json, InetAddress address, int port) {
        DatagramPacket datagramPacket = new DatagramPacket(json.bytes, json.length(), address, port);

        try {
            datagramSocket.send(datagramPacket);
            Log.info("Mensaje enviado a: "+ address.toString() + ':' + port +" [" + json + "] ")
        }

        catch ( Exception e) {
            Log.warn("Mensaje no pudo ser enviado a: "+ address.toString() +':'+port.toString() + ' ' + e.getMessage() )
        }
    }

    private String createJson(Distribution distribution) {
        def message

        Push_message.withTransaction {
            message = Push_message.findById(distribution.message_id)
        }

        def notification = new JsonObjectBuilder()
        notification.addJsonableItem(new JsonPropertyFactory(Keywords.ID, message.id))
        notification.addJsonableItem(new JsonPropertyFactory(Keywords.TITLE, message.title))
        notification.addJsonableItem(new JsonPropertyFactory(Keywords.BODY, message.description))
        notification.addJsonableItem(new JsonPropertyFactory(Keywords.TYPE, message.type))

        return notification.buildJson()
    }

}
