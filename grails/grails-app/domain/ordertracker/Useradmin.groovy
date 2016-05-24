package ordertracker

import ordertracker.security.UserAdminEncryptor

class Useradmin {

    static constraints = {
        email(email:true)
        password(blank:false, password:true)
    }
    
    Long id
    Long version
    String email
    String password
    String toString()
    { 
        "$email"
    }

    def afterInsert() {
        this.cipherUserPassword()
    }
    
    def afterUpdate() {
        if ( passwordModified ) cipherUserPassword()
    }

    def cipherUserPassword() {
        new UserAdminEncryptor(this).encryptPassword(password)
    }

    def beforeUpdate() {
        passwordModified = this.isDirty('password')
    }

    boolean	passwordModified
    static transients = ['passwordModified']
} 
