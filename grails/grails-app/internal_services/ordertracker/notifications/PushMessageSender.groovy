package ordertracker.notifications

import grails.plugins.rest.client.RestBuilder
import ordertracker.Distribution
import ordertracker.Push_message
import ordertracker.Seller
import ordertracker.UserNotification
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.util.logger.Log

/**
 * Created by martin on 18/05/16.
 */
class PushMessageSender {

    private Distribution distribution

    PushMessageSender(Distribution distribution) {
        this.distribution = distribution
    }

    public def sendMessage(String authorizationKey){
        def json = buildJson(distribution)
        return sendPostRequest(authorizationKey, json)
    }

    public def buildJson(Distribution distribution) {
        def pushMessage = Push_message.findById(distribution.message_id)

        if ( pushMessage == null )
            throw new RuntimeException("push message id: " + distribution.message_id + "no encontrado ")

        try {
            def token_gcm = UserNotification.findByUser_id(distribution.seller).token_gcm
            this.buildJson(pushMessage, token_gcm)
        }

        catch (NullPointerException e) {
            throw new RuntimeException("GCM token de " + Seller.findById(distribution.seller).name + " no fue encontrado ")
        }

    }

    public def buildJson(Push_message pushMessage, String token_gcm) {
        def jsonBuilder = new JsonObjectBuilder()

        def gcmJsonBuilder = new GCMJsonBuilder(pushMessage)
        jsonBuilder.addJsonableItem( gcmJsonBuilder.generateTo(token_gcm) )
        jsonBuilder.addJsonableItem( gcmJsonBuilder.generateNotification() )
        jsonBuilder.addJsonableItem( gcmJsonBuilder.generateData() )

        return jsonBuilder.buildJson()
    }

    public def sendPostRequest(String authorizationKey, String jsonMessage) {
        try {
            return new RestBuilder().post('https://gcm-http.googleapis.com/gcm/send') {
                header 'Content-Type', 'application/json; charset=UTF-8'
                header 'Authorization', 'key=' + authorizationKey
                json jsonMessage
            }
        }
        catch (RuntimeException e) {
            Log.info("Error al enviar el mensaje: " + e.getMessage())
            return null
        }
    }
}
