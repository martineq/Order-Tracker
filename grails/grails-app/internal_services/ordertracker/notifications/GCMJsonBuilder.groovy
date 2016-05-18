package ordertracker.notifications

import ordertracker.Push_message
import ordertracker.constants.Keywords
import ordertracker.protocol.builder.JsonObjectBuilder
import ordertracker.protocol.builder.properties.JsonPropertyFactory

class GCMJsonBuilder {

    private def pushMessage

    GCMJsonBuilder(Push_message pushMessage) {
        this.pushMessage = pushMessage
    }

    def generateTo(String gcm_token) {
        return new JsonPropertyFactory(Keywords.TO, gcm_token)
    }

    def generateNotification() {
        def notification = new JsonObjectBuilder()
        notification.addJsonableItem(new JsonPropertyFactory(Keywords.TITLE, pushMessage.title))
        notification.addJsonableItem(new JsonPropertyFactory(Keywords.BODY, pushMessage.description))

        return new JsonPropertyFactory(Keywords.NOTIFICATION, notification)
    }

    def generateData() {
        def data = new JsonObjectBuilder()
        data.addJsonableItem(new JsonPropertyFactory(Keywords.TYPE, pushMessage.type))

        return new JsonPropertyFactory(Keywords.DATA, data)
    }

}
