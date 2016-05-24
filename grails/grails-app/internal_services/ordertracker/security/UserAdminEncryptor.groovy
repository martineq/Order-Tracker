package ordertracker.security

import ordertracker.Useradmin

/**
 * Created by martin on 24/05/16.
 */
class UserAdminEncryptor {

    private Useradmin useradmin
    private Cypher cypher

    UserAdminEncryptor(Useradmin useradmin) {
        this.useradmin = useradmin
        this.cypher = new Cypher()
    }

    def encryptPassword(String password) {
        try {
            Useradmin.withNewSession {
                String query = "UPDATE Useradmin u SET u.password = :password WHERE u.id = :id"
                return (Useradmin.executeUpdate(query, [password: encryptString(password), id: useradmin.id]) == 1) ? true : false
            }
        } catch (NullPointerException e) {
            return false
        }
    }

    def validatePassword(String password) {
        try {
            Useradmin.withNewSession{
                String query = "SELECT COUNT(*) FROM Useradmin u WHERE u.id = :id AND u.password = :password"
                return ( Useradmin.executeQuery(query, [password: encryptString(password), id: useradmin.id]).get(0) == 1 ) ? true : false
            }
        } catch (NullPointerException e) {
            return false
        }
    }


    def encryptString(String message) {
        return cypher.encrypt(useradmin.id + useradmin.email + message)
    }
}
