package ordertracker.notifications

import ordertracker.Distribution
import ordertracker.Push_message
import ordertracker.User
import ordertracker.UserNotification
import ordertracker.util.logger.Log
import reactor.jarjar.com.lmax.disruptor.dsl.Disruptor

/**
 * Created by martin on 17/05/16.
 */
class PushMessageHandler {

    private String authorizationKey

    PushMessageHandler(String authorizationKey) {
        this.authorizationKey = authorizationKey
    }

    public def send() {
        def distributions

        Distribution.withTransaction {

            long total = Distribution.count

            try {
                def sellerList = UserNotification.executeQuery("SELECT user_id FROM UserNotification")
                distributions = Distribution.findAllBySellerInList(sellerList)

                distributions.each {
                    try {
                        def resp = new PushMessageSender(it).sendMessage(authorizationKey)
                        def stat = new ResponseAnalizer(resp).analizeResponse()
                        if (stat == true) this.deleteMessage(it)
                    }

                    catch (Exception e) {
                        Log.info(e.getMessage())
                    }
                }
            }

            catch (Exception e) {
                Log.info(e.getMessage())
            }

            finally {
                long delayedMessages = Distribution.executeQuery("SELECT COUNT(*) from Distribution d, UserNotification n WHERE d.seller = n.user_id").get(0)
                Log.info("Mensajes pendientes [ " + delayedMessages + " ] - Mensajes en espera del token [ " + ( total - delayedMessages) + " ]")
                return delayedMessages
            }
        }
    }

    public void deleteMessage(Distribution distributionMessage) {
        def id = distributionMessage.id
        def push_id = distributionMessage.message_id
        def seller_id = distributionMessage.seller

        try {
            distributionMessage.delete(flush: true)

            if ( Distribution.findByMessage_id(id) == null )
                Log.info("Mensaje push #" + id + " fue entragado a "+ User.findById(seller_id).username + " y borrado del servidor")

            if (Distribution.countByMessage_id(id) == 0)
                Push_message.findById(push_id).delete(flush: true)

            if ( Push_message.findById(push_id) == null )
                Log.info("Todos los mensajes push #"+ push_id + " fueron entregados")
        }

        catch ( Exception e ) {}
    }
}
