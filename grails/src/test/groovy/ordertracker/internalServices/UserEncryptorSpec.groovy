package ordertracker.internalServices

import grails.test.mixin.Mock
import ordertracker.User
import ordertracker.UserLoader
import ordertracker.security.UserEncryptor
import spock.lang.Specification

/**
 * Created by martin on 24/05/16.
 */
@Mock([User])
class UserEncryptorSpec extends Specification {

    private static String USER = "martin"
    private static String PASSWORD = "85780"
    private static String TOKEN = "token1"

    def setup() {
        new UserLoader().load()
    }

    def generateUserMartin(String password, String token) {
        return new User(username: USER, password: password, token: token).save(flush: true)
    }

    def "test passwordEncryption"() {
        given:
            def user = generateUserMartin(PASSWORD, TOKEN)

        and:
            def encryptor = new UserEncryptor(user)

        when:
            encryptor.encryptPassword(PASSWORD)

        then:
            encryptor.validatePassword(PASSWORD) == true

    }

}
