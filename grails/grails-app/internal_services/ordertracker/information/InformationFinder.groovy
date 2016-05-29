package ordertracker.information

import ordertracker.Seller
import ordertracker.User
import ordertracker.UserType
import ordertracker.constants.Keywords
import ordertracker.queries.QueryException
import ordertracker.queries.Requester

/**
 * Created by martin on 17/05/16.
 */
class InformationFinder {

    private Requester requester

    public InformationFinder(Requester requester) {
        this.requester = requester
    }

    public def findProperty(Keywords keyword, String errorMessage) {
        try {

            return requester.getProperty(keyword)
        }

        catch (NullPointerException n) {
            throw new QueryException(errorMessage)
        }
    }

    public def findSellerID(Keywords keyword) throws QueryException {

        return findSellerID(findUserID(keyword))
    }

    public def findUserID(Keywords keyword) throws QueryException{
        try {
            def username = requester.getProperty(keyword)
            return User.findByUsername(username).id
        }

        catch (NullPointerException e) {
            throw new QueryException("Nombre de usuario no encontrado")
        }
    }

    public def findSellerID(long user_id) throws QueryException{
        try {
            return UserType.findByUser_idAndType(user_id, Seller.getTypeName()).type_id
        }

        catch (NullPointerException e) {
            throw new QueryException("No existe un vendedor para ese usuario")
        }
    }

}
