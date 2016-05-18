package ordertracker.notifications

import ordertracker.Distribution
import ordertracker.Push_message
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.util.logger.Log

/**
 * Created by martin on 17/05/16.
 */
class PushMessageBuilder {

    private String authorizationKey

    PushMessageBuilder(String authorizationKey) {
        this.authorizationKey = authorizationKey
    }

    public void send() {
        def distributions

        Distribution.withTransaction {

            try {
                distributions = Distribution.findAll()
            } catch (Exception e) {
                println(e.getMessage())
                distributions = null
            }

            distributions.each { Distribution distribution ->
                try {
                    def json = buildJson(distribution)
                    // TODO generateRequest
                }
                catch (Exception e) {
                    Log.info("No se pudo enviat el mensaje: "+e.getMessage())
                }
            }
        }
    }

    public def buildJson(Distribution distribution) {
        def jsonBuilder = new JsonObjectBuilder()

        def pushMessage = Push_message.findById(distribution.message_id)

        def gcmJsonBuilder = new GCMJsonBuilder(pushMessage)
        jsonBuilder.addJsonableItem( gcmJsonBuilder.generateTo("gcm_token") )
        jsonBuilder.addJsonableItem( gcmJsonBuilder.generateNotification() )
        jsonBuilder.addJsonableItem( gcmJsonBuilder.generateData() )

        return jsonBuilder.buildJson()
    }
}
