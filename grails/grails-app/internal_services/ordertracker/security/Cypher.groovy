package ordertracker.security

import java.security.MessageDigest

/**
 * Created by martin on 24/05/16.
 */
class Cypher {

    private MessageDigest messageDigest

    Cypher() {
        this.messageDigest = MessageDigest.getInstance("SHA-256")
    }

    def encrypt(String something) {
        int random = 0
        String hashValue = ""

        messageDigest.digest(something.getBytes("UTF-8")).each {
            random = ( random  % 15 ) + 1
            def hex = Integer.toHexString(0xFF & it)
            hashValue += ( ( hex.length() == 1 ) ? Integer.toHexString(random) + hex : hex )
        }

        return hashValue
    }
}
