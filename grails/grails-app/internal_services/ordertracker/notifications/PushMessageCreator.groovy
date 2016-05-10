package ordertracker.notifications

import ordertracker.Distribution
import ordertracker.Push_message
import ordertracker.Seller
import ordertracker.User
import ordertracker.UserType
import ordertracker.constants.Keywords
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory
import ordertracker.util.logger.Log

class PushMessageCreator {

    def datagramSocket
    def distributions
    int port

    PushMessageCreator(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket
        this.port = 2222
    }

    public void send() {
        Distribution.withTransaction {

            try {
                this.distributions = Distribution.findAll()
            } catch (Exception e) {
                println(e.getMessage())
                this.distributions = null
            }

            distributions.each { Distribution distribution ->
                try {
                    this.sendMessage(createJson(distribution), obtainIP(distribution))
                }
                catch (Exception e) {}
            }
        }
    }

    private InetAddress obtainIP(Distribution distribution) {
        def userType = UserType.findByType_idAndType(distribution.seller, Seller.getTypeName())
        def seller_ip = User.findById( userType.user_id ).ip

        if ( seller_ip == "0" )
            throw new NullPointerException()

        return InetAddress.getByName(seller_ip);
    }

    private void sendMessage(String json, InetAddress address) {
        DatagramPacket datagramPacket = new DatagramPacket(json.bytes, json.length(), address, port);

        try {
            datagramSocket.send(datagramPacket);
            Log.info("Mensaje enviado a: "+ address.toString() + " [" + json + "] ")
        }

        catch ( Exception e) {
            Log.warn("Mensaje no pudo ser enviado a: "+ address.toString() +" // " + e.getMessage() )
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

        return notification.buildJson()
    }

}
