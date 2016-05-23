 package ordertracker
 
class LoginInterceptor {

     public LoginInterceptor() {

        matchAll().excludes(controller: 'useradmin')
    }
    
    boolean before() {
        // if the user has not been authenticated,
        // redirect to authenticate the user...
        if(!session.user) {
            redirect controller: 'Useradmin', action: 'login'
            return false
        }
        true
    }
    
}