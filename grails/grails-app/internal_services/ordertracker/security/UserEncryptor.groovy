package ordertracker.security

import ordertracker.User
import ordertracker.util.CalendarDate

import java.security.MessageDigest

/**
 * Created by martin on 24/05/16.
 */
class UserEncryptor {

    private User user
    private Cypher cypher

    UserEncryptor(User user) {
        this.user = user;
        this.cypher = new Cypher()
    }

    def encryptPassword(String password) {
        User.withNewSession{
            String query = "UPDATE User u SET u.password = :password WHERE u.id = :id"
            return ( User.executeUpdate(query, [password: cypherMessage(password), id: user.id]) == 1 ) ? true : false
        }
    }

    def validatePassword(String password) {
        User.withNewSession{
            String query = "SELECT COUNT(*) FROM User u WHERE u.id = :id AND u.password = :password"
            return ( User.executeQuery(query, [password: cypherMessage(password), id: user.id]).get(0) == 1 ) ? true : false
        }
    }

    def generateRandomToken() {
        User.withNewSession{
            return cypher.encrypt( Long.toString(CalendarDate.currentDate()) + user.getToken() )
        }
    }

    def encryptToken(String token) {
        User.withNewSession{
            String query = "UPDATE User u SET u.token = :token WHERE u.id = :id "
            return ( User.executeUpdate(query, [token: cypherMessage(token), id: user.id]) == 1 ) ? true : false
        }
    }

    def validateToken(String token) {
        User.withNewSession{
            String query = "SELECT COUNT(*) FROM User u WHERE u.id = :id AND u.token = :token"
            return ( User.executeQuery(query, [id: user.id, token: cypherMessage(token)]).get(0) == 1 ) ? true : false
        }
    }

    def cypherMessage(String message) {
        return cypher.encrypt(user.id + user.username + message)
    }
}
