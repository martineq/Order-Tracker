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
import ordertracker.util.logger.Log

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

        try {
            def user_message = Distribution.findByMessage_idAndSeller(message_id, seller_id)

            if ( user_message != null ) {
                user_message.delete(flush: true)
                message_deleted = true

                try {
                Log.info(Seller.findById(seller_id).name + " devolvió el ACK del mensaje push #"+ message_id)

                if ( Distribution.findByMessage_id(message_id) == null )
                    Log.info("Mensaje push #" + message_id + " fue entragado a "+ User.findById(seller_id).username + " y borrado del servidor")
                } catch (NullPointerException e){}

                if (Distribution.countByMessage_id(user_message.message_id) == 0) {
                    Push_message.findById(user_message.message_id).delete(flush: true)
                    try {
                        Log.info("Todos los mensajes push #"+ message_id + " fueron entregados")
                    } catch (NullPointerException e){}
                }
            }

        } catch ( Exception e ) {}

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
