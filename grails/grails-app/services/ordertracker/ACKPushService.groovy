package ordertracker

import ordertracker.constants.Enums
import ordertracker.constants.HttpProtocol
import ordertracker.constants.Keywords
import ordertracker.protocol.ProtocolJsonBuilder
import ordertracker.protocol.Result
import ordertracker.protocol.Status
import ordertracker.queries.QueryException
import ordertracker.queries.Queryingly
import ordertracker.queries.Requester
import ordertracker.tranmission.TransmissionMedium

class ACKPushService implements Queryingly {

    private long seller_id
    private long message_id
    private boolean message_deleted

    ACKPushService() {
        this.seller_id = 0
        this.message_id = 0
        this.message_deleted = false
    }

    @Override
    def validate(Requester requester) {
        if ( requester.getProperty(HttpProtocol.METHOD).toString().compareTo(HttpProtocol.DELETE.toString()) != 0 )
            throw new QueryException("Invalid HTTP request method: must be DELETE")

        requester.validateRequest( Enums.asList(Keywords.ID) )

        try {
            def username = requester.getProperty(Keywords.USERNAME)
            seller_id = User.findByUsername(username).id
            message_id = new Long(requester.getProperty(Keywords.ID))
        }

        catch ( RuntimeException e ) {
            throw QueryException("Invalid query")
        }

        return true
    }

    @Override
    def generateQuery() {

        println(Distribution.count)
        try {
            def user_message = Distribution.findByMessage_idAndSeller(message_id, seller_id)

            if ( user_message != null ) {
                user_message.delete(flush: true)
                message_deleted = true

                if (Distribution.countByMessage_id(user_message.message_id) == 0) {
                    Push_message.findById(user_message.message_id).delete(flush: true)
                }
            }

        } catch ( Exception e ) {}

        Distribution.findAll().each { Distribution distribution ->
            println(distribution.id + ' '+ distribution.message_id + ' ' +distribution.seller)}

        Push_message.findAll().each { Push_message m ->
            println( m.id + ' '+ m.description + ' ' + m.properties)
        }

        return message_deleted
    }

    @Override
    def obtainResponse(TransmissionMedium transmissionMedium) {
        if ( message_deleted == true )
            return new ProtocolJsonBuilder(new Status(Result.OK, "ACK received"))
        else
            return new ProtocolJsonBuilder(new Status(Result.ERROR, "ACK invalid"))
    }
}
