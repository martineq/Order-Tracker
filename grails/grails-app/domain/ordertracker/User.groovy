package ordertracker

import ordertracker.security.UserEncryptor

class User {

    static constraints = {
    }

    String username
    String password
    String token

    def afterInsert() {
        cipherUserPassword()
    }

    def afterUpdate() {
        if ( passwordModified ) cipherUserPassword()
    }

    def cipherUserPassword() {
        new UserEncryptor(this).encryptPassword(password)
    }

    def beforeUpdate() {
        passwordModified = this.isDirty('password')
    }

    boolean	passwordModified
    static transients = ['passwordModified']
}
