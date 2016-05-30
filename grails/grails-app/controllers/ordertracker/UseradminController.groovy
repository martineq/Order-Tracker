package ordertracker

import ordertracker.security.UserAdminEncryptor


class UseradminController {
    def login = { 
        
    }
    
    def loginagain = { 
        
    }
    
    def doLogin = {
        Useradmin user = Useradmin.findWhere(email:params['email'])

        session.user = user

        if ( new UserAdminEncryptor(user).validatePassword((String) params['password']) ) {
            redirect(uri: '/')
        }

        else redirect(controller: 'useradmin', action: 'loginagain')
    }
    
    def logout = {
        session.user = null
        redirect(controller:'useradmin',action:'login')
    }

} 
